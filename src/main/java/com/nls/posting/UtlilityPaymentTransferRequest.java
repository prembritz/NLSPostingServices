package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({
  "hdrTranId",
  "reqChannelName",
  "reqUnitID",
  "reqTransactionReferenceNo",
  "reqvalueDate",
  "reqDebitAcCurrency",
  "reqAccountNo",
  "reqDebitAmount",
  "reqDrNarration",
  "reqCreditAccountNo",
  "vipMarker",
  "waiveCharge"
})
@Schema(
    name = "UtlilityPaymentTransferRequest",
    description = "request object with HdrTranId, reqUnitID, reqChannelName...etc")
public class UtlilityPaymentTransferRequest {

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

  @Schema(required = true, example = "ACCENTRYINTER", description = "Hdr Trans Id")
  @JsonbProperty("hdrTranId")
  public String hdrTranId;

  @Schema(required = true, example = "IB", description = "req Channel Name")
  @JsonbProperty("reqChannelName")
  public String reqChannelName;

  @Schema(
      required = true,
      example = "KE0010001",
      description = "req UnitID",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("reqUnitID")
  public String reqUnitID;

  @Schema(
      required = true,
      example = "TBS2006070374565",
      description = "Req Transaction Reference No")
  @JsonbProperty("ReferenceNo")
  public String reqTransactionReferenceNo;

  @Schema(required = false, example = "20082022", description = "req Value Date")
  @JsonbProperty("TransactionDate")
  public String reqvalueDate;

  @Schema(required = true, example = "KES", description = "req Debit Acc Currency")
  @JsonbProperty("TransactionCurrency")
  public String reqDebitAcCurrency;

  @Schema(required = true, example = "1711460011", description = "req Debit Account No")
  @JsonbProperty("AccountNumber")
  public String reqAccountNo;

  @Schema(required = true, example = "20", description = "req Debit Amount")
  @JsonbProperty("TransactionAmount")
  public String reqDebitAmount;

  @Schema(required = false, example = "", description = "req DrNarration")
  @JsonbProperty("Narration")
  public String reqDrNarration;

  @Schema(required = true, example = "7519350046", description = "req Credit Account no")
  @JsonbProperty("BillerCode")
  public String reqCreditAccountNo;
}
