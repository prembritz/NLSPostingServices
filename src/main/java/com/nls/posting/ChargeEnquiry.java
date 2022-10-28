package com.nls.posting;

import com.nls.core.CoreServices;
import com.nls.core.ReferenceObject;
import com.nls.core.ReferenceRequest;
import com.nls.core.ValidateFinancialTransObject;
import com.nls.core.ValidateFinancialTransRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.inject.Inject;
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

@Path("/ChargeEnquiry")
public class ChargeEnquiry {

  @Inject CoreServices coreServices;

  @Timeout(value = 15, unit = ChronoUnit.SECONDS)
  @Counted()
  @POST
  @Traced()
  @Produces(MediaType.APPLICATION_JSON)
  @APIResponseSchema(
      value = ChargeEnquiryObject.class,
      responseDescription = "Charge Enquiry Response",
      responseCode = "200")
  @Operation(summary = "Charge Enquiry Request", description = "returns Charge Enquiry data")
  public Response getChargeTypeDetails(
      @RequestBody(
              description = "Debit Account",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = ChargeEnquiryRequest.class)))
          ChargeEnquiryRequest id) {

    ChargeEnquiryObject chargeEnqObj = new ChargeEnquiryObject();

    String UniqueReference = "";
    String ValidationStatus = "";
    String ChargeAmt = "";

    LocalDateTime startTime = LocalDateTime.now();

    try {

      System.out.println("Charge Enquiry Started on [" + startTime + "]");

      String ServiceName = id.getServiceType();
      String UnitId = id.getUnitId();
      String DebitAccountno = id.getDebitAccount();
      String DebitAccountCurrency = id.getChargeCurrency();
      String DebitAmount = id.getAmount();

      System.out.println(
          "Fetching Cheque Enquiry Details for Debit Account [" + DebitAccountno + "]");

      // Reference Generator

      ReferenceObject reference = coreServices.generateReference(new ReferenceRequest("20208ABC"));
      UniqueReference = reference.getUniqueReference();
      System.out.println("Unique Reference [" + UniqueReference + "]");

      // Validate Financial Transaction

      ValidateFinancialTransObject ValFinTrans =
          coreServices.ValidateTransactions(
              new ValidateFinancialTransRequest(
                  UnitId,
                  UniqueReference,
                  ServiceName,
                  DebitAccountno,
                  "",
                  DebitAmount,
                  DebitAccountCurrency,
                  "CHARGE",
                  "false"));
      ValidationStatus = ValFinTrans.getStatus();
      ChargeAmt = ValFinTrans.getChargeAmt();
      System.out.println(
          "["
              + UniqueReference
              + "] Validate Financial Transaction Status ["
              + ValidationStatus
              + "] ChargeAmt ["
              + ChargeAmt
              + "]");

      if (ChargeAmt != null && Double.parseDouble(ChargeAmt) > 0) {
        ResponseMessages(
            chargeEnqObj,
            ServiceName,
            DebitAccountno,
            ERROR_CODE.SUCCESSFUL,
            ResponseStatus.SUCCESS.getValue(),
            ChargeAmt,
            UnitId,
            DebitAccountCurrency);
        return Response.status(Status.ACCEPTED).entity(chargeEnqObj).build();
      } else {
        ResponseMessages(
            chargeEnqObj,
            ServiceName,
            DebitAccountno,
            ERROR_CODE.NOT_FOUND,
            ResponseStatus.CHARGE_NOT_FOUND.getValue(),
            "",
            UnitId,
            DebitAccountCurrency);
        return Response.status(Status.ACCEPTED).entity(chargeEnqObj).build();
      }
    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    } finally {
      LocalDateTime endTime = LocalDateTime.now();
      long millis = ChronoUnit.MILLIS.between(startTime, endTime);
      System.out.println(
          "Charge Enquiry Completed and Processing Time Taken [ " + millis + " ] MilliSeconds");
    }
  }

  private void ResponseMessages(
      ChargeEnquiryObject chargeEnqObj,
      String ServiceType,
      String DebitAccount,
      ERROR_CODE ErrorCode,
      String ErrorDesc,
      String ChargeAmount,
      String UnitId,
      String ChargeCurrency) {

    chargeEnqObj.setUnitId(UnitId);
    chargeEnqObj.setServiceType(ServiceType);
    chargeEnqObj.setDebitAccount(DebitAccount);
    chargeEnqObj.setChargeCurrency(ChargeCurrency);
    chargeEnqObj.setChargeAmount(ChargeAmount);
    chargeEnqObj.setErrorCode(ErrorCode);
    chargeEnqObj.setErrorMessage(ErrorDesc);
  }
}
