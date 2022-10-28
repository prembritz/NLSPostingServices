package com.nls.core;

import java.io.Closeable;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

// https://205.205.210.62/MB/api/airtime/buy

@Path("MB/api/airtime")
@RegisterRestClient(configKey = "MOBILE_AIRTIME_CORE_SERVICES") // https://205.205.210.62
public interface MobileAirtimeCoreService extends Closeable {
  @POST
  @Traced
  // @Timeout(value=50,unit=ChronoUnit.MILLIS)
  @Path("/buy")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public MobileAirtimeCoreObject MobileAirTimeDirectPost(MobileAirtimeCoreRequest PayloadRequest);
}
