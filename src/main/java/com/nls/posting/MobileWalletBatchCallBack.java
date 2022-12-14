package com.nls.posting;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nls.core.CoreServices;
import com.nls.core.DuplicateCheckObject;
import com.nls.core.DuplicateCheckRequest;
import com.nls.core.LockTransactionRequest;
import com.nls.core.LockTrasactionObject;
import com.nls.core.MobileMoneyCoreService;
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
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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

@Path("/MobileWalletCallBack")
public class MobileWalletBatchCallBack {

  private static HashMap<String, String> GlobalParameters;
  LinkedHashMap<String, String> ErrorResponseMap = null;
  private static DataSource cmDBPool;

  private static String coreDBSchema;
  private static String channelSchema;

  private static CoreServices coreServices;
  private static MobileMoneyCoreService moneyCoreService;

  public static void SetSchemaNames(String coreDBSchema, String channelSchema) {
    MobileWalletBatchCallBack.coreDBSchema = coreDBSchema;
    MobileWalletBatchCallBack.channelSchema = channelSchema;
  }

  public static void setDBPool(DataSource cmDBPool) {
    MobileWalletBatchCallBack.cmDBPool = cmDBPool;
  }

  public static void setInitiailizeGlobalParameters(HashMap<String, String> GlobalParameters) {
    MobileWalletBatchCallBack.GlobalParameters = GlobalParameters;
  }

  public static void setCoreServices(CoreServices coreServices) {
    MobileWalletBatchCallBack.coreServices = coreServices;
  }

  public static void setMobMoneyCoreServices(MobileMoneyCoreService moneyCoreService) {
    MobileWalletBatchCallBack.moneyCoreService = moneyCoreService;
  }

  // @Inject MobileMoneyCoreService moneyCoreService;

  @Timeout(value = 30, unit = ChronoUnit.SECONDS)
  @Counted()
  @POST
  @Traced()
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponseSchema(
      value = CallBackObjects.class,
      responseDescription = "MobileWallet CallBack Response",
      responseCode = "200")
  @Operation(
      summary = "MobileWallet CallBack Request",
      description = "returns MobileWallet CallBack data")
  public Response getBatchCallBackDetails(
      @RequestBody(
              description = "Batch Reference No",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CallBackRequest.class)))
          CallBackRequest id) {

    System.out.println("************* Calling Mobile Wallet Batch File *****************");

    CallBackObjects CBObj = new CallBackObjects();
    MobileWalletBatchObject AccRespObj = new MobileWalletBatchObject();
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
    Gson g = new Gson();
    String RequestData = "";
    String ServiceName = id.getServiceName();
    String BatchReferenceNo = id.getBatchReferenceNo();

    try {
      Connection DbConnection = cmDBPool.getConnection();
      PreparedStatement CBFTBatchPs =
          DbConnection.prepareStatement(
              "select * from "
                  + channelSchema
                  + "."
                  + " BATCH$POSTING$QUEUE where BATCH_REFERENCE_NO=? and status='QUEUE'");
      CBFTBatchPs.setString(1, BatchReferenceNo);
      ResultSet CBFTBatchRs = CBFTBatchPs.executeQuery();
      while (CBFTBatchRs.next()) {

        System.out.println("*************Calling queue records************");

        try {
          RequestData = CBFTBatchRs.getString("REQUEST_DATA");
          JsonParser parser = new JsonParser();
          JsonObject MobileObj = (JsonObject) parser.parse(RequestData.toString());
          String Id = CBFTBatchRs.getString("ID");
          String TransId = MobileObj.get("hdrTranId").toString().replaceAll("\"", "");
          SourceUniqRef = MobileObj.get("reqRefNo").toString().replaceAll("\"", "");
          String DebitAccountno = MobileObj.get("reqAccNo").toString().replaceAll("\"", "");
          String CreditAccountno = "";
          String DebitAccountCurrency =
              MobileObj.get("reqDebitccy").toString().replaceAll("\"", "");
          String DebitAmount = MobileObj.get("reqDebitamt").toString().replaceAll("\"", "");
          String UnitId = MobileObj.get("reqUnitId").toString().replaceAll("\"", "");
          String channelId = MobileObj.get("reqChnlId").toString().replaceAll("\"", "");
          String mobileNumber = MobileObj.get("reqmobilenumber").toString().replaceAll("\"", "");
          String debitAcctCustomerId = "";
          String debitAccountName = "";

          System.out.println(
              "SourceUniqRef ["
                  + SourceUniqRef
                  + "],DebitAccountno ["
                  + DebitAccountno
                  + "],"
                  + "DebitAmount ["
                  + DebitAmount
                  + "],CreditAccountno["
                  + CreditAccountno
                  + "]");

          String CBXReference = "";
          VIPMarker =
              MobileObj.get("vipMarker")
                      .toString()
                      .replaceAll("\"", "")
                      .equalsIgnoreCase(ResponseStatus.Y.getValue())
                  ? "VIP"
                  : "";
          WaiveCharge =
              MobileObj.get("waiveCharge")
                      .toString()
                      .replaceAll("\"", "")
                      .equalsIgnoreCase(ResponseStatus.N.getValue())
                  ? "false"
                  : "true";

          System.out.println(
              "Fetching MobileWallet Batch Details Transaction Id [" + SourceUniqRef + "]");

          System.out.println("FT Prefix [" + GlobalParameters.get("CBX_PREFIX") + "]");
          // System.out.println("Initialized Rest Client [" + coreServices + "]");

          if (mobileNumber.startsWith("07") || mobileNumber.startsWith("01")) {
            mobileNumber = "254" + mobileNumber.substring(1);
          }

          // Reference Generator

          try {
            ReferenceObject reference =
                coreServices.generateReference(new ReferenceRequest(SourceUniqRef));
            UniqueReference = reference.getUniqueReference();
            System.out.println("Unique Reference [" + UniqueReference + "]");
          } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            ResponseUpdation(
                DbConnection,
                ServiceName,
                "FAILED",
                UniqueReference,
                SourceUniqRef,
                ERROR_CODE.NOT_FOUND,
                ResponseStatus.UNIQUE_REFERENCE_GENERATED_FAILED.getValue(),
                "TimedOut",
                channelSchema,
                Id,
                CBXReference,
                TransId,
                UnitId);
            continue;
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
            TransactionLockStatus =
                Locking.getLockSuccessful().equalsIgnoreCase("true") ? true : false;

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

              ResponseUpdation(
                  DbConnection,
                  ServiceName,
                  "FAILED",
                  UniqueReference,
                  SourceUniqRef,
                  ERROR_CODE.NOT_FOUND,
                  ResponseStatus.TRANSACTION_ALREADY_LOCKED.getValue(),
                  "",
                  channelSchema,
                  Id,
                  CBXReference,
                  TransId,
                  UnitId);
              continue;
            }
          } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            ResponseUpdation(
                DbConnection,
                ServiceName,
                "FAILED",
                UniqueReference,
                SourceUniqRef,
                ERROR_CODE.NOT_FOUND,
                ResponseStatus.LOCKING_TRANSACTION_UNSUCCESSFUL.getValue(),
                "TimedOut",
                channelSchema,
                Id,
                CBXReference,
                TransId,
                UnitId);
            continue;
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

                ResponseUpdation(
                    DbConnection,
                    ServiceName,
                    "FAILED",
                    UniqueReference,
                    SourceUniqRef,
                    ERROR_CODE.NOT_FOUND,
                    ResponseStatus.DUPLICATE_DATA.getValue(),
                    "",
                    channelSchema,
                    Id,
                    CBXReference,
                    TransId,
                    UnitId);

                continue;
              }
            }
          } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            ResponseUpdation(
                DbConnection,
                ServiceName,
                "FAILED",
                UniqueReference,
                SourceUniqRef,
                ERROR_CODE.NOT_FOUND,
                ResponseStatus.VALIDATION_TRANSACTION_UNSUCCESSFUL.getValue(),
                "TimedOut",
                channelSchema,
                Id,
                CBXReference,
                TransId,
                UnitId);
            continue;
          }

          // Custom validation

          // Validate Financial Transaction

          try {
            if (DuplicateCheck.equalsIgnoreCase(ResponseStatus.UNIQUE_DATA.getValue())) {

              System.out.println(
                  "VipMarker [" + VIPMarker + "] Waive charge [" + WaiveCharge + "]");

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
              debitAcctCustomerId = ValFinTrans.getDebitCustomerId();
              debitAccountName = ValFinTrans.getDebitAccTitle();

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
                        annotationValue,
                        field.get(ValFinTrans).toString().replaceAll("\"", "\\\""));
                }
              }

              if (ValidationStatus.equalsIgnoreCase(ResponseStatus.FAILED.getValue())) {
                ResponseUpdation(
                    DbConnection,
                    ServiceName,
                    "FAILED",
                    UniqueReference,
                    SourceUniqRef,
                    ERROR_CODE.NOT_FOUND,
                    ValFinTrans.getErrorDetail(),
                    "",
                    channelSchema,
                    Id,
                    CBXReference,
                    TransId,
                    UnitId);
                continue;
              }
            }
          } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            ResponseUpdation(
                DbConnection,
                ServiceName,
                "FAILED",
                UniqueReference,
                SourceUniqRef,
                ERROR_CODE.NOT_FOUND,
                ResponseStatus.VALIDATING_FINANCIAL_TRANSACTION_UNSUCCESSFUL.getValue(),
                "TimedOut",
                channelSchema,
                Id,
                CBXReference,
                TransId,
                UnitId);
            continue;
          }

          // OFS Formatting

          /*
           * OFSData.put("DebitAccount", DebitAccountno); OFSData.put("TransactionAmount",
           * DebitAmount);
           */
          try {
            if (ValidationStatus.equalsIgnoreCase(ResponseStatus.SUCCESS.getValue())
                || ValidationStatus.equalsIgnoreCase(ResponseStatus.PENDING.getValue())) {

              // System.out.println("VIP Error Details ["+VIPErrorDesc+"]");

              OFSData = new LinkedHashMap<String, String>();
              Field[] fields = MobileWalletBatchRequestData.class.getDeclaredFields();
              OFSData.put("UniqueReference", UniqueReference);
              OFSData.put("FTReference", GlobalParameters.get("CBX_PREFIX") + "" + UniqueReference);
              for (Field field : fields) {
                if (field.isAnnotationPresent(JsonbProperty.class)) {
                  String annotationValue = field.getAnnotation(JsonbProperty.class).value();
                  if (MobileObj.get(field.getName()) != null)
                    OFSData.put(
                        annotationValue,
                        MobileObj.get(field.getName()).toString().replaceAll("\"", "\\\""));
                }
              }

              CreditAccountno = GlobalParameters.get("MOB_MONEY_CREDIT_ACCNO");
              OFSData.put("CreditAccountNo", CreditAccountno);

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
                ResponseUpdation(
                    DbConnection,
                    ServiceName,
                    "FAILED",
                    UniqueReference,
                    SourceUniqRef,
                    ERROR_CODE.NOT_FOUND,
                    ResponseStatus.SERVICE_MAPPING_NOT_FOUND.getValue(),
                    "",
                    channelSchema,
                    Id,
                    CBXReference,
                    TransId,
                    UnitId);
                continue;
              }
            }
          } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            ResponseUpdation(
                DbConnection,
                ServiceName,
                "FAILED",
                UniqueReference,
                SourceUniqRef,
                ERROR_CODE.NOT_FOUND,
                ResponseStatus.OFS_FORMATTING_UNSUCCESSFUL.getValue(),
                "TimedOut",
                channelSchema,
                Id,
                CBXReference,
                TransId,
                UnitId);
            continue;
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
                ResponseUpdation(
                    DbConnection,
                    ServiceName,
                    "FAILED",
                    UniqueReference,
                    SourceUniqRef,
                    ERROR_CODE.NOT_FOUND,
                    QueuedTrans.getErrorDetail(),
                    "",
                    channelSchema,
                    Id,
                    CBXReference,
                    TransId,
                    UnitId);
                continue;
              }
            }
          } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            ResponseUpdation(
                DbConnection,
                ServiceName,
                "FAILED",
                UniqueReference,
                SourceUniqRef,
                ERROR_CODE.NOT_FOUND,
                ResponseStatus.QUEUING_TRANSACTIONS_FAILED.getValue(),
                "Timedout",
                channelSchema,
                Id,
                CBXReference,
                TransId,
                UnitId);
            continue;
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

              OFSData.remove("UniqueReference");
              OFSData.remove("FTReference");
              Gson gson = new Gson();
              RequestData = gson.toJson(OFSData);
              RequestData = RequestData.replaceAll("\"", "\\\"");
              System.out.println("Json Request Data[" + RequestData + "]");

              RespDetailMap = new LinkedHashMap<>();
              Field[] resfields = MobileWalletBatchObject.class.getDeclaredFields();
              for (Field field : resfields) {
                if (field.isAnnotationPresent(JsonbProperty.class)) {
                  String annotationValue = field.getAnnotation(JsonbProperty.class).value();
                  if (field.get(AccRespObj) != null)
                    RespDetailMap.put(
                        annotationValue, field.get(AccRespObj).toString().replaceAll("\"", "\\\""));
                }
              }
              RespDetailMap.putAll(FiancialDetailsMap);

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

              RespDetailMap.put("UniqueReference", SourceUniqRef);
              RespDetailMap.put("CBXReference", CBXReference);

              gson = new Gson();
              String ResponseData = gson.toJson(RespDetailMap);
              ResponseData = ResponseData.replaceAll("\"", "\\\"");
              System.out.println("Json Response Data[" + ResponseData + "]");

              ResponseUpdation(
                  DbConnection,
                  ServiceName,
                  "SUCCESS",
                  UniqueReference,
                  SourceUniqRef,
                  ERROR_CODE.SUCCESSFUL,
                  ErrorMessage,
                  VIPErrorDesc,
                  channelSchema,
                  Id,
                  CBXReference,
                  TransId,
                  UnitId);

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
            ResponseUpdation(
                DbConnection,
                ServiceName,
                "FAILED",
                UniqueReference,
                SourceUniqRef,
                ERROR_CODE.NOT_FOUND,
                ResponseStatus.TRANSACTION_DETAIL_LOGGING_UNSUCCESSFUL.getValue(),
                "Timedout",
                channelSchema,
                Id,
                CBXReference,
                TransId,
                UnitId);
            continue;
          }
        } catch (Exception except) {
          except.printStackTrace();
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
        }
      }
      return Response.status(Status.ACCEPTED).entity(CBObj).build();
    } catch (Exception except) {
      except.printStackTrace();
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  public void ResponseUpdation(
      Connection dbconnection,
      String serviceName,
      String Status,
      String UniqueReference,
      String SourceUniqRef,
      ERROR_CODE errorCode,
      String ErrorDesc,
      String ErrorReason,
      String channelDBSchema,
      String Id,
      String CBXReference,
      String TransId,
      String UnitId)
      throws SQLException {

    System.out.println("************ Calling Response Updation Method ***********************");

    ErrorResponseMap = new LinkedHashMap<String, String>();
    ErrorResponseMap.put("hdr_Tran_Id", TransId);
    ErrorResponseMap.put("hdr_Ref_No", "1");
    ErrorResponseMap.put("res_Result_Code", errorCode.toString());
    ErrorResponseMap.put("res_Cbx_Reference_No", SourceUniqRef);
    ErrorResponseMap.put("res_User_Id", UnitId);
    ErrorResponseMap.put("res_StatusCode", errorCode.toString());
    ErrorResponseMap.put("res_StatusDescription", ErrorDesc);
    ErrorResponseMap.put("res_Description", ErrorReason);
    ErrorResponseMap.put("res_CoreReferenceNo", CBXReference);
    ErrorResponseMap.put("UniqueReference", UniqueReference);

    Gson g = new Gson();
    String ErrorResponseData = g.toJson(ErrorResponseMap);
    ErrorResponseData = ErrorResponseData.replaceAll("\"", "\\\"");

    PreparedStatement BatchUpdatePs =
        dbconnection.prepareStatement(
            "select tbl.* from " + channelDBSchema + ".BATCH$POSTING$QUEUE tbl" + " where tbl.id=?",
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
    BatchUpdatePs.setString(1, Id);
    ResultSet BatchUpdateRs = BatchUpdatePs.executeQuery();
    if (BatchUpdateRs.next()) {
      BatchUpdateRs.updateString("RESPONSE_DATA", ErrorResponseData);
      BatchUpdateRs.updateString("CORE_REFERENCE_NO", CBXReference);
      BatchUpdateRs.updateString("STATUS", Status);
      BatchUpdateRs.updateRow();
    }
    BatchUpdatePs.close();
    BatchUpdateRs.close();
    if (dbconnection.getAutoCommit() == false) dbconnection.commit();
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
                  + channelSchema
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
}
