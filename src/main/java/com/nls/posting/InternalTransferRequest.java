package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({
  "hdrTranId",
  "hdrStatus",
  "reqChannelName",
  "reqUnitID",
  "reqTransactionReferenceNo",
  "reqvalueDate",
  "reqDebitAcCurrency",
  "reqConversionRate",
  "reqAccountNo",
  "reqDebitAccCategory",
  "reqForceDebitFlag",
  "reqDebitAmount",
  "reqTransactionCurrency",
  "reqDrNarration",
  "reqBuyRate",
  "reqDebitBaseEquivalent",
  "reqDebitSourceBranch",
  "reqDebitTransactionCode",
  "reqTransDebRefNo",
  "reqCreditAccountNo",
  "reqCreditAccCategory",
  "reqForceCreditFlag",
  "reqCreditAmount",
  "reqCreditCurrency",
  "reqBeneficiaryName",
  "reqCreditNarration1",
  "reqSellRate",
  "reqCreditBaseEquivalent",
  "reqCreditSourceBranch",
  "reqCreditTransactionCode",
  "vipMarker",
  "waiveCharge"
})
@Schema(
    name = "InternalTransferRequest",
    description = "request object with HdrTranId, hdrStatus, reqUnitID, reqChannelName...etc")
public class InternalTransferRequest {

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

  @Schema(required = false, example = "", description = "Hdr Status")
  @JsonbProperty("hdrStatus")
  public String hdrStatus;

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
  @JsonbProperty("reqTransactionRefNo")
  public String reqTransactionReferenceNo;

  @Schema(required = false, example = "20082022", description = "req Value Date")
  @JsonbProperty("reqValueDate")
  public String reqvalueDate;

  @Schema(required = true, example = "KES", description = "req Debit Acc Currency")
  @JsonbProperty("reqDebitCurrency")
  public String reqDebitAcCurrency;

  @Schema(required = false, example = "1", description = "req Conversion Rate")
  @JsonbProperty("reqConversionRate")
  public String reqConversionRate;

  @Schema(required = true, example = "1711460011", description = "req Debit Account No")
  @JsonbProperty("reqDebitAccountNo")
  public String reqAccountNo;

  @Schema(required = false, example = "CUR", description = "req Debit Acc Category")
  @JsonbProperty("reqDebitAccCategory")
  public String reqDebitAccCategory;

  @Schema(required = false, example = "N", description = "req Force DebitFlag")
  @JsonbProperty("reqForceDebitFlag")
  public String reqForceDebitFlag;

  @Schema(required = true, example = "20", description = "req Debit Amount")
  @JsonbProperty("reqDebitAmount")
  public String reqDebitAmount;

  @Schema(required = false, example = "KES", description = "req Transaction currency")
  @JsonbProperty("reqTransactionCurrency")
  public String reqTransactionCurrency;

  @Schema(
      required = false,
      example = "DOMEWT -TBS2006070374565 - -Charity payments",
      description = "req DrNarration")
  @JsonbProperty("reqDrNarration1")
  public String reqDrNarration;

  @Schema(required = false, example = "1", description = "req Buy Rate")
  @JsonbProperty("reqBuyRate")
  public String reqBuyRate;

  @Schema(required = false, example = "10.004", description = "req Debit Base Equivalent")
  @JsonbProperty("reqDebitBaseEquivalent")
  public String reqDebitBaseEquivalent;

  @Schema(required = false, example = "", description = "req Debit Source branch")
  @JsonbProperty("reqDebitSourceBranch")
  public String reqDebitSourceBranch;

  @Schema(required = false, example = "01", description = "req Debit Transaction Code")
  @JsonbProperty("reqDebitTransactionCode")
  public String reqDebitTransactionCode;

  @Schema(
      required = false,
      example = "TBSSN07062026902",
      description = "req Transaction deb Ref No")
  @JsonbProperty("reqTransDebRefNo")
  public String reqTransDebRefNo;

  @Schema(required = true, example = "7519350046", description = "req Credit Account no")
  @JsonbProperty("reqCreditAccountNo")
  public String reqCreditAccountNo;

  @Schema(required = false, example = "GL", description = "req Credit Acc Category")
  @JsonbProperty("reqCreditAccCategory")
  public String reqCreditAccCategory;

  @Schema(required = false, example = "N", description = "req Force Credit Flag")
  @JsonbProperty("reqForceCreditFlag")
  public String reqForceCreditFlag;

  @Schema(required = false, example = "10.004", description = "req Credit Amount")
  @JsonbProperty("reqCreditAmount")
  public String reqCreditAmount;

  @Schema(required = false, example = "KES", description = "req Credit Currency")
  @JsonbProperty("reqCreditCurrency")
  public String reqCreditCurrency;

  @Schema(required = false, example = "GILBER KIARIE", description = "req Beneficiary Name")
  @JsonbProperty("reqBeneficiaryName")
  public String reqBeneficiaryName;

  @Schema(
      required = false,
      example = "DOMEWT -TBS2006070374565 - -Charity payments",
      description = "req Credit Narration ")
  @JsonbProperty("reqCreditNarration1")
  public String reqCreditNarration1;

  @Schema(required = false, example = "1", description = "req Sell Rate")
  @JsonbProperty("reqSellRate")
  public String reqSellRate;

  @Schema(required = false, example = "10.004", description = "req Credit Base Equivalent")
  @JsonbProperty("reqCreditBaseEquivalent")
  public String reqCreditBaseEquivalent;

  @Schema(required = false, example = "", description = "req Credit Source Branch")
  @JsonbProperty("reqCreditSourceBranch")
  public String reqCreditSourceBranch;

  @Schema(required = false, example = "01", description = "Req Credit Transaction Code")
  @JsonbProperty("reqCreditTransactionCode")
  public String reqCreditTransactionCode;
}
