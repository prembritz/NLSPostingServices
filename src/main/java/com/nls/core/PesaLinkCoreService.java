package com.nls.core;

import java.io.Closeable;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

// @Path("/pesalink/REST/CM")
@Path("/gtw/exposed")
@RegisterRestClient(
    configKey = "PESLINK_CORE_SERVICES") // https://trinity.cbaloop.com/gtw/exposed/creditTransfer
public interface PesaLinkCoreService extends Closeable {

  @POST
  @Traced
  // @Timeout(value=50,unit=ChronoUnit.MILLIS)
  // @Path("/transfer")
  @Path("/creditTransfer")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String pesaLinkDirectPost(PesaLinkCoreRequest pesaRequest);
}
