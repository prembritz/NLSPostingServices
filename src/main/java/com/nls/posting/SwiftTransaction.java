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
import com.nls.core.ofs.OFSFieldFormatter;
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
import javax.inject.Inject;
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

@Path("/SwiftTransaction")
public class SwiftTransaction {

  private static DataSource cmDBPool;
  private static NumberFormat numberFormat = new DecimalFormat("###0.00");
  private static HashMap<String, String> ActualTableName;
  private static HashMap<String, String> GlobalParameters;
  private static String coreDBSchema;
  private static String channelSchema;

  @Inject CoreServices coreServices;

  public static void SetSchemaNames(String coreDBSchema, String channelSchema) {
    SwiftTransaction.coreDBSchema = coreDBSchema;
    SwiftTransaction.channelSchema = channelSchema;
  }

  public static void setDBPool(DataSource cmDBPool) {
    SwiftTransaction.cmDBPool = cmDBPool;
  }

  public static void setExternalTablenames(HashMap<String, String> ActualTableName) {
    SwiftTransaction.ActualTableName = ActualTableName;
  }

  public static void setInitiailizeGlobalParameters(HashMap<String, String> GlobalParameters) {
    SwiftTransaction.GlobalParameters = GlobalParameters;
  }

  @Timeout(value = 5, unit = ChronoUnit.SECONDS)
  @Counted()
  @POST
  @Traced()
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponseSchema(
      value = SwiftTransactionObject.class,
      responseDescription = "Swift Transaction Response",
      responseCode = "200")
  @Operation(summary = "Swift Transaction Request", description = "returns Swift Transaction data")
  public Response getSwiftTransactionDetails(
      @RequestBody(
              description = "Transaction ID",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = SwiftTransactionRequest.class)))
          SwiftTransactionRequest id) {

    SwiftTransactionObject SwiftTransObj = new SwiftTransactionObject();
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
      System.out.println("Swift Transaction Posting Started on [" + startTime + "]");
      TransId = id.hdrTranId;
      SourceUniqRef = id.reqTransactionReferenceNo;
      String DebitAccountno = id.reqAccountNo;
      String CreditAccountno = id.reqBeneficiaryAccount;
      String DebitAccountCurrency = id.reqDebitAcCurrency;
      String DebitAmount = id.reqDebitAmount;
      String UnitId = id.reqUnitID;
      String channelId = id.reqChnlId;
      String ServiceName = "SwiftTransaction";
      String BICCode = id.reqBeneficiaryBankBIC;
      String CBXReference = "";
      VIPMarker = id.vipMarker.equalsIgnoreCase(ResponseStatus.Y.getValue()) ? "VIP" : "";
      WaiveCharge = id.waiveCharge.equalsIgnoreCase(ResponseStatus.N.getValue()) ? "false" : "true";

      System.out.println(
          "Fetching Swift Transaction Details Transaction Id [" + SourceUniqRef + "]");

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
            SwiftTransObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.UNIQUE_REFERENCE_GENERATED_FAILED.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();
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
              SwiftTransObj,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.NOT_FOUND,
              CBXReference,
              UnitId,
              ResponseStatus.TRANSACTION_ALREADY_LOCKED.getValue(),
              TransId,
              VIPErrorDesc);
          return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();
        }
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        ResponseMessages(
            SwiftTransObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.LOCKING_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();
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
                SwiftTransObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                ResponseStatus.DUPLICATE_DATA.getValue(),
                TransId,
                VIPErrorDesc);
            return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();
          }
        }
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        ResponseMessages(
            SwiftTransObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.VALIDATION_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();
      }

      // Custom validation

      try (Connection dbConnection = cmDBPool.getConnection();
          PreparedStatement FldMapPS =
              dbConnection.prepareStatement(
                  "select * from "
                      + channelSchema
                      + ".t24$serv$fld$mappings "
                      + " where SERVICE_NAME=? AND FIELD_FORMATTER IS NOT NULL ");
          /*
           * PreparedStatement SortCodePS = dbConnection.prepareStatement( "select * from " +
           * coreDBSchema + ".BC$SORT$CODE" + " where ACC_WITH_BK_BIC=?");
           */
          PreparedStatement BicPS =
              dbConnection.prepareStatement(
                  "select * from " + coreDBSchema + ".DE$BIC " + " where ID=?")) {
        BicPS.setString(1, BICCode);
        ResultSet BicRS = BicPS.executeQuery();
        if (!BicRS.next()) {
          /*
           * SortCodePS.setString(1, BICCode); ResultSet SortCodeRS = SortCodePS.executeQuery(); if
           * (!SortCodeRS.next()) {
           */
          ResponseMessages(
              SwiftTransObj,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.NOT_FOUND,
              CBXReference,
              UnitId,
              ResponseStatus.BIC_NOT_FOUND.getValue(),
              TransId,
              VIPErrorDesc);
          return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();
          /*
           * } SortCodeRS.close(); SortCodePS.close();
           */
        }
        BicRS.close();
        BicPS.close();

        OFSData = new LinkedHashMap<String, String>();

        FldMapPS.setString(1, ServiceName);
        ResultSet FldMapRS = FldMapPS.executeQuery();
        while (FldMapRS.next()) {
          OFSFieldFormatter FldFormatter =
              (OFSFieldFormatter)
                  Class.forName(FldMapRS.getString("FIELD_FORMATTER")).newInstance();
          String FieldValue = FldFormatter.formattFieldValue(BICCode).toString();
          OFSData.put(FldMapRS.getString("INPUT_NAME"), FieldValue);
        }
        FldMapPS.close();
        FldMapRS.close();
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        ResponseMessages(
            SwiftTransObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.CUSTOM_VALIDATION_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();
      }

      // Validate Financial Transaction

      try {
        if (DuplicateCheck.equalsIgnoreCase(ResponseStatus.UNIQUE_DATA.getValue())) {

          // System.out.println("VipMarker ["+VIPMarker+"] Waive charge ["+WaiveCharge+"]");

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
                SwiftTransObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                ValFinTrans.getErrorDetail(),
                TransId,
                VIPErrorDesc);
            return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();
          }
        }
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        ResponseMessages(
            SwiftTransObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.VALIDATING_FINANCIAL_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();
      }

      // OFS Formatting

      /*
       * OFSData.put("DebitAccount", DebitAccountno); OFSData.put("TransactionAmount", DebitAmount);
       */
      try {
        if (ValidationStatus.equalsIgnoreCase(ResponseStatus.SUCCESS.getValue())
            || ValidationStatus.equalsIgnoreCase(ResponseStatus.PENDING.getValue())) {

          System.out.println("VIP Error Details [" + VIPErrorDesc + "]");

          OFSData = new LinkedHashMap<String, String>();
          Field[] fields = SwiftTransactionRequest.class.getDeclaredFields();
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
                SwiftTransObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                ResponseStatus.SERVICE_MAPPING_NOT_FOUND.getValue(),
                TransId,
                VIPErrorDesc);
            return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();
          }
        }
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        ResponseMessages(
            SwiftTransObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.OFS_FORMATTING_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();
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
                SwiftTransObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                QueuedTrans.getErrorDetail(),
                TransId,
                VIPErrorDesc);
            return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();
          }
        }
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        ResponseMessages(
            SwiftTransObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.QUEUING_TRANSACTIONS_FAILED.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();
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
              SwiftTransObj,
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
          Field[] resfields = SwiftTransactionObject.class.getDeclaredFields();
          for (Field field : resfields) {
            if (field.isAnnotationPresent(JsonbProperty.class)) {
              String annotationValue = field.getAnnotation(JsonbProperty.class).value();
              if (field.get(SwiftTransObj) != null)
                RespDetailMap.put(
                    annotationValue, field.get(SwiftTransObj).toString().replaceAll("\"", "\\\""));
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
            SwiftTransObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            CBXReference,
            UnitId,
            ResponseStatus.TRANSACTION_DETAIL_LOGGING_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc);
        return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();
      }

      return Response.status(Status.ACCEPTED).entity(SwiftTransObj).build();

    } catch (Exception except) {
      except.printStackTrace();
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    } finally {
      RelLocks = coreServices.ReleaseLock(new ReleaseLockRequest(UniqueReference));
      System.out.println(
          "[" + UniqueReference + "] Releasing Account Lock Status [" + RelLocks.getStatus() + "]");
      RelLocks = coreServices.ReleaseLock(new ReleaseLockRequest(SourceUniqRef));
      System.out.println(
          "["
              + SourceUniqRef
              + "] Releasing Transaction Lock Status ["
              + RelLocks.getStatus()
              + "]");

      LocalDateTime endTime = LocalDateTime.now();
      long millis = ChronoUnit.MILLIS.between(startTime, endTime);
      System.out.println(
          "Swift Transation Posting Completed and Processing Time Taken [ "
              + millis
              + " ] MilliSeconds");
    }
  }

  private void ResponseMessages(
      SwiftTransactionObject SwiftTransObj,
      String SourceUniqRef,
      String UniqueReference,
      ERROR_CODE ErrorCode,
      String CBXReference,
      String UnitId,
      String ErrorDescription,
      String TransId,
      String ResDescription) {

    SwiftTransObj.setHdrTranId(TransId);
    // SwiftTransObj.setHdrRefNo(UniqueReference);
    SwiftTransObj.setResResultCode(ErrorCode);
    SwiftTransObj.setResCbxReferenceNo(SourceUniqRef);
    SwiftTransObj.setResUserId(UnitId);
    SwiftTransObj.setResStatusCode(ErrorCode);
    SwiftTransObj.setResStatusDescription(ErrorDescription);
    SwiftTransObj.setResErrorCode(ErrorCode);
    SwiftTransObj.setResErrorMessage(ErrorDescription);
    SwiftTransObj.setResCoreReferenceNo(CBXReference);
    SwiftTransObj.setResDescription(ResDescription);
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
