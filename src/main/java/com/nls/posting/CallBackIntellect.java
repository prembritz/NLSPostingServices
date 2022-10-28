package com.nls.posting;

import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
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
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.opentracing.Traced;

@Path("/PostingCallBack")
public class CallBackIntellect {

  @Timeout(value = 15, unit = ChronoUnit.SECONDS)
  @Counted()
  @POST
  @Traced()
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponseSchema(
      value = CallBackIntellectObjects.class,
      responseDescription = "Intellect Callback Response",
      responseCode = "200")
  @Operation(
      summary = "Intellect Callback Request",
      description = "returns Intellect Callback data")
  public Response getCallBackDetails(
      @RequestBody(
              description = "Trans Reference",
              required = true,
              content = @Content(mediaType = "application/json"))
          LinkedHashMap<String, Object> post) {

    CallBackIntellectObjects CBObj = new CallBackIntellectObjects();

    try {

      System.out.println(post.toString());
      // String requestData = request.getReader().lines().collect(Collectors.joining());
      // System.out.println("Fetching Intellect Callback Details");
      // Gson gson = new Gson();
      // String ResponseJson=gson.toJson(CBObj);
      // System.out.println("Response Received ["+ResponseJson+"]");
      return Response.status(Status.ACCEPTED).entity(CBObj).build();
    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }
  }
}
