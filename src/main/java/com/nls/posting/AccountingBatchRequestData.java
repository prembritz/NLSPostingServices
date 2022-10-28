package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
    name = "AccountingBatchRequest",
    description = "request object with HdrTranId, hdrStatus, reqUnitID, reqChannelName...etc")
public class AccountingBatchRequestData {

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

  @Schema(required = true, example = "ACCENTRYINTER", description = "Hdr TranId")
  @JsonbProperty("hdrTranId")
  public String hdrTranId;

  @Schema(required = true, example = "", description = "Hdr Status")
  @JsonbProperty("hdrStatus")
  public String hdrStatus;

  @Schema(required = true, example = "CBX", description = "req ChannelName")
  @JsonbProperty("reqChannelName")
  public String reqChannelName;

  @Schema(required = true, example = "KE0010001", description = "req UnitID")
  @JsonbProperty("reqUnitID")
  public String reqUnitID;

  @Schema(
      required = true,
      example = "TBS2006070374565",
      description = "Req Transaction ReferenceNo")
  @JsonbProperty("reqTransactionRefNo")
  public String reqTransactionRefNo;

  @Schema(required = true, example = "2020-06-06", description = "req ValueDate")
  @JsonbProperty("reqValueDate")
  public String reqValueDate;

  @Schema(required = true, example = "KES", description = "req DebitAc Currency")
  @JsonbProperty("reqDebitCurrency")
  public String reqDebitCurrency;

  @Schema(required = true, example = "12.0", description = "req Conversion Rate1")
  @JsonbProperty("reqConversionRate")
  public String reqConversionRate;

  @Schema(required = true, example = "1711460011", description = "req Debit Account No")
  @JsonbProperty("reqDebitAccountNo")
  public String reqDebitAccountNo;

  @Schema(required = true, example = "20", description = "req Debit Account Category")
  @JsonbProperty("reqDebitAccCategory")
  public String reqDebitAccCategory;

  @Schema(required = true, example = "NA", description = "req Force Debit Flag")
  @JsonbProperty("reqForceDebitFlag")
  public String reqForceDebitFlag;

  @Schema(required = true, example = "1200", description = "req Debit Amount")
  @JsonbProperty("reqDebitAmount")
  public String reqDebitAmount;

  @Schema(required = true, example = "", description = "req Transaction Currency")
  @JsonbProperty("reqTransactionCurrency")
  public String reqTransactionCurrency;

  @Schema(
      required = true,
      example = "DOMEWT -TBS2006070374565 - -Charity payments",
      description = "req Debit Narration")
  @JsonbProperty("reqDebitNarration1")
  public String reqDebitNarration1;

  @Schema(required = true, example = "USD", description = "req Buy Rate")
  @JsonbProperty("reqBuyRate")
  public String reqBuyRate;

  @Schema(required = true, example = "100", description = "req DebitBase Equivalent")
  @JsonbProperty("reqDebitBaseEquivalent")
  public String reqDebitBaseEquivalent;

  @Schema(required = true, example = "NA", description = "req Debit source Branch")
  @JsonbProperty("reqDebitSourceBranch")
  public String reqDebitSourceBranch;

  @Schema(required = true, example = "N", description = "req Debit Transaction code")
  @JsonbProperty("reqDebitTransactionCode")
  public String reqDebitTransactionCode;

  @Schema(required = true, example = "59", description = "req Trans Debit Reference no")
  @JsonbProperty("reqTransDebRefNo")
  public String reqTransDebRefNo;

  @Schema(required = true, example = "7519350046", description = "req Credit Acc number")
  @JsonbProperty("reqCreditAccountNo")
  public String reqCreditAccountNo;

  @Schema(required = true, example = "", description = "req Credit Acc Category")
  @JsonbProperty("reqCreditAccCategory")
  public String reqCreditAccCategory;

  @Schema(required = true, example = "", description = "req Force Credit Flag")
  @JsonbProperty("reqForceCreditFlag")
  public String reqForceCreditFlag;

  @Schema(required = true, example = "", description = "req Credit Amount")
  @JsonbProperty("reqCreditAmount")
  public String reqCreditAmount;

  @Schema(required = true, example = "", description = "req Credit Currency")
  @JsonbProperty("reqCreditCurrency")
  public String reqCreditCurrency;

  @Schema(required = true, example = "", description = "req Beneficiary Name")
  @JsonbProperty("reqBeneficiaryName")
  public String reqBeneficiaryName;

  @Schema(
      required = true,
      example = "DOMEWT -TBS2006070374565 - -Charity payments",
      description = "req Credit Narration")
  @JsonbProperty("reqCreditNarration1")
  public String reqCreditNarration1;

  @Schema(required = true, example = "", description = "req sell Rate")
  @JsonbProperty("reqSellRate")
  public String reqSellRate;

  @Schema(required = true, example = "", description = "req Credit Base Equivalent")
  @JsonbProperty("reqCreditBaseEquivalent")
  public String reqCreditBaseEquivalent;

  @Schema(required = true, example = "", description = "req Credit Source Branch")
  @JsonbProperty("reqCreditSourceBranch")
  public String reqCreditSourceBranch;

  @Schema(required = true, example = "01", description = "req Credit Transaction Code")
  @JsonbProperty("reqCreditTransactionCode")
  public String reqCreditTransactionCode;
}
