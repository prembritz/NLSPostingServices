package com.nls.posting;

import com.google.gson.Gson;
import com.nls.core.CoreServices;
import com.nls.core.DuplicateCheckObject;
import com.nls.core.DuplicateCheckRequest;
import com.nls.core.LockTransactionRequest;
import com.nls.core.LockTrasactionObject;
import com.nls.core.OFSFormatObject;
import com.nls.core.OFSFormatRequest;
import com.nls.core.PersistUniqueDataObject;
import com.nls.core.PersistUniqueDataRequest;
import com.nls.core.QueueFinancialTransObject;
import com.nls.core.QueueFinancialTransRequest;
import com.nls.core.ReferenceObject;
import com.nls.core.ReferenceRequest;
import com.nls.core.ReleaseLockObject;
import com.nls.core.ReleaseLockRequest;
import com.nls.core.ValidateFinancialTransObject;
import com.nls.core.ValidateFinancialTransRequest;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ForkJoinPool;
import javax.json.bind.annotation.JsonbProperty;
import javax.sql.DataSource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.opentracing.Traced;

@Path("/AccrualDetails")
public class AccuralDetails {

  private static DataSource cmDBPool;
  private static NumberFormat numberFormat = new DecimalFormat("###0.00");
  private static HashMap<String, String> ActualTableName;
  private static HashMap<String, String> GlobalParameters;
  private static String coreDBSchema;
  private static String channelSchema;

  public static void SetSchemaNames(String coreDBSchema, String channelSchema) {
    AccuralDetails.coreDBSchema = coreDBSchema;
    AccuralDetails.channelSchema = channelSchema;
  }

  public static void setDBPool(DataSource cmDBPool) {
    AccuralDetails.cmDBPool = cmDBPool;
  }

  public static void setExternalTablenames(HashMap<String, String> ActualTableName) {
    AccuralDetails.ActualTableName = ActualTableName;
  }

  public static void setInitiailizeGlobalParameters(HashMap<String, String> GlobalParameters) {
    AccuralDetails.GlobalParameters = GlobalParameters;
  }

  private static CoreServices coreServices;

  public static void setCoreServices(CoreServices coreServices) {
    AccuralDetails.coreServices = coreServices;
  }

  @Timeout(value = 5, unit = ChronoUnit.SECONDS)
  @Counted()
  @POST
  @Traced()
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponseSchema(
      value = AccuralObject.class,
      responseDescription = "Accrual Response",
      responseCode = "200")
  @Operation(summary = "Accrual Request", description = "returns Accrual data")
  public Response getAccuralDetails(
      @RequestBody(
              description = "Transaction ID",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = AccuralRequest.class)))
          AccuralRequest id) {

    AccuralObject accuralObj = new AccuralObject();
    ReleaseLockObject RelLocks = null;
    LockTrasactionObject Locking = null;
    LinkedHashMap<String, String> OFSData = null;
    LinkedHashMap<String, String> FiancialDetailsMap = null;
    LinkedHashMap<String, String> RespDetailMap = null;

    String UniqueReference = "";
    boolean AccountLockStatus = false;
    boolean TransactionLockStatus = false;
    String DuplicateCheck = "";
    String ValidationStatus = "";
    String QueueStatus = "";
    String PersistStatus = "";
    String OFSMsg = "";
    String OFSStatus = "";
    String SourceUniqRef = "";
    String TransId = "";
    String VIPMarker = "";
    String WaiveCharge = "";
    String VIPErrorDesc = "";
    String ErrorMessage = ResponseStatus.SUCCESS.getValue();
    String ValFinErrDetails = "";

    LocalDateTime startTime = LocalDateTime.now();

    try {

      System.out.println("Accrual Postings Started on [" + startTime + "]");

      TransId = id.hdrTranId;
      SourceUniqRef = id.transactionrefno;
      // String settlementAccount = id.settlementaccountno;
      String CreditAccountno = id.settlementaccountno;
      String DebitAccountCurrency = id.reqTransactionCurrency;
      String DebitAmount = id.amount;
      String UnitId = id.unitid;
      String channelName = ResponseStatus.CHANNEL.getValue();
      String ServiceName = "Accrual";
      String CBXReference = "";
      String DebitAccountno = GlobalParameters.get("ACCRUAL_DEBIT_ACCOUNT");
      // String DebitAccountnumber = "";

      VIPMarker = id.vipMarker.equalsIgnoreCase(ResponseStatus.Y.getValue()) ? "VIP" : "";
      WaiveCharge = id.waiveCharge.equalsIgnoreCase(ResponseStatus.N.getValue()) ? "false" : "true";

      System.out.println("Fetching Accural Details Transaction Id [" + SourceUniqRef + "]");

      System.out.println("FT Prefix [" + GlobalParameters.get("CBX_PREFIX") + "]");

      // Reference Generator
      try {
        ReferenceObject reference =
            coreServices.generateReference(new ReferenceRequest(SourceUniqRef));
        UniqueReference = reference.getUniqueReference();
        System.out.println("Unique Reference [" + UniqueReference + "]");
      } catch (Exception e) {

        e.printStackTrace();
        ResponseMessages(
            accuralObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.UNIQUE_REFERENCE_GENERATED_FAILED.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(accuralObj).build();
      }

      // Lock Manager
      try {
        Locking =
            coreServices.LockingTransactions(
                new LockTransactionRequest(
                    ResponseStatus.ACCOUNT.getValue(),
                    DebitAccountno,
                    ResponseStatus.LOCK_PERIOD.getValue(),
                    UniqueReference));
        AccountLockStatus = Locking.getLockSuccessful().equalsIgnoreCase("true") ? true : false;

        Locking =
            coreServices.LockingTransactions(
                new LockTransactionRequest(
                    ResponseStatus.TRANSACTION.getValue(),
                    DebitAccountno,
                    ResponseStatus.LOCK_PERIOD.getValue(),
                    SourceUniqRef));
        TransactionLockStatus = Locking.getLockSuccessful().equalsIgnoreCase("true") ? true : false;

        System.out.println(
            "["
                + UniqueReference
                + "] Account Lock Status ["
                + AccountLockStatus
                + "] "
                + " Transaction Lock Status ["
                + TransactionLockStatus
                + "]");

        if (!AccountLockStatus || !TransactionLockStatus) {
          ResponseMessages(
              accuralObj,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.NOT_FOUND,
              CBXReference,
              UnitId,
              ResponseStatus.TRANSACTION_ALREADY_LOCKED.getValue(),
              TransId,
              VIPErrorDesc);
          return Response.status(Status.ACCEPTED).entity(accuralObj).build();
        }
      } catch (Exception e) {
        e.printStackTrace();
        ResponseMessages(
            accuralObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.LOCKING_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(accuralObj).build();
      }

      // Transaction Uniqueness
      try {
        if (AccountLockStatus && TransactionLockStatus) {
          DuplicateCheckObject DupCheck =
              coreServices.TransDuplicateCheck(
                  new DuplicateCheckRequest(
                      SourceUniqRef, ServiceName, ResponseStatus.SUCCESSFUL.getValue()));
          DuplicateCheck = DupCheck.getStatus();
          System.out.println(
              "[" + UniqueReference + "] Duplicate check Status [" + DuplicateCheck + "]");

          if (!DuplicateCheck.equalsIgnoreCase(ResponseStatus.UNIQUE_DATA.getValue())) {
            ResponseMessages(
                accuralObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                ResponseStatus.DUPLICATE_DATA.getValue(),
                TransId,
                VIPErrorDesc);
            return Response.status(Status.ACCEPTED).entity(accuralObj).build();
          }
        }
      } catch (Exception e) {

        e.printStackTrace();
        ResponseMessages(
            accuralObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.VALIDATION_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(accuralObj).build();
      }
      // Custom validation

      // Validate Financial Transaction

      try {
        if (DuplicateCheck.equalsIgnoreCase(ResponseStatus.UNIQUE_DATA.getValue())) {
          System.out.println("VipMarker [" + VIPMarker + "] Waive charge [" + WaiveCharge + "]");
          ValidateFinancialTransObject ValFinTrans =
              coreServices.ValidateTransactions(
                  new ValidateFinancialTransRequest(
                      UnitId,
                      UniqueReference,
                      ServiceName,
                      DebitAccountno,
                      CreditAccountno,
                      DebitAmount,
                      DebitAccountCurrency,
                      VIPMarker,
                      WaiveCharge));
          ValidationStatus = ValFinTrans.getStatus();
          System.out.println(
              "["
                  + UniqueReference
                  + "] Validate Financial Transaction Status ["
                  + ValidationStatus
                  + "]");
          ValFinErrDetails = ValFinTrans.getErrorDetail();
          FiancialDetailsMap = new LinkedHashMap<>();
          Field[] resfields = ValidateFinancialTransObject.class.getDeclaredFields();
          for (Field field : resfields) {
            if (field.isAnnotationPresent(JsonbProperty.class)) {
              String annotationValue = field.getAnnotation(JsonbProperty.class).value();
              // System.out.println("property name ["+annotationValue+"] value
              // ["+field.get(ValFinTrans).toString()+"]");
              if (field.get(ValFinTrans) != null)
                FiancialDetailsMap.put(
                    annotationValue, field.get(ValFinTrans).toString().replaceAll("\"", "\\\""));
            }
          }

          if (ValidationStatus.equalsIgnoreCase(ResponseStatus.FAILED.getValue())
              && !ValFinErrDetails.startsWith("Insufficient")) {
            ResponseMessages(
                accuralObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                ValFinTrans.getErrorDetail(),
                TransId,
                VIPErrorDesc);
            return Response.status(Status.ACCEPTED).entity(accuralObj).build();
          }
        }
      } catch (Exception e) {

        e.printStackTrace();
        ResponseMessages(
            accuralObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.VALIDATING_FINANCIAL_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(accuralObj).build();
      }

      // OFS Formatting

      try {
        if (ValidationStatus.equalsIgnoreCase(ResponseStatus.SUCCESS.getValue())
            || ValidationStatus.equalsIgnoreCase(ResponseStatus.PENDING.getValue())) {

          // System.out.println("VIP Error Details ["+VIPErrorDesc+"]");
          OFSData = new LinkedHashMap<String, String>();
          Field[] fields = AccuralRequest.class.getDeclaredFields();
          OFSData.put("UniqueReference", UniqueReference);
          OFSData.put("FTReference", GlobalParameters.get("CBX_PREFIX") + "" + UniqueReference);
          for (Field field : fields) {
            if (field.isAnnotationPresent(JsonbProperty.class)) {
              String annotationValue = field.getAnnotation(JsonbProperty.class).value();
              if (field.get(id) != null)
                OFSData.put(annotationValue, field.get(id).toString().replaceAll("\"", "\\\""));
            }
          }
          OFSData.put("DebitAccountNo", DebitAccountno);

          OFSFormatObject OFSFormat =
              coreServices.OFSFormatter(new OFSFormatRequest(ServiceName, OFSData));
          OFSStatus = OFSFormat.getStatus();
          OFSMsg = OFSFormat.getOfsMsg();
          System.out.println(
              "["
                  + UniqueReference
                  + "] OFS Formatter Status ["
                  + OFSStatus
                  + "] OFS Msg ["
                  + OFSMsg
                  + "]");

          if (OFSStatus.equalsIgnoreCase(ResponseStatus.SERVICE_MAPPING_NOT_FOUND.getValue())) {
            ResponseMessages(
                accuralObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                ResponseStatus.SERVICE_MAPPING_NOT_FOUND.getValue(),
                TransId,
                VIPErrorDesc);
            return Response.status(Status.ACCEPTED).entity(accuralObj).build();
          }
        }
      } catch (Exception e) {

        e.printStackTrace();
        ResponseMessages(
            accuralObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.OFS_FORMATTING_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(accuralObj).build();
      }

      // Queuing and lock amount Transactions

      try {
        if (OFSStatus.equalsIgnoreCase(ResponseStatus.FORMATTED_SUCCESSFULLY.getValue())) {
          QueueFinancialTransObject QueuedTrans =
              coreServices.QueueService(
                  new QueueFinancialTransRequest(
                      UniqueReference,
                      ServiceName,
                      UnitId,
                      DebitAccountno,
                      DebitAmount,
                      DebitAccountCurrency,
                      ResponseStatus.LOCK_FUNDS.getValue(),
                      channelName,
                      OFSMsg));
          QueueStatus = QueuedTrans.getStatus();
          System.out.println(
              "[" + UniqueReference + "] Queued Transaction Status [" + QueueStatus + "]");

          if (QueueStatus.equalsIgnoreCase(ResponseStatus.FAILED.getValue())) {
            ResponseMessages(
                accuralObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                QueuedTrans.getErrorDetail(),
                TransId,
                VIPErrorDesc);
            return Response.status(Status.ACCEPTED).entity(accuralObj).build();
          }
        }
      } catch (Exception e) {

        e.printStackTrace();
        ResponseMessages(
            accuralObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.QUEUING_TRANSACTIONS_FAILED.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(accuralObj).build();
      }

      // Persist Data Unique

      try {
        if (QueueStatus.equalsIgnoreCase(ResponseStatus.SUCCESS.getValue())) {

          CBXReference = GlobalParameters.get("CBX_PREFIX") + "" + UniqueReference;

          System.out.println("ValFinErrDetails [" + ValFinErrDetails + "]");
          if (ValFinErrDetails != null && !VIPMarker.equals("")) {
            VIPErrorDesc = ValFinErrDetails;
            ErrorMessage = ResponseStatus.PENDING.getValue();
            VipQueueInsertion(ServiceName, ErrorMessage, SourceUniqRef, CBXReference);
          } else {
            VIPErrorDesc = "SUCCESS";
          }

          ResponseMessages(
              accuralObj,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.SUCCESSFUL,
              CBXReference,
              UnitId,
              ErrorMessage,
              TransId,
              VIPErrorDesc);

          OFSData.remove("UniqueReference");
          OFSData.remove("FTReference");
          Gson gson = new Gson();
          String RequestData = gson.toJson(OFSData);
          RequestData = RequestData.replaceAll("\"", "\\\"");
          System.out.println("Json Request Data[" + RequestData + "]");

          RespDetailMap = new LinkedHashMap<>();
          Field[] resfields = AccuralObject.class.getDeclaredFields();
          for (Field field : resfields) {
            if (field.isAnnotationPresent(JsonbProperty.class)) {
              String annotationValue = field.getAnnotation(JsonbProperty.class).value();
              if (field.get(accuralObj) != null)
                RespDetailMap.put(
                    annotationValue, field.get(accuralObj).toString().replaceAll("\"", "\\\""));
            }
          }
          RespDetailMap.putAll(FiancialDetailsMap);
          gson = new Gson();
          String ResponseData = gson.toJson(RespDetailMap);
          ResponseData = ResponseData.replaceAll("\"", "\\\"");
          System.out.println("Json Response Data[" + ResponseData + "]");

          PersistUniqueDataObject PersistData =
              coreServices.PersistUniqueData(
                  new PersistUniqueDataRequest(
                      SourceUniqRef,
                      UniqueReference,
                      ServiceName,
                      DebitAccountno,
                      CreditAccountno,
                      DebitAmount,
                      DebitAccountCurrency,
                      channelName,
                      UnitId,
                      ResponseStatus.SUCCESSFUL.getValue(),
                      RequestData,
                      ResponseData));
          PersistStatus = PersistData.getStatus();
          System.out.println(
              "[" + UniqueReference + "] Persist Unique Data Status [" + PersistStatus + "]");
        }
      } catch (Exception e) {
        e.printStackTrace();
        ResponseMessages(
            accuralObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.TRANSACTION_DETAIL_LOGGING_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(accuralObj).build();
      }

      return Response.status(Status.ACCEPTED).entity(accuralObj).build();

    } catch (Exception except) {
      except.printStackTrace();
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    } finally {

      /*RelLocks = coreServices.ReleaseLock(new ReleaseLockRequest(UniqueReference));
      System.out.println(
          "[" + UniqueReference + "] Releasing Account Lock Status [" + RelLocks.getStatus() + "]");
      RelLocks = coreServices.ReleaseLock(new ReleaseLockRequest(SourceUniqRef));
      System.out.println(
          "["
              + SourceUniqRef
              + "] Releasing Transaction Lock Status ["
              + RelLocks.getStatus()
              + "]");*/

      coreServices
          .ReleaseLock(new ReleaseLockRequest(UniqueReference))
          .whenCompleteAsync(
              ((ReleaseLockObject, exception) -> {
                if (exception != null) {
                  exception.printStackTrace();
                } else {
                  System.out.println(
                      "["
                          + ReleaseLockObject.getUniqueReference()
                          + "] Releasing Account Lock Status ["
                          + ReleaseLockObject.getStatus()
                          + "]");
                }
              }),
              ForkJoinPool.commonPool());

      coreServices
          .ReleaseLock(new ReleaseLockRequest(SourceUniqRef))
          .whenCompleteAsync(
              ((ReleaseLockObject, exception) -> {
                if (exception != null) {
                  exception.printStackTrace();
                } else {
                  System.out.println(
                      "["
                          + ReleaseLockObject.getUniqueReference()
                          + "] Releasing Transaction Lock Status ["
                          + ReleaseLockObject.getStatus()
                          + "]");
                }
              }),
              ForkJoinPool.commonPool());

      LocalDateTime endTime = LocalDateTime.now();
      long millis = ChronoUnit.MILLIS.between(startTime, endTime);
      System.out.println(
          "Accrual Postings Completed and Processing Time Taken [ " + millis + " ] MilliSeconds");
    }
  }

  private void ResponseMessages(
      AccuralObject accuralObj,
      String SourceUniqRef,
      String UniqueReference,
      ERROR_CODE ErrorCode,
      String CBXReference,
      String UnitId,
      String ErrorDescription,
      String TransId,
      String ResDescription) {

    accuralObj.setHdrTranId(TransId);
    accuralObj.setResResultCode(ErrorCode);
    accuralObj.setResCbxReferenceNo(SourceUniqRef);
    accuralObj.setResUserId(UnitId);
    accuralObj.setResStatusCode(ErrorCode);
    accuralObj.setResStatusDescription(ErrorDescription);
    accuralObj.setResErrorCode(ErrorCode);
    accuralObj.setResErrorDesc(ResDescription);
    accuralObj.setResErrorMessage(ErrorDescription);
    accuralObj.setResRequestID("");
    accuralObj.setResCoreReferenceNo(CBXReference);
  }

  public void VipQueueInsertion(
      String serviceName, String Status, String SourceUniqRef, String CBXReference)
      throws SQLException {

    System.out.println("************ Calling VIP Queue Method ***********************");
    try {
      Connection DbConnection = cmDBPool.getConnection();
      PreparedStatement VIPQueuePs =
          DbConnection.prepareStatement(
              "select tbl.* from "
                  + channelSchema
                  + ".VIP$CALLBACK$QUEUE tbl"
                  + " where tbl.CORE_REFERENCE_NO=?",
              ResultSet.TYPE_SCROLL_INSENSITIVE,
              ResultSet.CONCUR_UPDATABLE);
      VIPQueuePs.setString(1, CBXReference);
      ResultSet VIPQueueRs = VIPQueuePs.executeQuery();
      if (!VIPQueueRs.next()) {
        VIPQueueRs.moveToInsertRow();
        VIPQueueRs.updateString("CORE_REFERENCE_NO", CBXReference);
        VIPQueueRs.updateString("TRANS_REFERENCE_NO", SourceUniqRef);
        VIPQueueRs.updateString("STATUS", Status);
        VIPQueueRs.updateString("SERVICE_NAME", serviceName);
        VIPQueueRs.insertRow();
      }
      VIPQueuePs.close();
      VIPQueueRs.close();
      if (DbConnection.getAutoCommit() == false) DbConnection.commit();
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }
}
