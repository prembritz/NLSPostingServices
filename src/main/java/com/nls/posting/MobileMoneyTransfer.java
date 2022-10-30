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
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
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

@Path("/MobileMoneyTransfer")
public class MobileMoneyTransfer {
  private static DataSource cmDBPool;
  private static NumberFormat numberFormat = new DecimalFormat("###0.00");
  private static HashMap<String, String> ActualTableName;
  private static HashMap<String, String> GlobalParameters;
  private static String coreDBSchema;
  private static String channelDBSchema;

  @Inject CoreServices coreServices;

  // @Inject
  // MobileMoneyCoreService moneyCoreService;

  public static void setDBPool(DataSource cmDBPool) {
    MobileMoneyTransfer.cmDBPool = cmDBPool;
  }

  public static void setExternalTablenames(HashMap<String, String> ActualTableName) {
    MobileMoneyTransfer.ActualTableName = ActualTableName;
  }

  public static void SetSchemaNames(String coreDBSchema, String channelDBSchema) {
    MobileMoneyTransfer.coreDBSchema = coreDBSchema;
    MobileMoneyTransfer.channelDBSchema = channelDBSchema;
  }

  public static void setInitiailizeGlobalParameters(HashMap<String, String> GlobalParameters) {
    MobileMoneyTransfer.GlobalParameters = GlobalParameters;
  }

  @Timeout(value = 5, unit = ChronoUnit.SECONDS)
  @Counted()
  @POST
  @Traced()
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponseSchema(
      value = MobileMoneyTransferObject.class,
      responseDescription = "Mobile Money Transfer Response",
      responseCode = "200")
  @Operation(
      summary = "Mobile Money Transfer Request",
      description = "returns Mobile Money Transfer data")
  public Response getMobileMoneyTransferDetails(
      @RequestBody(
              description = "Transaction ID",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = MobileMoneyTransferRequest.class)))
          MobileMoneyTransferRequest id) {

    MobileMoneyTransferObject walletObj = new MobileMoneyTransferObject();
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
      System.out.println("Mobile Money Transfer Posting Started on [" + startTime + "]");
      String ServiceName = "MobileMoneyTransfer";
      TransId = id.hdrTranId;
      SourceUniqRef = id.reqCBXRefNo;
      String DebitAccountno = id.reqAccNo;
      // String CreditAccountno = id.reqCardNo;
      String CreditAccountno = "";
      String DebitAccountCurrency = id.reqDebitccy;
      String DebitAmount = id.reqDebitamt;
      String UnitId = id.reqUnitId;
      String channelId = id.reqChnlId;
      String debitAcctCustomerId = "";
      String CBXReference = "";
      String mobileNumber = id.reqMobilenumber;
      VIPMarker = id.vipMarker.equalsIgnoreCase(ResponseStatus.Y.getValue()) ? "VIP" : "";
      WaiveCharge = id.waiveCharge.equalsIgnoreCase(ResponseStatus.N.getValue()) ? "false" : "true";

      if (mobileNumber.startsWith("07") || mobileNumber.startsWith("01")) {
        mobileNumber = "254" + mobileNumber.substring(1);
      }

      String debitAccountName = "";

      System.out.println(
          "Fetching Mobile Money Transfer Details Transaction Id [" + SourceUniqRef + "]");
      System.out.println(
          "Mobile Money Transfer Prefix [" + GlobalParameters.get("CBX_PREFIX") + "]");

      // Reference Generator
      try {
        ReferenceObject reference =
            coreServices.generateReference(new ReferenceRequest(SourceUniqRef));
        UniqueReference = reference.getUniqueReference();
        System.out.println("Unique Reference [" + UniqueReference + "]");
      } catch (Exception e) {
        e.printStackTrace();
        ResponseMessages(
            walletObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            VIPErrorDesc,
            CBXReference,
            UnitId,
            ResponseStatus.UNIQUE_REFERENCE_GENERATED_FAILED.getValue(),
            TransId,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(walletObj).build();
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
              walletObj,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.NOT_FOUND,
              VIPErrorDesc,
              CBXReference,
              UnitId,
              ResponseStatus.TRANSACTION_ALREADY_LOCKED.getValue(),
              TransId,
              ResponseStatus.TRANSACTION_ALREADY_LOCKED.getValue());
          return Response.status(Status.ACCEPTED).entity(walletObj).build();
        }
      } catch (Exception e) {
        e.printStackTrace();

        ResponseMessages(
            walletObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            VIPErrorDesc,
            CBXReference,
            UnitId,
            ResponseStatus.LOCKING_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(walletObj).build();
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
                walletObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                VIPErrorDesc,
                CBXReference,
                UnitId,
                ResponseStatus.DUPLICATE_DATA.getValue(),
                TransId,
                ResponseStatus.DUPLICATE_DATA.getValue());
            return Response.status(Status.ACCEPTED).entity(walletObj).build();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        ResponseMessages(
            walletObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            VIPErrorDesc,
            CBXReference,
            UnitId,
            ResponseStatus.VALIDATION_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(walletObj).build();
      }

      // Custom validation for checking Biccode

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

          debitAcctCustomerId = ValFinTrans.getDebitCustomerId();
          debitAccountName = ValFinTrans.getDebitAccTitle();

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
                walletObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                VIPErrorDesc,
                CBXReference,
                UnitId,
                ValFinTrans.getErrorDetail(),
                TransId,
                ValFinTrans.getErrorDetail());
            return Response.status(Status.ACCEPTED).entity(walletObj).build();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        ResponseMessages(
            walletObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            VIPErrorDesc,
            CBXReference,
            UnitId,
            ResponseStatus.VALIDATING_FINANCIAL_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(walletObj).build();
      }

      // OFS Formatting
      // CreditAccountno=GlobalParameters.get("EFT_CREDIT_ACCT_NUMBER");
      try {

        if (ValidationStatus.equalsIgnoreCase(ResponseStatus.SUCCESS.getValue())
            || ValidationStatus.equalsIgnoreCase(ResponseStatus.PENDING.getValue())) {

          System.out.println("VIP Error Details [" + VIPErrorDesc + "]");

          OFSData = new LinkedHashMap<String, String>();
          Field[] fields = MobileMoneyTransferRequest.class.getDeclaredFields();
          OFSData.put("UniqueReference", UniqueReference);
          OFSData.put("FTReference", CBXReference);
          for (Field field : fields) {
            if (field.isAnnotationPresent(JsonbProperty.class)) {
              String annotationValue = field.getAnnotation(JsonbProperty.class).value();
              if (field.get(id) != null)
                OFSData.put(annotationValue, field.get(id).toString().replaceAll("\"", "\\\""));
            }
          }
          CreditAccountno = GlobalParameters.get("MOB_MONEY_CREDIT_ACCNO");
          OFSData.put("CreditAccountNo", CreditAccountno);

          // OFSData.put("ReqCreditBeneficiaryAccount", CreditAccountno);
          OFSFormatObject OFSFormat =
              coreServices.OFSFormatter(new OFSFormatRequest(ServiceName, OFSData));
          OFSStatus = OFSFormat.getStatus();
          OFSMsg = OFSFormat.getOfsMsg();
          System.out.println(
              "["
                  + UniqueReference
                  + "] Mobile Money Transfer OFS Formatter Status ["
                  + OFSStatus
                  + "] OFS Msg ["
                  + OFSMsg
                  + "]");

          if (OFSStatus.equalsIgnoreCase(ResponseStatus.SERVICE_MAPPING_NOT_FOUND.getValue())) {
            ResponseMessages(
                walletObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                VIPErrorDesc,
                CBXReference,
                UnitId,
                ResponseStatus.SERVICE_MAPPING_NOT_FOUND.getValue(),
                TransId,
                ResponseStatus.SERVICE_MAPPING_NOT_FOUND.getValue());

            return Response.status(Status.ACCEPTED).entity(walletObj).build();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        ResponseMessages(
            walletObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            VIPErrorDesc,
            CBXReference,
            UnitId,
            ResponseStatus.OFS_FORMATTING_UNSUCCESSFUL.getValue(),
            TransId,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(walletObj).build();
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
              "["
                  + UniqueReference
                  + "] Queued Mobile Money Transfer Transaction Status ["
                  + QueueStatus
                  + "]");

          if (QueueStatus.equalsIgnoreCase(VIPErrorDesc)) {
            ResponseMessages(
                walletObj,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                VIPErrorDesc,
                CBXReference,
                UnitId,
                QueuedTrans.getErrorDetail(),
                TransId,
                QueuedTrans.getErrorDetail());

            return Response.status(Status.ACCEPTED).entity(walletObj).build();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        ResponseMessages(
            walletObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            VIPErrorDesc,
            CBXReference,
            UnitId,
            ResponseStatus.QUEUING_TRANSACTIONS_FAILED.getValue(),
            TransId,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(walletObj).build();
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
              walletObj,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.SUCCESSFUL,
              VIPErrorDesc,
              CBXReference,
              UnitId,
              ErrorMessage,
              TransId,
              ErrorMessage);

          OFSData.remove("UniqueReference");
          OFSData.remove("FTReference");
          Gson gson = new Gson();
          String RequestData = gson.toJson(OFSData);
          RequestData = RequestData.replaceAll("\"", "\\\"");
          System.out.println("Mobile Money Json Request Data[" + RequestData + "]");

          RespDetailMap = new LinkedHashMap<>();
          Field[] resfields = MobileMoneyTransferObject.class.getDeclaredFields();
          for (Field field : resfields) {
            if (field.isAnnotationPresent(JsonbProperty.class)) {
              String annotationValue = field.getAnnotation(JsonbProperty.class).value();
              if (field.get(walletObj) != null)
                RespDetailMap.put(
                    annotationValue, field.get(walletObj).toString().replaceAll("\"", "\\\""));
            }
          }
          RespDetailMap.putAll(FiancialDetailsMap);

          /*
           * MobilePayloadCoreObject PayloadCoreObject=null; try { PayloadCoreObject =
           * moneyCoreService.MobileMoneyDirectPost(new MobilePayloadCoreRequest(DebitAccountno,
           * debitAcctCustomerId,CBXReference,DebitAmount,"OTHER","MP",UniqueReference,3,
           * mobileNumber,false, debitAccountName,"CM",mobileNumber,"RETAIL"));
           *
           *
           * RespDetailMap.put("Code",""+PayloadCoreObject.getCode());
           * RespDetailMap.put("Status",""+PayloadCoreObject.getStatus());
           * RespDetailMap.put("Description",""+PayloadCoreObject.getDescription());
           * RespDetailMap.put("MNO_ref",""+PayloadCoreObject.getmNO_Ref());
           * RespDetailMap.put("TransactionID",PayloadCoreObject.getTransactionID());
           *
           * insertMessageNotification(UniqueReference,PayloadCoreObject); } catch (Exception e) {
           * System.out.println("******catch******"); RespDetailMap.put("MobileMoneyTransferStatus",
           * "Timeout"); return Response.status(Status.INTERNAL_SERVER_ERROR).build(); }
           */

          Map<String, String> PayloadObj = new LinkedHashMap<>();
          PayloadObj.put("Account", "" + DebitAccountno);
          PayloadObj.put("ClientBase", "" + debitAcctCustomerId);
          PayloadObj.put("FTRef", "" + CBXReference);
          PayloadObj.put("Amount", "" + DebitAmount);
          PayloadObj.put("Receiver", "OTHER");

          PayloadObj.put("MsgType", "MP");
          PayloadObj.put("ChannelRef", "" + UniqueReference);
          PayloadObj.put("channelID", "3");
          PayloadObj.put("Mobile", "" + mobileNumber);
          PayloadObj.put("Bulk", "false");

          PayloadObj.put("Name", "" + debitAccountName);
          PayloadObj.put("ChannelName", "CM");
          PayloadObj.put("SenderMobile", "" + mobileNumber);
          PayloadObj.put("Business", "RETAIL");

          insertMessageNotification(UniqueReference, PayloadObj);

          gson = new Gson();
          String ResponseData = gson.toJson(RespDetailMap);
          ResponseData = ResponseData.replaceAll("\"", "\\\"");
          System.out.println("Mobile Money Transfer Json Response Data[" + ResponseData + "]");

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
                  + "] Persist Mobile Money Transfer Unique Data Status ["
                  + PersistStatus
                  + "]");
        }
      } catch (Exception e) {
        e.printStackTrace();

        ResponseMessages(
            walletObj,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            VIPErrorDesc,
            CBXReference,
            UnitId,
            ResponseStatus.TRANSACTION_DETAIL_LOGGING_UNSUCCESSFUL.getValue(),
            TransId,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(walletObj).build();
      }

      return Response.status(Status.ACCEPTED).entity(walletObj).build();

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
          "Mobile Money Transfer Posting Completed and Processing Time Taken [ "
              + millis
              + " ] MilliSeconds");
    }
  }

  private void ResponseMessages(
      MobileMoneyTransferObject walletObj,
      String SourceUniqRef,
      String UniqueReference,
      ERROR_CODE ErrorCode,
      String ErrorDesc,
      String CBXReference,
      String UnitId,
      String ErrorDescription,
      String TransId,
      String TimedoutMessage) {

    walletObj.setHdrTranId(TransId);
    // walletObj.setHdrRefNo(UniqueReference);
    walletObj.setResResultCode(ErrorCode);
    walletObj.setResCbxReferenceNo(SourceUniqRef);
    walletObj.setResUserId(UnitId);
    walletObj.setResStatusCode(ErrorCode);
    walletObj.setResStatusDescription(ErrorDescription);
    walletObj.setResErrorCode(ErrorCode);
    walletObj.setResErrorDesc(ErrorDesc);
    walletObj.setResErrorMessage(TimedoutMessage);
    walletObj.setResCoreReferenceNo(CBXReference);
  }

  public static void insertMessageNotification(
      String uniqueReference, Map<String, String> PayloadObj) throws SQLException {

    System.out.println("*****************************************");
    try {
      Connection connectionLocal = cmDBPool.getConnection();

      Gson gson = new Gson();
      String msgRequest = gson.toJson(PayloadObj);
      PreparedStatement queueStatement =
          connectionLocal.prepareStatement(
              "select tbl.* from "
                  + channelDBSchema
                  + "."
                  + " forward_queue_mobilemoney tbl where tbl.reference='DUMMY' ",
              ResultSet.TYPE_SCROLL_INSENSITIVE,
              ResultSet.CONCUR_UPDATABLE);
      ResultSet queueDetails = queueStatement.executeQuery();
      queueDetails.moveToInsertRow();
      queueDetails.updateString("REFERENCE", uniqueReference);
      queueDetails.updateString("MSG_SEND", msgRequest);
      queueDetails.updateInt("WAITING_STATUS", 1);
      queueDetails.insertRow();
      if (connectionLocal.getAutoCommit() == false) connectionLocal.commit();
      queueStatement.close();
      queueDetails.close();
      System.out.println(
          " Forwarding Mobile Money Message Inserted:[ "
              + uniqueReference
              + "] ["
              + msgRequest
              + "]");
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
      System.out.println("Mobile Money Transfer queue insertion is Failed !");
    }
  }

  /*
   * public static void insertMessageNotification(String uniqueReference,MobilePayloadCoreObject
   * PayloadCoreObject ) throws SQLException {
   *
   *
   * System.out.println("*****************************************"); try { Connection
   * connectionLocal = cmDBPool.getConnection(); Map<String, String> PayloadObj=new
   * LinkedHashMap<>(); PayloadObj.put("Code",""+PayloadCoreObject.getCode());
   * PayloadObj.put("Status",""+PayloadCoreObject.getStatus());
   * PayloadObj.put("Description",""+PayloadCoreObject.getDescription());
   * PayloadObj.put("MNO_ref",""+PayloadCoreObject.getmNO_Ref());
   * PayloadObj.put("TransactionID",PayloadCoreObject.getTransactionID());
   *
   * Gson gson = new Gson(); String msgRequest = gson.toJson(PayloadObj);
   * System.out.println("msgRequest["+msgRequest+"]"); PreparedStatement queueStatement =
   * connectionLocal.prepareStatement("select * from "+channelDBSchema+"." +
   * " forward_queue_mobilemoney where reference='DUMMY' ", 1005, 1008); ResultSet queueDetails =
   * queueStatement.executeQuery(); queueDetails.moveToInsertRow();
   * queueDetails.updateString("REFERENCE", uniqueReference); queueDetails.updateString("MSG_SEND",
   * msgRequest); queueDetails.updateInt("WAITING_STATUS", 1); queueDetails.insertRow();
   * connectionLocal.commit(); queueStatement.close(); queueDetails.close();
   * System.out.println(" Forwarding Mobile Money Message Inserted:[ " + uniqueReference + "] [" +
   * msgRequest + "]"); } catch (Exception e) { // TODO: handle exception e.printStackTrace(); }
   *
   * }
   */

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
