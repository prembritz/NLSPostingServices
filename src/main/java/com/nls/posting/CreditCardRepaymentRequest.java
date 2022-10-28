package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({
  "hdrTransId",
  "hdrstatus",
  "reqChnlId",
  "reqUnitID",
  "proCode",
  "reqTransactionReferenceNo",
  "reqAccountNo",
  "debitAccountCurrency",
  "reqDebitAmount",
  "creditCardNumber",
  "vipMarker",
  "waiveCharge"
})
@Schema(
    name = "CreditCardRepaymentRequest",
    description =
        "request object with HdrTranId, hdrStatus, reqChnlId, reqUnitID, reqChannelName...etc")
public class CreditCardRepaymentRequest {

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

  @Schema(required = false, example = "PRDT123", description = "Product Code")
  @JsonbProperty("ProCode")
  public String proCode;

  @Schema(required = true, example = "20211ABC", description = "Hdr Trans Id")
  @JsonbProperty("hdrTranId")
  public String hdrTransId;

  @Schema(required = false, example = "test", description = "req Dr Narration")
  @JsonbProperty("hdrStatus")
  public String hdrstatus;

  @Schema(required = true, example = "CBX", description = "req Channel Id")
  @JsonbProperty("reqChnlId")
  public String reqChnlId;

  @Schema(
      required = true,
      example = "KE0010001",
      description = "req UnitID",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("reqUnitID")
  public String reqUnitID;

  @Schema(required = true, example = "TFA8090900198", description = "Req Transaction ReferenceNo")
  @JsonbProperty("reqTransactionReferenceNo")
  public String reqTransactionReferenceNo;

  @Schema(required = true, example = "1711460011", description = "req AccountNo")
  @JsonbProperty("DebitAccountNumber")
  public String reqAccountNo;

  @Schema(required = true, example = "KES", description = "req Debit Acc Currency")
  @JsonbProperty("DebitAccountCurrency")
  public String debitAccountCurrency;

  @Schema(required = true, example = "20", description = "req Debit Amount")
  @JsonbProperty("PaymentAmount")
  public String reqDebitAmount;

  @Schema(required = false, example = "1242827323", description = "req Creditcard Number")
  @JsonbProperty("CreditCardNumber")
  public String creditCardNumber;
}
