package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
    name = "MobileWalletBatchRequest",
    description = "request object with HdrTranId, hdrStatus, reqUnitID, reqChannelName...etc")
public class MobileWalletBatchRequestData {

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

  @Schema(required = true, example = "MOBILEWALLET", description = "Hdr TranId")
  @JsonbProperty("hdrTranId")
  public String hdrTranId;

  @Schema(required = false, example = "NULL", description = "Hdr Status")
  @JsonbProperty("hdrStatus")
  public String hdrStatus;

  @Schema(required = true, example = "", description = "req ChnlId")
  @JsonbProperty("reqChnlId")
  public String reqChnlId;

  @Schema(required = false, example = "20200302191431", description = "req Txn Dt")
  @JsonbProperty("reqTxnDt")
  public String reqTxnDt;

  @Schema(required = false, example = "", description = "req Interface Id")
  @JsonbProperty("reqInterfaceId")
  public String reqInterfaceId;

  @Schema(required = true, example = "C734020320191339", description = "reqRefNo")
  @JsonbProperty("reqRefNo")
  public String reqRefNo;

  @Schema(required = false, example = "C734020320191339", description = "req Echo Field")
  @JsonbProperty("reqEchoField")
  public String reqEchoField;

  @Schema(
      required = true,
      example = "KE0010001",
      description = "req UnitID",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("reqUnitId")
  public String reqUnitId;

  @Schema(required = false, example = "", description = "req Card No")
  @JsonbProperty("reqCardNo")
  public String reqCardNo;

  @Schema(required = true, example = "01136163929200", description = "req Acc No")
  @JsonbProperty("reqAccNo")
  public String reqAccNo;

  @Schema(required = false, example = "100", description = "req Amt")
  @JsonbProperty("reqAmt")
  public String reqAmt;

  @Schema(required = false, example = "KES", description = "req Txn CCY")
  @JsonbProperty("reqTxnCCY")
  public String reqTxnCCY;

  @Schema(required = false, example = "20", description = "req Conv Mode")
  @JsonbProperty("reqConvMode")
  public String reqConvMode;

  @Schema(required = false, example = "NA", description = "req Conv Rate")
  @JsonbProperty("reqConvRate")
  public String reqConvRate;

  @Schema(required = false, example = "N", description = "req Cr Conv Rate")
  @JsonbProperty("reqCrConvRate")
  public String reqCrConvRate;

  @Schema(required = false, example = "12.0", description = "reqCrConvMode")
  @JsonbProperty("reqCrConvMode")
  public String reqCrConvMode;

  @Schema(required = false, example = "", description = "req Int Remarks")
  @JsonbProperty("reqIntRemarks")
  public String reqIntRemarks;

  @Schema(required = false, example = "Mpesa  0722217071", description = "cust reference")
  @JsonbProperty("custreference")
  public String custreference;

  @Schema(required = true, example = "C734020320191339", description = "reqCBXRefNo")
  @JsonbProperty("reqCBXRefNo")
  public String reqCBXRefNo;

  @Schema(required = false, example = "2020-03-02", description = "req Val Dt")
  @JsonbProperty("reqValDt")
  public String reqValDt;

  @Schema(required = false, example = "N", description = "req Repost Flg")
  @JsonbProperty("reqRepostFlg")
  public String reqRepostFlg;

  @Schema(required = false, example = "", description = "req deal ref no")
  @JsonbProperty("reqdealrefno")
  public String reqdealrefno;

  @Schema(required = false, example = "", description = "req deal rate")
  @JsonbProperty("reqdealrate")
  public String reqdealrate;

  @Schema(required = false, example = "", description = "req deal amount")
  @JsonbProperty("reqdealamount")
  public String reqdealamount;

  @Schema(required = false, example = "", description = "req dealer name")
  @JsonbProperty("reqdealername")
  public String reqdealername;

  @Schema(required = true, example = "0722217071", description = "req mobile number")
  @JsonbProperty("reqmobilenumber")
  public String reqmobilenumber;

  @Schema(required = false, example = "Safaricom", description = "req wallet provider")
  @JsonbProperty("reqwalletprovider")
  public String reqwalletprovider;

  @Schema(required = true, example = "Mpesa", description = "req wallet name")
  @JsonbProperty("reqwalletname")
  public String reqwalletname;

  @Schema(required = false, example = "", description = "req ex rate1")
  @JsonbProperty("reqexrate1")
  public String reqexrate1;

  @Schema(required = false, example = "", description = "req dr cr flag")
  @JsonbProperty("reqdrcrflag")
  public String reqdrcrflag;

  @Schema(required = true, example = "KES", description = "req Debit ccy")
  @JsonbProperty("reqDebitccy")
  public String reqDebitccy;

  @Schema(required = true, example = "100", description = "req Debit amt")
  @JsonbProperty("reqDebitamt")
  public String reqDebitamt;

  @Schema(required = false, example = "100", description = "req Debit keyin Amount")
  @JsonbProperty("reqDebitkeyinAmount")
  public String reqDebitkeyinAmount;

  @Schema(required = false, example = "", description = "req Charge Debit AccountNo")
  @JsonbProperty("reqChargeDebitAccountNo")
  public String reqChargeDebitAccountNo;

  @Schema(required = false, example = "", description = "req Charge Debit AccCategory")
  @JsonbProperty("reqChargeDebitAccCategory")
  public String reqChargeDebitAccCategory;

  @Schema(required = false, example = "", description = "req Charge Debit Currency")
  @JsonbProperty("reqChargeDebitCurrency")
  public String reqChargeDebitCurrency;

  @Schema(required = false, example = "", description = "req Charge Debit Amount")
  @JsonbProperty("reqChargeDebitAmount")
  public String reqChargeDebitAmount;

  @Schema(required = false, example = "", description = "req Charge Debit Narration")
  @JsonbProperty("reqChargeDebitNarration")
  public String reqChargeDebitNarration;

  @Schema(required = false, example = "", description = "req Charge Force Debit Flag")
  @JsonbProperty("reqChargeForceDebitFlag")
  public String reqChargeForceDebitFlag;

  @Schema(required = false, example = "", description = "req Charge Conversion Rate")
  @JsonbProperty("reqChargeConversionRate")
  public String reqChargeConversionRate;

  @Schema(required = false, example = "", description = "req Charge Buy Rate")
  @JsonbProperty("reqChargeBuyRate")
  public String reqChargeBuyRate;

  @Schema(required = false, example = "", description = "req Charge SellRate")
  @JsonbProperty("reqChargeSellRate")
  public String reqChargeSellRate;

  @Schema(required = false, example = "", description = "req Charge Mid Rate")
  @JsonbProperty("reqChargeMidRate")
  public String reqChargeMidRate;

  @Schema(required = false, example = "", description = "req Charge Credit AccountNo")
  @JsonbProperty("reqChargeCreditAccountNo")
  public String reqChargeCreditAccountNo;

  @Schema(required = false, example = "USD", description = "req Charge Credit AccCategory")
  @JsonbProperty("reqChargeCreditAccCategory")
  public String reqChargeCreditAccCategory;

  @Schema(required = false, example = "", description = "req Charge Credit Currency")
  @JsonbProperty("reqChargeCreditCurrency")
  public String reqChargeCreditCurrency;

  @Schema(required = false, example = "KE", description = "req Charge Credit Amount")
  @JsonbProperty("reqChargeCreditAmount")
  public String reqChargeCreditAmount;

  @Schema(required = false, example = "", description = "req Charge Credit Narration")
  @JsonbProperty("reqChargeCreditNarration")
  public String reqChargeCreditNarration;

  @Schema(required = false, example = "", description = "req Beneficiary Name")
  @JsonbProperty("reqBeneficiaryName")
  public String reqBeneficiaryName;

  @Schema(required = false, example = "", description = "req Charge Force CreditFlag")
  @JsonbProperty("reqChargeForceCreditFlag")
  public String reqChargeForceCreditFlag;

  @Schema(required = false, example = "1", description = "org cross rate value")
  @JsonbProperty("orgcrossratevalue")
  public String orgcrossratevalue;

  @Schema(required = false, example = "NCBA", description = "req tax amount")
  @JsonbProperty("reqtaxamount")
  public String reqtaxamount;

  @Schema(required = false, example = "", description = "req tax gl account")
  @JsonbProperty("reqtaxglaccount")
  public String reqtaxglaccount;

  @Schema(required = false, example = "KES", description = "req tax currency")
  @JsonbProperty("reqtaxcurrency")
  public String reqtaxcurrency;

  @Schema(required = false, example = "", description = "req Tax Credit Narration")
  @JsonbProperty("reqTaxCreditNarration")
  public String reqTaxCreditNarration;

  @Schema(required = false, example = "100", description = "req cr dr amount")
  @JsonbProperty("reqcrdramount")
  public String reqcrdramount;

  @Schema(required = false, example = "D", description = "req cr dr indicator")
  @JsonbProperty("reqcrdrindicator")
  public String reqcrdrindicator;

  @Schema(required = false, example = "", description = "req Susp Cr Chg Amt")
  @JsonbProperty("reqSuspCrChgAmt")
  public String reqSuspCrChgAmt;

  @Schema(required = false, example = "201916001562", description = "req mkr id")
  @JsonbProperty("reqmkrid")
  public String reqmkrid;

  @Schema(required = false, example = "201916001562", description = "req auth id")
  @JsonbProperty("reqauthid")
  public String reqauthid;

  @Schema(required = false, example = "DMUSUNGU", description = "req mkr name")
  @JsonbProperty("reqmkrname")
  public String reqmkrname;

  @Schema(required = false, example = "DMUSUNGU", description = "req auth name")
  @JsonbProperty("reqauthname")
  public String reqauthname;

  @Schema(required = false, example = "", description = "req partner fee gl acc")
  @JsonbProperty("reqpartnerfeeglacc")
  public String reqpartnerfeeglacc;

  @Schema(required = false, example = "", description = "req partner fee")
  @JsonbProperty("reqpartnerfee")
  public String reqpartnerfee;

  @Schema(required = false, example = "", description = "req partner fee ccy")
  @JsonbProperty("reqpartnerfeeccy")
  public String reqpartnerfeeccy;

  @Schema(required = false, example = "1", description = "req Charge Exchange Rate")
  @JsonbProperty("reqChargeExchangeRate")
  public String reqChargeExchangeRate;

  @Schema(required = false, example = "NA", description = "req Debit Narration1")
  @JsonbProperty("reqDebitNarration1")
  public String reqDebitNarration1;

  @Schema(required = false, example = "NA", description = "req Credit Narration1")
  @JsonbProperty("reqCreditNarration1")
  public String reqCreditNarration1;
}
