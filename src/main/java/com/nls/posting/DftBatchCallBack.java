package com.nls.posting;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nls.core.CallBackRequest;
import com.nls.core.CoreServices;
import com.nls.core.DuplicateCheckObject;
import com.nls.core.DuplicateCheckRequest;
import com.nls.core.LockTransactionRequest;
import com.nls.core.LockTrasactionObject;
import com.nls.core.OFSFormatObject;
import com.nls.core.OFSFormatRequest;
import com.nls.core.PersistUniqueDataObject;
import com.nls.core.PersistUniqueDataRequest;
import com.nls.core.PesaLinkCoreObject;
import com.nls.core.PesaLinkCoreRequest;
import com.nls.core.PesaLinkCoreService;
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
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.inject.Inject;
import javax.json.bind.annotation.JsonbProperty;
import javax.sql.DataSource;
import javax.ws.rs.POST;
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

// @Path("/DFTCallBack")
public class DftBatchCallBack {

  private static HashMap<String, String> GlobalParameters;
  LinkedHashMap<String, String> ErrorResponseMap = null;
  private static DataSource cmDBPool;

  private static String coreDBSchema;
  private static String channelSchema;

  public static void SetSchemaNames(String coreDBSchema, String channelSchema) {
    DftBatchCallBack.coreDBSchema = coreDBSchema;
    DftBatchCallBack.channelSchema = channelSchema;
  }

  public static void setDBPool(DataSource cmDBPool) {
    DftBatchCallBack.cmDBPool = cmDBPool;
  }

  public static void setInitiailizeGlobalParameters(HashMap<String, String> GlobalParameters) {
    DftBatchCallBack.GlobalParameters = GlobalParameters;
  }

  @Inject CoreServices coreServices;

  @Inject PesaLinkCoreService pesaCoreServices;

  @Timeout(value = 30, unit = ChronoUnit.SECONDS)
  @Counted()
  @POST
  @Traced()
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponseSchema(
      value = CallBackObjects.class,
      responseDescription = "DFT CallBack Response",
      responseCode = "200")
  @Operation(summary = "DFT CallBack Request", description = "returns DFT CallBack data")
  public Response getDFTBatchCallBackDetails(
      @RequestBody(
              description = "Batch Reference No",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CallBackRequest.class)))
          CallBackRequest id) {

    System.out.println("************* Calling DFT Batch File *****************");

    DftBatchObject DFTRespObj = new DftBatchObject();
    CallBackObjects CBObj = new CallBackObjects();
    PesaLinkCoreObject LoopAccCoreObj = null;
    ReleaseLockObject RelLocks = null;
    LockTrasactionObject Locking = null;
    LinkedHashMap<String, String> OFSData = new LinkedHashMap<String, String>();
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
    String ServiceName = "DFTBatch";

    try {
      Connection DbConnection = cmDBPool.getConnection();
      PreparedStatement DFTBatchPs =
          DbConnection.prepareStatement(
              "select * from "
                  + channelSchema
                  + "."
                  + " BATCH$POSTING$QUEUE where service_name=? and status='QUEUE'");
      DFTBatchPs.setString(1, ServiceName);
      ResultSet DFTBatchRs = DFTBatchPs.executeQuery();
      while (DFTBatchRs.next()) {

        System.out.println("*************Calling queue records************");

        try {
          RequestData = DFTBatchRs.getString("REQUEST_DATA");
          JsonParser parser = new JsonParser();
          JsonObject DFTObj = (JsonObject) parser.parse(RequestData.toString());
          String Id = DFTBatchRs.getString("ID");
          String TransId = DFTObj.get("hdrTranId").toString().replaceAll("\"", "");
          String BeneficiaryAccount =
              DFTObj.get("reqBeneficiaryAccount").toString().replaceAll("\"", "");
          SourceUniqRef = DFTObj.get("reqTransactionReferenceNo").toString().replaceAll("\"", "");
          String DebitAccountno = DFTObj.get("reqAccountNo").toString().replaceAll("\"", "");
          String CreditAccountno =
              DFTObj.get("reqBeneficiaryAccount").toString().replaceAll("\"", "");
          String CreditAccountNumber = "";
          String DebitAccountCurrency =
              DFTObj.get("reqDebitAcCurrency").toString().replaceAll("\"", "");
          String DebitAmount = DFTObj.get("reqDebitAmount").toString().replaceAll("\"", "");
          String UnitId = DFTObj.get("reqUnitID").toString().replaceAll("\"", "");
          String channelId = DFTObj.get("reqChnlId").toString().replaceAll("\"", "");
          String BICCode = DFTObj.get("reqBeneficiaryBankBIC").toString().replaceAll("\"", "");
          String OFSServiceName =
              ServiceName + "-" + DFTObj.get("type").toString().replaceAll("\"", "");
          String ServiceType = DFTObj.get("type").toString().replaceAll("\"", "");

          String debitcustomername = "";
          String Bankid = DFTObj.get("reqBeneficiaryBankBIC").toString().replaceAll("\"", "");
          String CreditNarration = DFTObj.get("reqCrNarration").toString().replaceAll("\"", "");
          String custId = DFTObj.get("reqCIF").toString().replaceAll("\"", "");
          String creditCurrency = DFTObj.get("reqCreditAcCurrency").toString().replaceAll("\"", "");
          String tranType = "";
          String creditBenificiaryName =
              DFTObj.get("reqBeneficiaryName").toString().replaceAll("\"", "");

          System.out.println("DebitAccountno[" + DebitAccountno + "]");
          System.out.println("OFSServiceName[" + OFSServiceName + "]");

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
              DFTObj.get("vipMarker")
                      .toString()
                      .replaceAll("\"", "")
                      .equalsIgnoreCase(ResponseStatus.Y.getValue())
                  ? "VIP"
                  : "";
          WaiveCharge =
              DFTObj.get("waiveCharge")
                      .toString()
                      .replaceAll("\"", "")
                      .equalsIgnoreCase(ResponseStatus.N.getValue())
                  ? "false"
                  : "true";

          System.out.println("Fetching DFT Batch Details Transaction Id [" + SourceUniqRef + "]");

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

          System.out.println(
              "["
                  + OFSServiceName
                  + "],["
                  + ServiceType
                  + "],["
                  + ResponseStatus.PESALINK.getValue()
                  + "]");
          if (ServiceType.equals(ResponseStatus.RTGS.getValue())) {
            boolean Status =
                RTGSCustomValidation(
                    DbConnection,
                    BICCode,
                    OFSData,
                    SourceUniqRef,
                    UniqueReference,
                    CBXReference,
                    Id,
                    ServiceName,
                    TransId,
                    UnitId);
            if (!Status) continue;
            CreditAccountno = "";
            CreditAccountNumber = GlobalParameters.get(ServiceType + "_" + DebitAccountCurrency);
            OFSData.put("CreditAccountNo", CreditAccountNumber);
          } else if (ServiceType.equals(ResponseStatus.EFT.getValue())) {
            boolean Status =
                EFTCustomValidation(
                    DbConnection,
                    BICCode,
                    OFSData,
                    SourceUniqRef,
                    UniqueReference,
                    CBXReference,
                    Id,
                    ServiceName,
                    TransId,
                    UnitId);
            if (!Status) continue;
            CreditAccountno = "";
            CreditAccountNumber = GlobalParameters.get("EFT_CREDIT_ACCT_NUMBER");
            OFSData.put("CreditAccountNo", CreditAccountNumber);
          } else if (ServiceType.equals(ResponseStatus.PESALINK.getValue())) {
            CreditAccountno = "";
            CreditAccountNumber = GlobalParameters.get("PESALINK_CREDIT_ACCNO");
            OFSData.put("CreditAccountNo", CreditAccountNumber);
          } else if (ServiceType.equals(ResponseStatus.SACCO.getValue())) {
            CreditAccountno = "";
            CreditAccountNumber = GlobalParameters.get("SACCO_CREDIT_ACCT_NUMBER");
            OFSData.put("CreditAccountNo", CreditAccountNumber);
          } else {
            ResponseUpdation(
                DbConnection,
                ServiceName,
                "FAILED",
                UniqueReference,
                SourceUniqRef,
                ERROR_CODE.NOT_FOUND,
                ResponseStatus.INVALID_TRANSACTION_TYPE.getValue(),
                "",
                channelSchema,
                Id,
                CBXReference,
                TransId,
                UnitId);
            continue;
          }

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
              System.out.println(
                  "["
                      + UniqueReference
                      + "] Validate Financial Transaction Status ["
                      + ValidationStatus
                      + "]");
              ValFinErrDetails = ValFinTrans.getErrorDetail();
              debitcustomername = ValFinTrans.getDebitAccTitle();
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
              Field[] fields = DftBatchRequestData.class.getDeclaredFields();
              OFSData.put("UniqueReference", UniqueReference);
              OFSData.put("FTReference", GlobalParameters.get("CBX_PREFIX") + "" + UniqueReference);
              for (Field field : fields) {
                if (field.isAnnotationPresent(JsonbProperty.class)) {
                  String annotationValue = field.getAnnotation(JsonbProperty.class).value();
                  if (DFTObj.get(field.getName()) != null) {
                    OFSData.put(
                        annotationValue,
                        DFTObj.get(field.getName()).toString().replaceAll("\"", "\\\""));
                  }
                }
              }

              OFSFormatObject OFSFormat =
                  coreServices.OFSFormatter(new OFSFormatRequest(OFSServiceName, OFSData));
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
              Field[] resfields = DftBatchObject.class.getDeclaredFields();
              for (Field field : resfields) {
                if (field.isAnnotationPresent(JsonbProperty.class)) {
                  String annotationValue = field.getAnnotation(JsonbProperty.class).value();
                  if (field.get(DFTRespObj) != null)
                    RespDetailMap.put(
                        annotationValue, field.get(DFTRespObj).toString().replaceAll("\"", "\\\""));
                }
              }
              RespDetailMap.putAll(FiancialDetailsMap);

              if (ServiceType.equals(ResponseStatus.PESALINK.getValue())) {
                // PesaLinkCoreObject pesalinkObj=null;
                String pesalinkObj = "";
                try {
                  pesalinkObj =
                      pesaCoreServices.pesaLinkDirectPost(
                          new PesaLinkCoreRequest(
                              debitcustomername,
                              Bankid,
                              DebitAmount,
                              DebitAccountno,
                              CreditNarration,
                              creditBenificiaryName,
                              custId,
                              DebitAccountno,
                              BeneficiaryAccount,
                              creditCurrency,
                              tranType,
                              UniqueReference));
                  LoopAccCoreObj = gson.fromJson(pesalinkObj, PesaLinkCoreObject.class);
                  System.out.println(
                      "Pesalink Batch Direct Call Response =" + LoopAccCoreObj.getResponseDesc());
                  RespDetailMap.put("PesalinkResponseStatus", LoopAccCoreObj.getResponseDesc());
                } catch (Exception e) {
                  RespDetailMap.put("PesalinkResponseStatus", "Timeout");
                }
              }

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
                          CreditAccountNumber,
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
          RelLocks = coreServices.ReleaseLock(new ReleaseLockRequest(UniqueReference));
          System.out.println(
              "["
                  + UniqueReference
                  + "] Releasing Account Lock Status ["
                  + RelLocks.getStatus()
                  + "]");
          RelLocks = coreServices.ReleaseLock(new ReleaseLockRequest(SourceUniqRef));
          System.out.println(
              "["
                  + SourceUniqRef
                  + "] Releasing Transaction Lock Status ["
                  + RelLocks.getStatus()
                  + "]");
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

  private boolean RTGSCustomValidation(
      Connection dbConnection,
      String BICCode,
      LinkedHashMap<String, String> OFSData,
      String SourceUniqRef,
      String UniqueReference,
      String CBXReference,
      String Id,
      String ServiceName,
      String TransId,
      String UnitId)
      throws SQLException {

    System.out.println("UniqueReference[" + UniqueReference + "]");
    try (PreparedStatement FldMapPS =
            dbConnection.prepareStatement(
                "select * from "
                    + channelSchema
                    + ".t24$serv$fld$mappings "
                    + " where SERVICE_NAME=? AND FIELD_FORMATTER IS NOT NULL ");
        PreparedStatement SortCodePS =
            dbConnection.prepareStatement(
                "select * from " + coreDBSchema + ".BC$SORT$CODE" + " where ACC_WITH_BK_BIC=?");
        PreparedStatement BicPS =
            dbConnection.prepareStatement(
                "select * from " + coreDBSchema + ".DE$BIC " + " where ID=?")) {
      BicPS.setString(1, BICCode);
      ResultSet BicRS = BicPS.executeQuery();
      if (!BicRS.next()) {
        SortCodePS.setString(1, BICCode);
        ResultSet SortCodeRS = SortCodePS.executeQuery();
        if (!SortCodeRS.next()) {
          ResponseUpdation(
              dbConnection,
              ServiceName,
              "FAILED",
              UniqueReference,
              SourceUniqRef,
              ERROR_CODE.NOT_FOUND,
              ResponseStatus.BIC_NOT_FOUND.getValue(),
              "",
              channelSchema,
              Id,
              CBXReference,
              TransId,
              UnitId);
          return false;
          // return Response.status(Status.ACCEPTED).entity(RTGSObj).build();
        }
        SortCodeRS.close();
        SortCodePS.close();
      }
      BicRS.close();
      BicPS.close();

      OFSData = new LinkedHashMap<String, String>();

      FldMapPS.setString(1, ServiceName);
      ResultSet FldMapRS = FldMapPS.executeQuery();
      while (FldMapRS.next()) {
        OFSFieldFormatter FldFormatter =
            (OFSFieldFormatter) Class.forName(FldMapRS.getString("FIELD_FORMATTER")).newInstance();
        String FieldValue = FldFormatter.formattFieldValue(BICCode).toString();
        OFSData.put(FldMapRS.getString("INPUT_NAME"), FieldValue);
      }
      FldMapPS.close();
      FldMapRS.close();

      return true;
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
      ResponseUpdation(
          dbConnection,
          ServiceName,
          "FAILED",
          UniqueReference,
          SourceUniqRef,
          ERROR_CODE.NOT_FOUND,
          ResponseStatus.CUSTOM_VALIDATION_UNSUCCESSFUL.getValue(),
          "Timedout",
          channelSchema,
          Id,
          CBXReference,
          TransId,
          UnitId);
      return false;
      // return Response.status(Status.ACCEPTED).entity(RTGSObj).build();
    }
  }

  private boolean EFTCustomValidation(
      Connection dbConnection,
      String BICCode,
      LinkedHashMap<String, String> OFSData,
      String SourceUniqRef,
      String UniqueReference,
      String CBXReference,
      String Id,
      String ServiceName,
      String TransId,
      String UnitId)
      throws SQLException {

    try (PreparedStatement BicPS =
        dbConnection.prepareStatement(
            "select * from " + coreDBSchema + ".BC$SORT$CODE" + " where ID=?")) {
      BicPS.setString(1, BICCode);
      ResultSet BicRS = BicPS.executeQuery();
      if (!BicRS.next()) {
        ResponseUpdation(
            dbConnection,
            ServiceName,
            "FAILED",
            UniqueReference,
            SourceUniqRef,
            ERROR_CODE.NOT_FOUND,
            ResponseStatus.BIC_NOT_FOUND.getValue(),
            "",
            channelSchema,
            Id,
            CBXReference,
            TransId,
            UnitId);
        return false;
      }
      BicRS.close();
      BicPS.close();

      return true;
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
      ResponseUpdation(
          dbConnection,
          ServiceName,
          "FAILED",
          UniqueReference,
          SourceUniqRef,
          ERROR_CODE.NOT_FOUND,
          ResponseStatus.CUSTOM_VALIDATION_UNSUCCESSFUL.getValue(),
          "Timedout",
          channelSchema,
          Id,
          CBXReference,
          TransId,
          UnitId);
      return false;
      // return Response.status(Status.ACCEPTED).entity(RTGSObj).build();
    }
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
