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

@Path("/SaccoTransfer")
public class KESaccoTransfter {
  private static DataSource cmDBPool;
  private static NumberFormat numberFormat = new DecimalFormat("###0.00");
  private static HashMap<String, String> ActualTableName;
  private static HashMap<String, String> GlobalParameters;
  private static String coreDBSchema;
  private static String channelDBSchema;

  @Inject CoreServices coreServices;

  public static void setDBPool(DataSource cmDBPool) {
    KESaccoTransfter.cmDBPool = cmDBPool;
  }

  public static void setExternalTablenames(HashMap<String, String> ActualTableName) {
    KESaccoTransfter.ActualTableName = ActualTableName;
  }

  public static void SetSchemaNames(String coreDBSchema, String channelDBSchema) {
    KESaccoTransfter.coreDBSchema = coreDBSchema;
    KESaccoTransfter.channelDBSchema = channelDBSchema;
  }

  public static void setInitiailizeGlobalParameters(HashMap<String, String> GlobalParameters) {
    KESaccoTransfter.GlobalParameters = GlobalParameters;
  }

  @Timeout(value = 5, unit = ChronoUnit.SECONDS)
  @Counted()
  @POST
  @Traced()
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponseSchema(
      value = KESaccoTransferObject.class,
      responseDescription = "KESACCO Transfer Response",
      responseCode = "200")
  @Operation(summary = "KE SACCO Transfer Request", description = "returns KESACCO Transfer data")
  public Response getSaccoDetails(
      @RequestBody(
              description = "Transaction ID",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = KeSaccoTransferRequest.class)))
          KeSaccoTransferRequest id) {

    KESaccoTransferObject saccoObj = new KESaccoTransferObject();
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
    String VIPMarker = "";
    String WaiveCharge = "";
    String VIPErrorDesc = "";
    String ErrorMessage = ResponseStatus.SUCCESS.getValue();
    String ValFinErrDetails = "";
    String transID = "";

    LocalDateTime startTime = LocalDateTime.now();

    try {
      System.out.println("Sacco Transfer Posting Started on [" + startTime + "]");
      String ServiceName = "SaccoTransfer";
      transID = id.HdrTranId;
      SourceUniqRef = id.reqTransactionReferenceNo;
      String DebitAccountno = id.reqAccountNo;
      // String CreditAccountno = id.reqBeneficiaryaccount;
      String DebitAccountCurrency = id.reqDebitAcCurrency;
      String DebitAmount = id.reqDebitAmount;
      String UnitId = id.reqUnitID;
      String channelId = id.reqChnlId;
      // String CreditAccountCurrency = id.reqCreditAcCurrency;
      String CreditAccountno = "";
      String BicCode = id.reqBeneficiaryBankBIC;

      VIPMarker = id.vipMarker.equalsIgnoreCase(ResponseStatus.Y.getValue()) ? "VIP" : "";
      WaiveCharge = id.waiveCharge.equalsIgnoreCase(ResponseStatus.N.getValue()) ? "false" : "true";

      String CBXReference = "";
      System.out.println(
          "Fetching KESACCO Transfer Details Transaction Id [" + SourceUniqRef + "]");
      // System.out.println("KESACCO Prefix [" + GlobalParameters.get("CBX_PREFIX") + "]");

      // Reference Generator
      try {
        ReferenceObject reference =
            coreServices.generateReference(new ReferenceRequest(SourceUniqRef));
        UniqueReference = reference.getUniqueReference();
        // CBXReference = GlobalParameters.get("CBX_PREFIX") + "" + UniqueReference;
        System.out.println("Unique Reference [" + UniqueReference + "]");
      } catch (Exception e) {
        e.printStackTrace();
        ResponseMessages(
            saccoObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            VIPErrorDesc,
            CBXReference,
            UnitId,
            ResponseStatus.UNIQUE_REFERENCE_GENERATED_FAILED.getValue(),
            transID);
        return Response.status(Status.ACCEPTED).entity(saccoObj).build();
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
              saccoObj,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.NOT_FOUND,
              VIPErrorDesc,
              CBXReference,
              UnitId,
              ResponseStatus.TRANSACTION_ALREADY_LOCKED.getValue(),
              transID);
          return Response.status(Status.ACCEPTED).entity(saccoObj).build();
        }
      } catch (Exception e) {
        e.printStackTrace();

        ResponseMessages(
            saccoObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            VIPErrorDesc,
            CBXReference,
            UnitId,
            ResponseStatus.LOCKING_TRANSACTION_UNSUCCESSFUL.getValue(),
            transID);
        return Response.status(Status.ACCEPTED).entity(saccoObj).build();
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
                saccoObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                VIPErrorDesc,
                CBXReference,
                UnitId,
                ResponseStatus.DUPLICATE_DATA.getValue(),
                transID);
            return Response.status(Status.ACCEPTED).entity(saccoObj).build();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        ResponseMessages(
            saccoObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            VIPErrorDesc,
            CBXReference,
            UnitId,
            ResponseStatus.VALIDATION_TRANSACTION_UNSUCCESSFUL.getValue(),
            transID);
        return Response.status(Status.ACCEPTED).entity(saccoObj).build();
      }

      // Custom validation for checking Biccode
      /*
       * try (Connection dbConnection = cmDBPool.getConnection(); PreparedStatement BicPS =
       * dbConnection.prepareStatement("select * from "+coreDBSchema+".BC$SORT$CODE" +
       * " where ID=?")){ BicPS.setString(1, BicCode); ResultSet BicRS = BicPS.executeQuery();
       * if(!BicRS.next()) { ResponseMessages(saccoObj, SourceUniqRef, UniqueReference,
       * ERROR_CODE.NOT_FOUND, ResponseStatus.FAILED.getValue(), CBXReference, UnitId,
       * ResponseStatus.BIC_NOT_FOUND.getValue()); return
       * Response.status(Status.ACCEPTED).entity(saccoObj).build(); }
       *
       * BicRS.close(); BicPS.close(); } catch (Exception e) { e.printStackTrace();
       * ResponseMessages(saccoObj, SourceUniqRef, UniqueReference,
       * ERROR_CODE.NOT_FOUND,ResponseStatus.FAILED.getValue(), CBXReference,UnitId,
       * ResponseStatus.CUSTOM_VALIDATION_UNSUCCESSFUL.getValue()); return
       * Response.status(Status.ACCEPTED).entity(saccoObj).build(); }
       */

      // Validate Financial Transaction
      try {
        if (DuplicateCheck.equalsIgnoreCase(ResponseStatus.UNIQUE_DATA.getValue())) {
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
          ValFinErrDetails = ValFinTrans.getErrorDetail();
          System.out.println(
              "["
                  + UniqueReference
                  + "] Validate Financial Transaction Status ["
                  + ValidationStatus
                  + "]");

          FiancialDetailsMap = new LinkedHashMap<>();
          Field[] resfields = ValidateFinancialTransObject.class.getDeclaredFields();
          for (Field field : resfields) {
            if (field.isAnnotationPresent(JsonbProperty.class)) {
              String annotationValue = field.getAnnotation(JsonbProperty.class).value();
              if (field.get(ValFinTrans) != null)
                FiancialDetailsMap.put(
                    annotationValue, field.get(ValFinTrans).toString().replaceAll("\"", "\\\""));
            }
          }

          if (ValidationStatus.equalsIgnoreCase(ResponseStatus.FAILED.getValue())) {
            ResponseMessages(
                saccoObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                VIPErrorDesc,
                CBXReference,
                UnitId,
                ValFinTrans.getErrorDetail(),
                transID);
            return Response.status(Status.ACCEPTED).entity(saccoObj).build();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        ResponseMessages(
            saccoObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            VIPErrorDesc,
            CBXReference,
            UnitId,
            ResponseStatus.VALIDATING_FINANCIAL_TRANSACTION_UNSUCCESSFUL.getValue(),
            transID);
        return Response.status(Status.ACCEPTED).entity(saccoObj).build();
      }

      // OFS Formatting
      CreditAccountno = GlobalParameters.get("SACCO_CREDIT_ACCT_NUMBER");
      try {

        if (ValidationStatus.equalsIgnoreCase(ResponseStatus.SUCCESS.getValue())) {

          OFSData = new LinkedHashMap<String, String>();
          Field[] fields = KeSaccoTransferRequest.class.getDeclaredFields();
          OFSData.put("UniqueReference", UniqueReference);
          OFSData.put("FTReference", CBXReference);
          for (Field field : fields) {
            if (field.isAnnotationPresent(JsonbProperty.class)) {
              String annotationValue = field.getAnnotation(JsonbProperty.class).value();
              if (field.get(id) != null)
                OFSData.put(annotationValue, field.get(id).toString().replaceAll("\"", "\\\""));
            }
          }

          OFSData.put("CreditAccountNo", CreditAccountno);
          OFSFormatObject OFSFormat =
              coreServices.OFSFormatter(new OFSFormatRequest(ServiceName, OFSData));
          OFSStatus = OFSFormat.getStatus();
          OFSMsg = OFSFormat.getOfsMsg();
          System.out.println(
              "["
                  + UniqueReference
                  + "] KESACCO OFS Formatter Status ["
                  + OFSStatus
                  + "] OFS Msg ["
                  + OFSMsg
                  + "]");

          if (OFSStatus.equalsIgnoreCase(ResponseStatus.SERVICE_MAPPING_NOT_FOUND.getValue())) {
            ResponseMessages(
                saccoObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                VIPErrorDesc,
                CBXReference,
                UnitId,
                ResponseStatus.SERVICE_MAPPING_NOT_FOUND.getValue(),
                transID);

            return Response.status(Status.ACCEPTED).entity(saccoObj).build();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        ResponseMessages(
            saccoObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            VIPErrorDesc,
            CBXReference,
            UnitId,
            ResponseStatus.OFS_FORMATTING_UNSUCCESSFUL.getValue(),
            transID);
        return Response.status(Status.ACCEPTED).entity(saccoObj).build();
      }

      // Queueing and lock amount Transactions
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
              "[" + UniqueReference + "] Queued KESACCO Transaction Status [" + QueueStatus + "]");

          if (QueueStatus.equalsIgnoreCase(ResponseStatus.FAILED.getValue())) {
            ResponseMessages(
                saccoObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                VIPErrorDesc,
                CBXReference,
                UnitId,
                QueuedTrans.getErrorDetail(),
                transID);

            return Response.status(Status.ACCEPTED).entity(saccoObj).build();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        ResponseMessages(
            saccoObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            VIPErrorDesc,
            CBXReference,
            UnitId,
            ResponseStatus.QUEUING_TRANSACTIONS_FAILED.getValue(),
            transID);
        return Response.status(Status.ACCEPTED).entity(saccoObj).build();
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
              saccoObj,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.SUCCESSFUL,
              ResponseStatus.SUCCESS.getValue(),
              CBXReference,
              UnitId,
              ErrorMessage,
              transID);

          OFSData.remove("UniqueReference");
          OFSData.remove("FTReference");
          Gson gson = new Gson();
          String RequestData = gson.toJson(OFSData);
          RequestData = RequestData.replaceAll("\"", "\\\"");
          System.out.println("Json Request Data[" + RequestData + "]");

          RespDetailMap = new LinkedHashMap<>();
          Field[] resfields = KESaccoTransferObject.class.getDeclaredFields();
          for (Field field : resfields) {
            if (field.isAnnotationPresent(JsonbProperty.class)) {
              String annotationValue = field.getAnnotation(JsonbProperty.class).value();
              if (field.get(saccoObj) != null)
                RespDetailMap.put(
                    annotationValue, field.get(saccoObj).toString().replaceAll("\"", "\\\""));
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
              "["
                  + UniqueReference
                  + "] Persist KESACCO Unique Data Status ["
                  + PersistStatus
                  + "]");
        }
      } catch (Exception e) {
        e.printStackTrace();

        ResponseMessages(
            saccoObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.NOT_FOUND,
            VIPErrorDesc,
            CBXReference,
            UnitId,
            ResponseStatus.TRANSACTION_DETAIL_LOGGING_UNSUCCESSFUL.getValue(),
            transID);
        return Response.status(Status.ACCEPTED).entity(saccoObj).build();
      }

      return Response.status(Status.ACCEPTED).entity(saccoObj).build();

    } catch (Exception except) {
      except.printStackTrace();
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    } finally {
      RelLocks = coreServices.ReleaseLock(new ReleaseLockRequest(UniqueReference));
      System.out.println(
          "["
              + UniqueReference
              + "] Releasing KESACCO Account Lock Status ["
              + RelLocks.getStatus()
              + "]");
      RelLocks = coreServices.ReleaseLock(new ReleaseLockRequest(SourceUniqRef));
      System.out.println(
          "["
              + UniqueReference
              + "] Releasing KESACCO Transaction Lock Status ["
              + RelLocks.getStatus()
              + "]");

      LocalDateTime endTime = LocalDateTime.now();
      long millis = ChronoUnit.MILLIS.between(startTime, endTime);
      System.out.println(
          "Sacco Transfer Posting Completed and Processing Time Taken [ "
              + millis
              + " ] MilliSeconds");
    }
  }

  private void ResponseMessages(
      KESaccoTransferObject saccoObj,
      String SourceUniqRef,
      String UniqueReference,
      ERROR_CODE ErrorCode,
      String ErrorDesc,
      String CBXReference,
      String UnitId,
      String ErrorDescription,
      String transId) {

    saccoObj.setHdrTranId(transId);
    // saccoObj.setHdrRefNo(UniqueReference);
    saccoObj.setResResultCode(ErrorCode);
    saccoObj.setResCbxReferenceNo(SourceUniqRef);
    saccoObj.setResUserId(UnitId);
    saccoObj.setResStatusCode(ErrorCode);
    saccoObj.setResStatusDescription(ErrorDescription);
    saccoObj.setResErrorCode(ErrorCode);
    saccoObj.setResErrorDesc(ErrorDesc);
    saccoObj.setResErrorMessage(ErrorDescription);
    saccoObj.setResRequestID("");
    saccoObj.setResCoreReferenceNo(CBXReference);
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
                  + channelDBSchema
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
