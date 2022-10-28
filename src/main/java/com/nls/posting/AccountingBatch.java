package com.nls.posting;

import com.google.gson.Gson;
import com.nls.core.BatchCoreServices;
import com.nls.core.CallBackRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;
import javax.inject.Inject;
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

@Path("/AccountingBatch")
public class AccountingBatch {

  private static DataSource cmDBPool;
  private static NumberFormat numberFormat = new DecimalFormat("###0.00");
  private static HashMap<String, String> ActualTableName;
  private static HashMap<String, String> GlobalParameters;

  private static String coreDBSchema;
  private static String channelDBSchema;

  public static void SetSchemaNames(String coreDBSchema, String channelDBSchema) {
    AccountingBatch.coreDBSchema = coreDBSchema;
    AccountingBatch.channelDBSchema = channelDBSchema;
  }

  public static void setDBPool(DataSource cmDBPool) {
    AccountingBatch.cmDBPool = cmDBPool;
  }

  @Inject BatchCoreServices coreServices;

  @Timeout(value = 30, unit = ChronoUnit.SECONDS)
  @Counted()
  @POST
  @Traced()
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponseSchema(
      value = AccountingBatchHeaderObject.class,
      responseDescription = "Accounting Batch Response",
      responseCode = "200")
  @Operation(summary = "Accounting Batch Request", description = "returns Accounting Batch data")
  public Response getAccountingBatchDetails(
      @RequestBody(
              description = "Transaction Reference No",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = AccountingBatchRequest.class)))
          AccountingBatchRequest id) {

    boolean DataExists = false;
    String ServiceName = "AccountingBatch";

    LocalDateTime startTime = LocalDateTime.now();

    AccountingBatchHeaderObject AccBatchObj = new AccountingBatchHeaderObject();
    Connection dbconnection = null;
    try {

      System.out.println("Accounting Batch Started on [" + startTime + "]");

      dbconnection = cmDBPool.getConnection();
      PreparedStatement DupBatchPS =
          dbconnection.prepareStatement(
              "select * from "
                  + channelDBSchema
                  + ".BATCH$POSTING$QUEUE where"
                  + " BATCH_REFERENCE_NO=?");
      PreparedStatement BatchPS =
          dbconnection.prepareStatement(
              "insert into "
                  + channelDBSchema
                  + ".BATCH$POSTING$QUEUE"
                  + " (BATCH_REFERENCE_NO,REQUEST_DATA,TRANS_REFERENCE_NO,STATUS,SERVICE_NAME)"
                  + " values(?,?,?,'QUEUE','"
                  + ServiceName
                  + "')");

      System.out.println("Accounting Batch Tranaction Size [" + id.batchData.size() + "]");

      DupBatchPS.setString(1, id.reqBatchRefNo);
      ResultSet DupBatchRS = DupBatchPS.executeQuery();
      if (DupBatchRS.next()) {
        ResponseMessages(AccBatchObj, id, ResponseStatus.DUPLICATE_BATCH_REFERENCE.getValue(), "F");
        return Response.status(Status.ACCEPTED).entity(AccBatchObj).build();
      }
      DupBatchPS.close();
      DupBatchRS.close();

      if (id.batchData.size() == Integer.parseInt(id.reqNoOfTxns)) {
        for (int i = 0; i < id.batchData.size(); i++) {
          DataExists = true;
          Gson g = new Gson();
          String request_Data = g.toJson(id.batchData.get(i));
          // System.out.println("Request Data ["+request_Data.toString()+"]");

          BatchPS.setString(1, id.reqBatchRefNo);
          BatchPS.setString(2, request_Data.toString());
          BatchPS.setString(3, id.batchData.get(i).reqTransactionRefNo);
          BatchPS.executeUpdate();
          BatchPS.clearParameters();
        }
        if (dbconnection.getAutoCommit() == false) dbconnection.commit();
        BatchPS.close();

        if (DataExists) {
          ResponseMessages(AccBatchObj, id, ResponseStatus.SUCCESS.getValue(), "S");
        }
      } else {
        ResponseMessages(AccBatchObj, id, ResponseStatus.TRANS_COUNT_MISMATCH.getValue(), "F");
      }

      return Response.status(Status.ACCEPTED).entity(AccBatchObj).build();
    } catch (Exception except) {
      except.printStackTrace();
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    } finally {
      if (DataExists) {
        coreServices
            .AccountingCallBack(new CallBackRequest(id.reqBatchRefNo, ServiceName))
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
                      "Account Batch Completed and Processing Time Taken [ "
                          + millis
                          + " ] MilliSeconds");
                }),
                ForkJoinPool.commonPool());
      }
    }
  }

  private void ResponseMessages(
      AccountingBatchHeaderObject AccBatchObj,
      AccountingBatchRequest id,
      String ErrorMessage,
      String ErrorCode) {

    AccBatchObj.setResFileRefNo(id.reqFileRefNo);
    AccBatchObj.setResBatchRefNo(id.reqBatchRefNo);
    AccBatchObj.setResNoOfTxns(id.reqNoOfTxns);
    AccBatchObj.setUnitId(id.reqUnitId);
    AccBatchObj.setResBatchId("");
    AccBatchObj.setResBatchstatus(ErrorCode);
    AccBatchObj.setResErrorDesc(ErrorMessage);
  }
}
