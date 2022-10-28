package com.nls.posting;

import com.google.gson.Gson;
import com.nls.core.BatchCoreServices;
import com.nls.core.CallBackRequest;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

@Path("/SalaryProcessingBatch")
public class SalaryBatch {

  private static DataSource cmDBPool;
  private static NumberFormat numberFormat = new DecimalFormat("###0.00");
  private static HashMap<String, String> ActualTableName;
  private static HashMap<String, String> GlobalParameters;

  private static String coreDBSchema;
  private static String channelDBSchema;

  public static void SetSchemaNames(String coreDBSchema, String channelDBSchema) {
    SalaryBatch.coreDBSchema = coreDBSchema;
    SalaryBatch.channelDBSchema = channelDBSchema;
  }

  public static void setDBPool(DataSource cmDBPool) {
    SalaryBatch.cmDBPool = cmDBPool;
  }

  @Inject BatchCoreServices coreServices;

  @Timeout(value = 30, unit = ChronoUnit.SECONDS)
  @Counted()
  @POST
  @Traced()
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponseSchema(
      value = SalaryBatchHeaderObject.class,
      responseDescription = "Salary Batch Response",
      responseCode = "200")
  @Operation(summary = "Salary Batch Request", description = "returns Salary Batch data")
  public Response getSalaryBatchDetails(
      @RequestBody(
              description = "Transaction Reference No",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = SalaryBatchRequest.class)))
          SalaryBatchRequest id) {

    boolean DataExists = false;
    String ServiceName = "SalaryBatch";
    LinkedHashMap<String, String> MasterRequestData = new LinkedHashMap<>();
    SalaryBatchHeaderObject SalaryBatchObj = new SalaryBatchHeaderObject();
    Connection dbconnection = null;
    LocalDateTime startTime = LocalDateTime.now();

    try {
      System.out.println("Salary Processing Batch Started on [" + startTime + "]");
      dbconnection = cmDBPool.getConnection();

      PreparedStatement BatchPS =
          dbconnection.prepareStatement(
              "insert into "
                  + channelDBSchema
                  + ".BATCH$POSTING$QUEUE"
                  + " (BATCH_REFERENCE_NO,REQUEST_DATA,TRANS_REFERENCE_NO,STATUS,SERVICE_NAME,MASTER_REFERENCE_NO)"
                  + "  values(?,?,?,'QUEUE','"
                  + ServiceName
                  + "',?)");

      PreparedStatement DupBatchPS =
          dbconnection.prepareStatement(
              "select * from "
                  + channelDBSchema
                  + ".BATCH$POSTING$QUEUE"
                  + " where BATCH_REFERENCE_NO=? ");

      int RTGSCount = id.rtgsBatchData.size();
      int EFTCount = id.eftBatchData.size();
      int SaccoCount = id.saccoBatchData.size();
      int MobileWalletCount = id.mobileWalletBatchData.size();
      int PesalinkCount = id.pesaLinkBatchData.size();

      int TotalTransactionCount =
          RTGSCount + EFTCount + SaccoCount + MobileWalletCount + PesalinkCount;

      System.out.println("Salary Batch Tranaction Size [" + id.totalCount + "]");
      System.out.println("Salary Batch RTGS Transaction Size [" + RTGSCount + "]");
      System.out.println("Salary Batch EFT Tranaction Size [" + EFTCount + "]");
      System.out.println("Salary Batch Sacco Tranaction Size [" + SaccoCount + "]");
      System.out.println("Salary Batch Mobile Wallet Tranaction Size [" + MobileWalletCount + "]");
      System.out.println("Salary Batch Pesalink Tranaction Size [" + PesalinkCount + "]");
      System.out.println("Salary Batch Overall Transaction Size [" + TotalTransactionCount + "]");

      String request_Data = "";
      Gson g = new Gson();
      DupBatchPS.setString(1, id.batchFileReferenceNumber);
      ResultSet DupBatchRS = DupBatchPS.executeQuery();
      if (DupBatchRS.next()) {
        ResponseMessages(
            SalaryBatchObj, id, ResponseStatus.DUPLICATE_BATCH_REFERENCE.getValue(), "F");
        return Response.status(Status.ACCEPTED).entity(SalaryBatchObj).build();
      }
      DupBatchPS.close();
      DupBatchRS.close();

      String TotalAmount = id.txnTotalAmt;

      System.out.println("Master total amount [" + TotalAmount + "]");

      if (TotalTransactionCount == Integer.parseInt(id.totalCount)) {

        int RTGSSize = id.rtgsBatchData.size();
        int EFTSize = id.eftBatchData.size();
        int SaccoSize = id.saccoBatchData.size();
        int PesalinkSize = id.pesaLinkBatchData.size();
        int WalletSize = id.mobileWalletBatchData.size();

        int counts = 0;
        Double PostingAmounts = 0.0;
        while (counts < RTGSSize) {
          PostingAmounts += Double.parseDouble(id.rtgsBatchData.get(counts).reqDebitAmount);
          counts++;
        }
        System.out.println("RTGS total Amount [" + PostingAmounts + "]");
        counts = 0;
        while (counts < EFTSize) {
          PostingAmounts += Double.parseDouble(id.eftBatchData.get(counts).reqDebitAmount);
          counts++;
        }
        counts = 0;
        while (counts < SaccoSize) {
          PostingAmounts += Double.parseDouble(id.saccoBatchData.get(counts).reqDebitAmount);
          counts++;
        }
        counts = 0;
        while (counts < PesalinkSize) {
          PostingAmounts += Double.parseDouble(id.pesaLinkBatchData.get(counts).reqDebitAmount);
          counts++;
        }
        counts = 0;
        while (counts < WalletSize) {
          PostingAmounts += Double.parseDouble(id.mobileWalletBatchData.get(counts).reqDebitAmount);
          counts++;
        }
        System.out.println("total Posting Amount [" + PostingAmounts + "]");

        if (Double.parseDouble(TotalAmount) > PostingAmounts
            || Double.parseDouble(TotalAmount) < PostingAmounts) {
          ResponseMessages(
              SalaryBatchObj, id, ResponseStatus.TRANS_AMOUNT_MISMATCH.getValue(), "F");
          return Response.status(Status.ACCEPTED).entity(SalaryBatchObj).build();
        }

        DataExists = true;
        Field[] resfields = SalaryBatchRequest.class.getDeclaredFields();
        for (Field field : resfields) {
          if (field.isAnnotationPresent(JsonbProperty.class)) {
            String annotationValue = field.getAnnotation(JsonbProperty.class).value();
            // System.out.println("property name ["+annotationValue+"] value
            // ["+field.get(id).toString()+"]");
            if (field.get(id) != null) {
              MasterRequestData.put(
                  annotationValue, field.get(id).toString().replaceAll("\"", "\\\""));
              if (annotationValue.contains("req_TransactionCode")) break;
            }
          }
        }
        request_Data = g.toJson(MasterRequestData);
        // System.out.println("master request Data["+request_Data+"]");
        BatchPS.setString(1, id.batchFileReferenceNumber);
        BatchPS.setString(2, request_Data.toString());
        BatchPS.setString(3, id.reqTransactionReferenceNo);
        BatchPS.setString(4, id.reqTransactionReferenceNo);
        BatchPS.executeUpdate();
        BatchPS.clearParameters();

        for (int i = 0; i < id.rtgsBatchData.size(); i++) {
          request_Data = g.toJson(id.rtgsBatchData.get(i));
          BatchPS.setString(1, id.batchFileReferenceNumber);
          BatchPS.setString(2, request_Data.toString());
          BatchPS.setString(3, id.rtgsBatchData.get(i).reqBatchReferenceNo);
          BatchPS.setString(4, "");
          BatchPS.executeUpdate();
          BatchPS.clearParameters();
        }

        for (int i = 0; i < id.eftBatchData.size(); i++) {
          request_Data = g.toJson(id.eftBatchData.get(i));
          BatchPS.setString(1, id.batchFileReferenceNumber);
          BatchPS.setString(2, request_Data.toString());
          BatchPS.setString(3, id.eftBatchData.get(i).reqBatchReferenceNo);
          BatchPS.setString(4, "");
          BatchPS.executeUpdate();
          BatchPS.clearParameters();
        }

        for (int i = 0; i < id.saccoBatchData.size(); i++) {
          request_Data = g.toJson(id.saccoBatchData.get(i));
          BatchPS.setString(1, id.batchFileReferenceNumber);
          BatchPS.setString(2, request_Data.toString());
          BatchPS.setString(3, id.saccoBatchData.get(i).reqBatchReferenceNo);
          BatchPS.setString(4, "");
          BatchPS.executeUpdate();
          BatchPS.clearParameters();
        }

        for (int i = 0; i < id.pesaLinkBatchData.size(); i++) {
          request_Data = g.toJson(id.pesaLinkBatchData.get(i));
          BatchPS.setString(1, id.batchFileReferenceNumber);
          BatchPS.setString(2, request_Data.toString());
          BatchPS.setString(3, id.pesaLinkBatchData.get(i).reqBatchReferenceNo);
          BatchPS.setString(4, "");
          BatchPS.executeUpdate();
          BatchPS.clearParameters();
        }

        for (int i = 0; i < id.mobileWalletBatchData.size(); i++) {
          request_Data = g.toJson(id.mobileWalletBatchData.get(i));
          BatchPS.setString(1, id.batchFileReferenceNumber);
          BatchPS.setString(2, request_Data.toString());
          BatchPS.setString(3, id.mobileWalletBatchData.get(i).reqBatchReferenceNo);
          BatchPS.setString(4, "");
          BatchPS.executeUpdate();
          BatchPS.clearParameters();
        }

        if (dbconnection.getAutoCommit() == false) dbconnection.commit();
        BatchPS.close();

        if (DataExists) {
          ResponseMessages(SalaryBatchObj, id, ResponseStatus.SUCCESS.getValue(), "S");
        }
      } else {
        ResponseMessages(SalaryBatchObj, id, ResponseStatus.TRANS_COUNT_MISMATCH.getValue(), "F");
      }

      return Response.status(Status.ACCEPTED).entity(SalaryBatchObj).build();
    } catch (Exception except) {
      except.printStackTrace();
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    } finally {
      if (DataExists) {
        coreServices
            .SalaryCallBack(new CallBackRequest(id.batchFileReferenceNumber, ServiceName))
            .whenCompleteAsync(
                ((callbackObject, exception) -> {
                  if (exception != null) {
                    exception.printStackTrace();
                  } else {
                    System.out.println("Response Received " + callbackObject);
                  }
                  LocalDateTime endTime = LocalDateTime.now();
                  long millis = ChronoUnit.MILLIS.between(startTime, endTime);
                  System.out.println(
                      "Salary Processing Batch Completed and Processing Time Taken [ "
                          + millis
                          + " ] MilliSeconds");
                }),
                ForkJoinPool.commonPool());
      }
    }
  }

  private void ResponseMessages(
      SalaryBatchHeaderObject SalaryBatchObj,
      SalaryBatchRequest id,
      String ErrorMessage,
      String ErrorCode) {

    SalaryBatchObj.setResFileRefNo(id.fileReferenceNumber);
    SalaryBatchObj.setResBatchRefNo(id.batchFileReferenceNumber);
    SalaryBatchObj.setResNoOfTxns(id.totalCount);
    SalaryBatchObj.setUnitId(id.reqUserId);
    SalaryBatchObj.setResBatchId(id.hdrTranId);
    SalaryBatchObj.setResBatchstatus(ErrorCode);
    SalaryBatchObj.setResErrorDesc(ErrorMessage);
  }
}
