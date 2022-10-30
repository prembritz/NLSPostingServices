package com.nls.core;

import java.io.Closeable;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletionStage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/NlsCoreServices")
@RegisterRestClient(configKey = "CORE_SERVICES") // http://localhost:8081
public interface CoreServices extends Closeable {
  @POST
  @Traced
  @Timeout(value = 100, unit = ChronoUnit.MILLIS)
  @Path("/ReferenceGenerator")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ReferenceObject generateReference(ReferenceRequest ReferenceData);

  @POST
  @Traced
  @Timeout(value = 100, unit = ChronoUnit.MILLIS)
  @Path("/LockManager")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public LockTrasactionObject LockingTransactions(LockTransactionRequest LTData);

  @POST
  @Traced
  @Timeout(value = 200, unit = ChronoUnit.MILLIS)
  @Path("/ValidateTransactionUniqueness")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public DuplicateCheckObject TransDuplicateCheck(DuplicateCheckRequest DupData);

  @POST
  @Traced
  @Timeout(value = 200, unit = ChronoUnit.MILLIS)
  @Path("/ValidateFinancialTransaction")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ValidateFinancialTransObject ValidateTransactions(
      ValidateFinancialTransRequest validateTransData);

  @POST
  @Traced
  @Timeout(value = 100, unit = ChronoUnit.MILLIS)
  @Path("/OFSFormatter")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public OFSFormatObject OFSFormatter(OFSFormatRequest ofsData);

  @POST
  @Traced
  @Timeout(value = 100, unit = ChronoUnit.MILLIS)
  @Path("/ProcessAndQueueFinancialTransaction")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public QueueFinancialTransObject QueueService(QueueFinancialTransRequest QueueData);

  @POST
  @Traced
  @Timeout(value = 100, unit = ChronoUnit.MILLIS)
  @Path("/PersistUniqueData")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public PersistUniqueDataObject PersistUniqueData(PersistUniqueDataRequest UniqueData);

  @POST
  @Traced
  @Timeout(value = 100, unit = ChronoUnit.MILLIS)
  @Path("/ReleaseLockByUniqueReference")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public CompletionStage<ReleaseLockObject> ReleaseLock(ReleaseLockRequest RLData);
}
