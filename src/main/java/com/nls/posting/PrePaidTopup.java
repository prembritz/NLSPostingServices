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

@Path("/PrePaidTopup")
public class PrePaidTopup {

  private static DataSource cmDBPool;
  private static NumberFormat numberFormat = new DecimalFormat("###0.00");
  private static HashMap<String, String> ActualTableName;
  private static HashMap<String, String> GlobalParameters;
  private static String coreDBSchema;
  private static String channelSchema;

  public static void SetSchemaNames(String coreDBSchema, String channelSchema) {
    PrePaidTopup.coreDBSchema = coreDBSchema;
    PrePaidTopup.channelSchema = channelSchema;
  }

  public static void setDBPool(DataSource cmDBPool) {
    PrePaidTopup.cmDBPool = cmDBPool;
  }

  public static void setExternalTablenames(HashMap<String, String> ActualTableName) {
    PrePaidTopup.ActualTableName = ActualTableName;
  }

  public static void setInitiailizeGlobalParameters(HashMap<String, String> GlobalParameters) {
    PrePaidTopup.GlobalParameters = GlobalParameters;
  }

  private static CoreServices coreServices;

  public static void setCoreServices(CoreServices coreServices) {
    PrePaidTopup.coreServices = coreServices;
  }

  @Timeout(value = 5, unit = ChronoUnit.SECONDS)
  @Counted()
  @POST
  @Traced()
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponseSchema(
      value = PrePaidTopupObject.class,
      responseDescription = "PrePaid Topup Response",
      responseCode = "200")
  @Operation(summary = "PrePaid Topup Request", description = "returns PrePaid Topup data")
  public Response getPrePaidTopupDetails(
      @RequestBody(
              description = "Transaction ID",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = PrePaidTopupRequest.class)))
          PrePaidTopupRequest id) {

    PrePaidTopupObject CCRObj = new PrePaidTopupObject();
    // ReleaseLockObject RelLocks = null;
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
      System.out.println("PrePaid Topup Posting Started on [" + startTime + "]");
      TransId = id.hdrTransId;
      SourceUniqRef = id.reqTransactionReferenceNo;
      String DebitAccountno = id.reqAccountNo;
      String CreditAccountno = "";
      String DebitAccountCurrency = id.debitAccountCurrency;
      String DebitAmount = id.reqDebitAmount;
      String UnitId = id.reqUnitID;
      String channelId = id.reqChnlId;
      String ServiceName = "PrePaidTopup";
      String CBXReference = "";
      VIPMarker = id.vipMarker.equalsIgnoreCase(ResponseStatus.Y.getValue()) ? "VIP" : "";
      WaiveCharge = id.waiveCharge.equalsIgnoreCase(ResponseStatus.N.getValue()) ? "false" : "true";

      System.out.println("Fetching PrePaid Topup Details Transaction Id [" + SourceUniqRef + "]");

      System.out.println("FT Prefix [" + GlobalParameters.get("CBX_PREFIX") + "]");
      // System.out.println("Initialized Rest Client [" + coreServices + "]");

      // Reference Generator

      try {
        ReferenceObject reference =
            coreServices.generateReference(new ReferenceRequest(SourceUniqRef));
        UniqueReference = reference.getUniqueReference();
        System.out.println("Unique Reference [" + UniqueReference + "]");
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        ResponseMessages(
            CCRObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            CBXReference,
            UnitId,
            ResponseStatus.UNIQUE_REFERENCE_GENERATED_FAILED.getValue(),
            TransId,
            VIPErrorDesc,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(CCRObj).build();
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
              CCRObj,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.NOT_FOUND,
              CBXReference,
              UnitId,
              ResponseStatus.TRANSACTION_ALREADY_LOCKED.getValue(),
              TransId,
              VIPErrorDesc,
              ResponseStatus.TRANSACTION_ALREADY_LOCKED.getValue());
          return Response.status(Status.ACCEPTED).entity(CCRObj).build();
        }
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        ResponseMessages(
            CCRObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            CBXReference,
            UnitId,
            ResponseStatus.LOCKING_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(CCRObj).build();
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
                CCRObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                ResponseStatus.DUPLICATE_DATA.getValue(),
                TransId,
                VIPErrorDesc,
                ResponseStatus.DUPLICATE_DATA.getValue());
            return Response.status(Status.ACCEPTED).entity(CCRObj).build();
          }
        }
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        ResponseMessages(
            CCRObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            CBXReference,
            UnitId,
            ResponseStatus.VALIDATION_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(CCRObj).build();
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

          if (ValidationStatus.equalsIgnoreCase(ResponseStatus.FAILED.getValue())) {
            ResponseMessages(
                CCRObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                ValFinTrans.getErrorDetail(),
                TransId,
                VIPErrorDesc,
                ValFinTrans.getErrorDetail());
            return Response.status(Status.ACCEPTED).entity(CCRObj).build();
          }
        }
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        ResponseMessages(
            CCRObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            CBXReference,
            UnitId,
            ResponseStatus.VALIDATING_FINANCIAL_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(CCRObj).build();
      }

      // OFS Formatting

      /*
       * OFSData.put("DebitAccount", DebitAccountno); OFSData.put("TransactionAmount", DebitAmount);
       */
      try {
        if (ValidationStatus.equalsIgnoreCase(ResponseStatus.SUCCESS.getValue())
            || ValidationStatus.equalsIgnoreCase(ResponseStatus.PENDING.getValue())) {

          // System.out.println("VIP Error Details ["+VIPErrorDesc+"]");

          OFSData = new LinkedHashMap<String, String>();
          Field[] fields = PrePaidTopupRequest.class.getDeclaredFields();
          OFSData.put("UniqueReference", UniqueReference);
          OFSData.put("FTReference", GlobalParameters.get("CBX_PREFIX") + "" + UniqueReference);
          for (Field field : fields) {
            if (field.isAnnotationPresent(JsonbProperty.class)) {
              String annotationValue = field.getAnnotation(JsonbProperty.class).value();
              if (field.get(id) != null)
                OFSData.put(annotationValue, field.get(id).toString().replaceAll("\"", "\\\""));
            }
          }

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
                CCRObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                ResponseStatus.SERVICE_MAPPING_NOT_FOUND.getValue(),
                TransId,
                VIPErrorDesc,
                ResponseStatus.SERVICE_MAPPING_NOT_FOUND.getValue());
            return Response.status(Status.ACCEPTED).entity(CCRObj).build();
          }
        }
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        ResponseMessages(
            CCRObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            CBXReference,
            UnitId,
            ResponseStatus.OFS_FORMATTING_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(CCRObj).build();
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
                      channelId,
                      OFSMsg));
          QueueStatus = QueuedTrans.getStatus();
          System.out.println(
              "[" + UniqueReference + "] Queued Transaction Status [" + QueueStatus + "]");

          if (QueueStatus.equalsIgnoreCase(ResponseStatus.FAILED.getValue())) {
            ResponseMessages(
                CCRObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                QueuedTrans.getErrorDetail(),
                TransId,
                VIPErrorDesc,
                QueuedTrans.getErrorDetail());
            return Response.status(Status.ACCEPTED).entity(CCRObj).build();
          }
        }
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        ResponseMessages(
            CCRObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            CBXReference,
            UnitId,
            ResponseStatus.QUEUING_TRANSACTIONS_FAILED.getValue(),
            TransId,
            VIPErrorDesc,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(CCRObj).build();
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
              CCRObj,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.SUCCESSFUL,
              CBXReference,
              UnitId,
              ErrorMessage,
              TransId,
              VIPErrorDesc,
              ErrorMessage);

          OFSData.remove("UniqueReference");
          OFSData.remove("FTReference");
          Gson gson = new Gson();
          String RequestData = gson.toJson(OFSData);
          RequestData = RequestData.replaceAll("\"", "\\\"");
          System.out.println("Json Request Data[" + RequestData + "]");
          CreditAccountno = GlobalParameters.get("Prepaidcardaccount");
          System.out.println("CreditAccountno [" + CreditAccountno + "]");
          OFSData.put("CreditAccountNo", CreditAccountno);

          RespDetailMap = new LinkedHashMap<>();
          Field[] resfields = PrePaidTopupObject.class.getDeclaredFields();
          for (Field field : resfields) {
            if (field.isAnnotationPresent(JsonbProperty.class)) {
              String annotationValue = field.getAnnotation(JsonbProperty.class).value();
              if (field.get(CCRObj) != null)
                RespDetailMap.put(
                    annotationValue, field.get(CCRObj).toString().replaceAll("\"", "\\\""));
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
                      channelId,
                      UnitId,
                      ResponseStatus.SUCCESSFUL.getValue(),
                      RequestData,
                      ResponseData));
          PersistStatus = PersistData.getStatus();
          System.out.println(
              "[" + UniqueReference + "] Persist Unique Data Status [" + PersistStatus + "]");
        }
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        ResponseMessages(
            CCRObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            CBXReference,
            UnitId,
            ResponseStatus.TRANSACTION_DETAIL_LOGGING_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(CCRObj).build();
      }

      return Response.status(Status.ACCEPTED).entity(CCRObj).build();

    } catch (Exception except) {
      except.printStackTrace();
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    } finally {

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
          "CreditCard Payment Posting Completed and Processing Time Taken [ "
              + millis
              + " ] MilliSeconds");
    }
  }

  private void ResponseMessages(
      PrePaidTopupObject CCRObj,
      String SourceUniqRef,
      String UniqueReference,
      ERROR_CODE ErrorCode,
      String CBXReference,
      String UnitId,
      String ErrorDescription,
      String TransID,
      String ResDescription,
      String TimedoutMessage) {

    CCRObj.setHdrTranId(TransID);
    // CCRObj.setHdrRefNo(UniqueReference);
    CCRObj.setResResultCode(ErrorCode);
    CCRObj.setResCbxReferenceNo(SourceUniqRef);
    CCRObj.setResUserId(UnitId);
    CCRObj.setResStatusCode(ErrorCode);
    CCRObj.setResStatusDescription(ErrorDescription);
    CCRObj.setResErrorCode(ErrorCode);
    CCRObj.setResErrorMessage(TimedoutMessage);
    CCRObj.setResCoreReferenceNo(CBXReference);
    CCRObj.setResDescription(ResDescription);
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
