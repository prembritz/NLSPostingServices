package com.nls.core;

import java.io.Closeable;
import java.util.concurrent.CompletionStage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/Posting")
@RegisterRestClient(configKey = "BATCH_CORE_SERVICES") // http://localhost:8080
public interface BatchCoreServices extends Closeable {

  @POST
  @Traced
  // @Timeout(value=40,unit=ChronoUnit.MILLIS)
  @Path("/CBFTCallBack")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public CompletionStage<CallBackObjects> CBFTCallBack(CallBackRequest CbData);

  @POST
  @Traced
  // @Timeout(value=40,unit=ChronoUnit.MILLIS)
  @Path("/AccountingCallBack")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public CompletionStage<CallBackObjects> AccountingCallBack(CallBackRequest CbData);

  @POST
  @Traced
  // @Timeout(value=40,unit=ChronoUnit.MILLIS)
  @Path("/MobileWalletCallBack")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public CompletionStage<CallBackObjects> MobileWalletCallBack(CallBackRequest CbData);

  @POST
  @Traced
  // @Timeout(value=40,unit=ChronoUnit.MILLIS)
  @Path("/DFTCallBack")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public CompletionStage<CallBackObjects> DFTCallBack(CallBackRequest CbData);

  @POST
  @Traced
  // @Timeout(value=40,unit=ChronoUnit.MILLIS)
  @Path("/SalaryProcessingCallBack")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public CompletionStage<CallBackObjects> SalaryCallBack(CallBackRequest CbData);
}
