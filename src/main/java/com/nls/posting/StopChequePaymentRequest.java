package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({
  "hdrTranId",
  "hdrRefNo",
  "unitID",
  "referenceNumber",
  "accountNo",
  "firstChequeNumber",
  "lastChequeNumber",
  "chequeCurrency",
  "narration",
  "amountFrom",
  "amountTo",
  "chequeType",
  "chargeAccountNumber",
  "stopDate",
  "reasonCode",
  "vipMarker",
  "waiveCharge"
})
@Schema(
    name = "Stop Cheque Payment Request",
    description = "request object with unitID, referenceNumber, accountNo, firstChequeNumber..etc")
public class StopChequePaymentRequest {

  @Schema(
      required = false,
      example = "N",
      description = "VIP Marker",
      enumeration = {"N", "Y"},
      title = "Markers are NON VIP/VIP")
  @JsonbProperty("VipMarker")
  public String vipMarker;

  @Schema(
      required = true,
      example = "N",
      description = "Waive Charge",
      enumeration = {"N", "Y"},
      title = "Charges are false/true")
  @JsonbProperty("WaiveCharge")
  public String waiveCharge;

  @Schema(required = true, example = "TFA8090900999", description = "Req Transaction ReferenceNo")
  @JsonbProperty("reqTransactionReferenceNo")
  public String reqTransactionReferenceNo;

  @Schema(required = true, example = "TFA8090900999", description = "Hdr TranId")
  @JsonbProperty("hdrTranId")
  public String hdrTranId;

  @Schema(
      required = true,
      example = "KE0010001",
      description = "req UnitID",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("UnitID")
  public String unitID;

  @Schema(required = false, example = "", description = "ReferenceNumber")
  @JsonbProperty("ReferenceNumber")
  public String referenceNumber;

  @Schema(required = true, example = "1711460011", description = "AccountNo")
  @JsonbProperty("AccountNo")
  public String accountNo;

  @Schema(required = true, example = "100", description = "FirstChequeNumber")
  @JsonbProperty("FirstChequeNumber")
  public String firstChequeNumber;

  @Schema(required = true, example = "200", description = "LastChequeNumber")
  @JsonbProperty("LastChequeNumber")
  public String lastChequeNumber;

  @Schema(required = true, example = "KES", description = "ChequeCurrency")
  @JsonbProperty("ChequeCurrency")
  public String chequeCurrency;

  @Schema(required = false, example = "test", description = "Narration")
  @JsonbProperty("Narration")
  public String narration;

  @Schema(required = false, example = "10", description = "AmountFrom")
  @JsonbProperty("AmountFrom")
  public String amountFrom;

  @Schema(required = false, example = "", description = "AmountTo")
  @JsonbProperty("AmountTo")
  public String amountTo;

  @Schema(required = true, example = "", description = "ChequeType")
  @JsonbProperty("ChequeType")
  public String chequeType;

  @Schema(required = false, example = "", description = "ChargeAccountNumber")
  @JsonbProperty("ChargeAccountNumber")
  public String chargeAccountNumber;

  @Schema(required = false, example = "", description = "StopDate")
  @JsonbProperty("StopDate")
  public String stopDate;

  @Schema(required = false, example = "", description = "ReasonCode")
  @JsonbProperty("ReasonCode")
  public String reasonCode;
}
