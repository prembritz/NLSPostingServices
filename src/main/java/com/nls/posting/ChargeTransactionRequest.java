package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({
  "hdrTranId",
  "reqChnlId",
  "reqUnitID",
  "reqChargeType",
  "reqTransactionReferenceNo",
  "reqAccountNo",
  "debitAccountCurrency",
  "amount",
  "reqCreditCurrency",
  "reqCreditAccountNo",
  "reqFXreference",
  "reqCreditNarration",
  "reqDebitNarration",
  "reqvalueDate",
  "vipMarker"
})
@Schema(
    name = "ChargeTransactionRequest",
    description = "request object with reqChnlId, reqUnitID ...etc")
public class ChargeTransactionRequest {

  @Schema(
      required = false,
      example = "N",
      description = "VIP Marker",
      enumeration = {"N", "Y"},
      title = "Markers are NON VIP/VIP")
  @JsonbProperty("VipMarker")
  public String vipMarker;

  @Schema(required = true, example = "Charge Transaction", description = "Trans Id")
  @JsonbProperty("hdrTranId")
  public String hdrTranId;

  @Schema(required = true, example = "CBX", description = "req Channel Id")
  @JsonbProperty("reqChannelName")
  public String reqChnlId;

  @Schema(
      required = true,
      example = "KE0010001",
      description = "req UnitID",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("reqUnitID")
  public String reqUnitID;

  @Schema(required = true, example = "TFA8090900198", description = "Req Charge Type")
  @JsonbProperty("reqChargeType")
  public String reqChargeType;

  @Schema(required = true, example = "TFA8090900198", description = "Req Transaction ReferenceNo")
  @JsonbProperty("reqTransactionRefNo")
  public String reqTransactionReferenceNo;

  @Schema(required = true, example = "1711460011", description = "req AccountNo")
  @JsonbProperty("reqDebitAccountNo")
  public String reqAccountNo;

  @Schema(required = true, example = "KES", description = "req Debit Acc Currency")
  @JsonbProperty("reqDebitCurrency")
  public String debitAccountCurrency;

  @Schema(required = true, example = "100", description = "Debit Amount")
  @JsonbProperty("reqDebitAmount")
  public String amount;

  @Schema(required = true, example = "KES", description = "req Credit Acc Currency")
  @JsonbProperty("reqCreditCurrency")
  public String reqCreditCurrency;

  @Schema(required = true, example = "7519350046", description = "req Credit Account Number")
  @JsonbProperty("reqCreditAccountNo")
  public String reqCreditAccountNo;

  @Schema(required = false, example = "20082022", description = "req FX Reference")
  @JsonbProperty("reqFXreference")
  public String reqFXreference;

  @Schema(
      required = false,
      example = "DOMEWT -TBS2006070374565 - -Charity payments",
      description = "req DrNarration")
  @JsonbProperty("reqCreditNarration")
  public String reqCreditNarration;

  @Schema(
      required = false,
      example = "DOMEWT -TBS2006070374565 - -Charity payments",
      description = "req CRNarration")
  @JsonbProperty("reqDebitNarration")
  public String reqDebitNarration;

  @Schema(required = false, example = "20082022", description = "req Value Date")
  @JsonbProperty("reqValueDate")
  public String reqvalueDate;
}
