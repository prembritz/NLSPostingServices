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
import java.util.ArrayList;
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

@Path("/StopChequePayment")
public class StopChequePayment {

  private static String coreDBSchema;
  private static String channelDBSchema;
  private static DataSource cmDBPool;
  private static NumberFormat numberFormat = new DecimalFormat("###0.00");
  private static HashMap<String, String> ActualTableName;
  private static HashMap<String, String> GlobalParameters;
  private static String ChequeRegisterTable = "CHEQUE$REGISTER$SUPPLEMENT";

  private static CoreServices coreServices;

  public static void setCoreServices(CoreServices coreServices) {
    StopChequePayment.coreServices = coreServices;
  }

  public static void setDBPool(DataSource cmDBPool) {
    StopChequePayment.cmDBPool = cmDBPool;
  }

  public static void setExternalTablenames(HashMap<String, String> ActualTableName) {
    StopChequePayment.ActualTableName = ActualTableName;
  }

  public static void setInitiailizeGlobalParameters(HashMap<String, String> GlobalParameters) {
    StopChequePayment.GlobalParameters = GlobalParameters;
  }

  public static void SetSchemaNames(String coreDBSchema, String channelDBSchema) {
    StopChequePayment.coreDBSchema = coreDBSchema;
    StopChequePayment.channelDBSchema = channelDBSchema;
  }

  @Timeout(value = 3, unit = ChronoUnit.SECONDS)
  @Counted()
  @POST
  @Traced()
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponseSchema(
      value = StopChequePaymentObject.class,
      responseDescription = "Stop Cheque Payment Response",
      responseCode = "200")
  @Operation(
      summary = "Stop Cheque Payment Request",
      description = "returns Stop Cheque Payment data")
  public Response getStopChequePayment(
      @RequestBody(
              description = "Transaction ID",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = StopChequePaymentRequest.class)))
          StopChequePaymentRequest id) {

    StopChequePaymentObject stopCheque = new StopChequePaymentObject();
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
    String message = "";
    String ErrorMessage = ResponseStatus.SUCCESS.getValue();
    String ValFinErrDetails = "";

    LocalDateTime startTime = LocalDateTime.now();

    try {
      System.out.println("Stop Cheque Payment Posting Started on [" + startTime + "]");
      String ServiceName = "StopChequePayment";
      // SourceUniqRef = id.referenceNumber;
      String DebitAccountno = id.accountNo;
      String CreditAccountno = "";
      String DebitAccountCurrency = id.chequeCurrency;
      String DebitAmount = id.amountFrom;
      String UnitId = id.unitID;
      String channelId = "";
      String chequeType = id.chequeType;
      String firstChequeNo = id.firstChequeNumber;
      String lastChequeNo = id.lastChequeNumber;
      // String CreditAccountCurrency = id.reqCreditAcCurrency;

      TransId = id.hdrTranId;
      SourceUniqRef = id.reqTransactionReferenceNo;
      String CBXReference = "";
      VIPMarker = id.vipMarker.equals("1") ? "VIP" : "";
      WaiveCharge = id.waiveCharge.equals("0") ? "false" : "true";

      System.out.println("Cheque Tableref [" + ChequeRegisterTable + "]");
      // String chequeRegistTables = ActualTableName.get(UnitId + "-" + ChequeRegisterTable);
      // System.out.println("Cheque TableName ["+chequeRegistTables+"]");

      System.out.println("Fetching Stop Cheque Payment Transaction Id [" + SourceUniqRef + "]");

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
            stopCheque,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            CBXReference,
            UnitId,
            ResponseStatus.UNIQUE_REFERENCE_GENERATED_FAILED.getValue(),
            TransId,
            VIPErrorDesc,
            message,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(stopCheque).build();
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
              stopCheque,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.NOT_FOUND,
              CBXReference,
              UnitId,
              ResponseStatus.TRANSACTION_ALREADY_LOCKED.getValue(),
              TransId,
              VIPErrorDesc,
              message,
              ResponseStatus.TRANSACTION_ALREADY_LOCKED.getValue());
          return Response.status(Status.ACCEPTED).entity(stopCheque).build();
        }
      } catch (Exception e) {

        e.printStackTrace();
        ResponseMessages(
            stopCheque,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            CBXReference,
            UnitId,
            ResponseStatus.LOCKING_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc,
            message,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(stopCheque).build();
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
                stopCheque,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                ResponseStatus.DUPLICATE_DATA.getValue(),
                TransId,
                VIPErrorDesc,
                message,
                ResponseStatus.DUPLICATE_DATA.getValue());
            return Response.status(Status.ACCEPTED).entity(stopCheque).build();
          }
        }
      } catch (Exception e) {

        e.printStackTrace();
        ResponseMessages(
            stopCheque,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            CBXReference,
            UnitId,
            ResponseStatus.VALIDATION_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc,
            message,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(stopCheque).build();
      }

      // Custom validation for checking startcheq number and endcheq number
      if (!firstChequeNo.equals("") && !lastChequeNo.equals("")) {
        int StartRange = Integer.parseInt(firstChequeNo);
        String query = "";
        int endRange = Integer.parseInt(lastChequeNo);
        PreparedStatement chequPs = null;
        ResultSet chequeRs = null;
        boolean exist2 = false;
        boolean rangeCheck = false;
        Connection dbConnection = cmDBPool.getConnection();
        System.out.println("Start Range Lessthen end cheque:: " + (StartRange <= endRange));
        System.out.println("Start Range greterthen end cheque:: " + (StartRange > endRange));
        int i = 0;
        ArrayList<String> failedList = new ArrayList<String>();
        ArrayList<String> successList = new ArrayList<String>();

        while (StartRange <= endRange) {
          rangeCheck = true;
          // query = "SELECT REGEXP_SUBSTR(ID,'[0-9]+[.][0-9]+', 1,1) as ID,STATUS FROM
          // "+coreDBSchema+"."+ chequeRegistTables + " WHERE REGEXP_SUBSTR(ID,'[0-9]+[.][0-9]+',
          // 1,1)='" + DebitAccountno + "."+ StartRange + "' "
          // + " and STATUS NOT IN('CLEARED','STOPPED','CANCELLED','PRESENTED'";

          query =
              "SELECT REGEXP_SUBSTR(ID,'[0-9]+[.][0-9]+', 1,1) as ID,STATUS FROM "
                  + coreDBSchema
                  + "."
                  + ChequeRegisterTable
                  + " "
                  + " WHERE REGEXP_SUBSTR(ID,'[0-9]+[.][0-9]+', 1,1)='"
                  + DebitAccountno
                  + "."
                  + StartRange
                  + "'";

          System.out.println(query);
          chequPs = dbConnection.prepareStatement(query);
          chequeRs = chequPs.executeQuery();
          while (chequeRs.next()) {
            exist2 = true;
            System.out.println(StartRange + " Cheque Number is found ");
            if (chequeRs.getString("STATUS").equals("CLEARED")
                || chequeRs.getString("STATUS").equals("STOPPED")
                || chequeRs.getString("STATUS").equals("CLEARED")
                || chequeRs.getString("STATUS").equals("PRESENTED")) {
              failedList.add(chequeRs.getString("ID") + "-" + chequeRs.getString("STATUS"));
            } else {
              successList.add(chequeRs.getString("ID") + "-" + chequeRs.getString("STATUS"));
            }
            i++;
          }

          chequeRs.close();
          chequPs.clearParameters();
          chequPs.close();
          StartRange++;
        }

        System.out.println("failedList [" + failedList.size() + "]");
        System.out.println("successList [" + successList.size() + "]");

        if (!rangeCheck) {
          System.out.println("Please check cheque range!");
          ResponseMessages(
              stopCheque,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.NOT_FOUND,
              CBXReference,
              UnitId,
              ResponseStatus.CUSTOM_VALIDATION_UNSUCCESSFUL.getValue(),
              TransId,
              VIPErrorDesc,
              message,
              ResponseStatus.CUSTOM_VALIDATION_UNSUCCESSFUL.getValue());
          return Response.status(Status.ACCEPTED).entity(stopCheque).build();
        } else if (i < 0) {
          System.out.println("Account numbers is not found in given Cheque range!");
          ResponseMessages(
              stopCheque,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.NOT_FOUND,
              CBXReference,
              UnitId,
              ResponseStatus.CUSTOM_VALIDATION_UNSUCCESSFUL.getValue(),
              TransId,
              VIPErrorDesc,
              message,
              ResponseStatus.CUSTOM_VALIDATION_UNSUCCESSFUL.getValue());
          return Response.status(Status.ACCEPTED).entity(stopCheque).build();
        } else if (failedList.size() > 0) {
          for (int j = 0; j < failedList.size(); j++) {
            message += "," + failedList.get(j);
          }
          if (!message.equals("")) {
            message = message.substring(1);
          }
          System.out.println("Cheque ranges not found [" + message + "]");
          ResponseMessages(
              stopCheque,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.NOT_FOUND,
              CBXReference,
              UnitId,
              ResponseStatus.FAILED.getValue(),
              TransId,
              VIPErrorDesc,
              message,
              ResponseStatus.FAILED.getValue());
          return Response.status(Status.ACCEPTED).entity(stopCheque).build();
        }
      }

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
                stopCheque,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                ValFinTrans.getErrorDetail(),
                TransId,
                VIPErrorDesc,
                message,
                ValFinTrans.getErrorDetail());
            return Response.status(Status.ACCEPTED).entity(stopCheque).build();
          }
        }
      } catch (Exception e) {

        e.printStackTrace();
        ResponseMessages(
            stopCheque,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            CBXReference,
            UnitId,
            ResponseStatus.VALIDATING_FINANCIAL_TRANSACTION_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc,
            message,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(stopCheque).build();
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
          Field[] fields = StopChequePaymentRequest.class.getDeclaredFields();
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
                stopCheque,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                ResponseStatus.SERVICE_MAPPING_NOT_FOUND.getValue(),
                TransId,
                VIPErrorDesc,
                message,
                ResponseStatus.SERVICE_MAPPING_NOT_FOUND.getValue());
            return Response.status(Status.ACCEPTED).entity(stopCheque).build();
          }
        }
      } catch (Exception e) {

        e.printStackTrace();
        ResponseMessages(
            stopCheque,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            CBXReference,
            UnitId,
            ResponseStatus.OFS_FORMATTING_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc,
            message,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(stopCheque).build();
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
                stopCheque,
                SourceUniqRef,
                UniqueReference,
                ERROR_CODE.NOT_FOUND,
                CBXReference,
                UnitId,
                QueuedTrans.getErrorDetail(),
                TransId,
                VIPErrorDesc,
                message,
                QueuedTrans.getErrorDetail());
            return Response.status(Status.ACCEPTED).entity(stopCheque).build();
          }
        }
      } catch (Exception e) {

        e.printStackTrace();
        ResponseMessages(
            stopCheque,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            CBXReference,
            UnitId,
            ResponseStatus.QUEUING_TRANSACTIONS_FAILED.getValue(),
            TransId,
            VIPErrorDesc,
            message,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(stopCheque).build();
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
              stopCheque,
              SourceUniqRef,
              UniqueReference,
              ERROR_CODE.SUCCESSFUL,
              CBXReference,
              UnitId,
              ErrorMessage,
              TransId,
              VIPErrorDesc,
              message,
              ErrorMessage);

          OFSData.remove("UniqueReference");
          OFSData.remove("FTReference");
          Gson gson = new Gson();
          String RequestData = gson.toJson(OFSData);
          RequestData = RequestData.replaceAll("\"", "\\\"");
          System.out.println("Json Request Data[" + RequestData + "]");

          RespDetailMap = new LinkedHashMap<>();
          Field[] resfields = StopChequePaymentObject.class.getDeclaredFields();
          for (Field field : resfields) {
            if (field.isAnnotationPresent(JsonbProperty.class)) {
              String annotationValue = field.getAnnotation(JsonbProperty.class).value();
              if (field.get(stopCheque) != null)
                RespDetailMap.put(
                    annotationValue, field.get(stopCheque).toString().replaceAll("\"", "\\\""));
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

        e.printStackTrace();
        ResponseMessages(
            stopCheque,
            SourceUniqRef,
            UniqueReference,
            ERROR_CODE.TIMED_OUT,
            CBXReference,
            UnitId,
            ResponseStatus.TRANSACTION_DETAIL_LOGGING_UNSUCCESSFUL.getValue(),
            TransId,
            VIPErrorDesc,
            message,
            ResponseStatus.TIMED_OUT.getValue());
        return Response.status(Status.ACCEPTED).entity(stopCheque).build();
      }

      return Response.status(Status.ACCEPTED).entity(stopCheque).build();

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
          "Stop Cheque Payment Posting Completed and Processing Time Taken [ "
              + millis
              + " ] MilliSeconds");
    }
  }

  private void ResponseMessages(
      StopChequePaymentObject stopCheque,
      String SourceUniqRef,
      String UniqueReference,
      ERROR_CODE ErrorCode,
      String CBXReference,
      String UnitId,
      String ErrorDescription,
      String TransId,
      String ResDescription,
      String message,
      String TimedoutMessage) {

    stopCheque.setHdrTranId(TransId);
    // stopCheque.setHdrRefNo(UniqueReference);
    stopCheque.setResResultCode(ErrorCode);
    stopCheque.setResCbxReferenceNo(SourceUniqRef);
    stopCheque.setResUserId(UnitId);
    stopCheque.setResStatusCode(ErrorCode);
    stopCheque.setResStatusDescription(ErrorDescription);
    stopCheque.setResErrorCode(ErrorCode);
    stopCheque.setResErrorMessage(TimedoutMessage);
    stopCheque.setResCoreReferenceNo(CBXReference);
    stopCheque.setResDescription(ResDescription);
    stopCheque.setChequeRanges(message);
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
