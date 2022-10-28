package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({
  "hdrTranId",
  "hdrStatus",
  "reqChnlId",
  "reqTxnDt",
  "reqInterfaceId",
  "reqRefNo",
  "reqEchoField",
  "reqUnitId",
  "reqCardNo",
  "reqAccNo",
  "reqAmt",
  "reqTxnCCY",
  "reqConvMode",
  "reqConvRate",
  "reqCrConvRate",
  "reqCrConvMode",
  "reqIntRemarks",
  "custReference",
  "reqCBXRefNo",
  "reqValDt",
  "reqRepostFlg",
  "reqDealrefno",
  "reqDealrate",
  "reqDealamount",
  "reqDealername",
  "reqMobilenumber",
  "reqWalletprovider",
  "reqWalletname",
  "reqExrate1",
  "reqDrcrflag",
  "reqDebitccy",
  "reqDebitamt",
  "reqDebitkeyinAmount",
  "reqChargeDebitAccountNo",
  "reqChargeDebitAccCategory",
  "reqChargeDebitCurrency",
  "reqChargeDebitAmount",
  "reqChargeDebitNarration",
  "reqChargeForceDebitFlag",
  "reqChargeConversionRate",
  "reqChargeBuyRate",
  "reqChargeSellRate",
  "reqChargeMidRate",
  "reqChargeCreditAccountNo",
  "reqChargeCreditAccCategory",
  "reqChargeCreditCurrency",
  "reqChargeCreditAmount",
  "reqChargeCreditNarration",
  "reqBeneficiaryName",
  "reqChargeForceCreditFlag",
  "orgcrossratevalue",
  "reqTaxamount",
  "reqTaxglaccount",
  "reqTaxcurrency",
  "reqTaxCreditNarration",
  "reqCrdramount",
  "reqCrdrindicator",
  "reqSuspCrChgAmt",
  "reqMkrid",
  "reqAuthid",
  "reqMkrname",
  "reqAuthname",
  "reqPartnerfeeglacc",
  "reqPartnerfee",
  "reqPartnerfeeccy",
  "reqChargeExchangeRate",
  "reqDebitNarration1",
  "reqCreditNarration1",
  "vipMarker",
  "waiveCharge"
})
@Schema(
    name = "Mobile Airtime TransferRequest",
    description = "request object with HdrTranId, hdrStatus, reqChnlId, reqUnitID..etc")
public class MobileAirtimeTransferRequest {

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

  @Schema(required = true, example = "1234", description = "Hdr Tran Id")
  @JsonbProperty("hdrTranId")
  public String hdrTranId;

  @Schema(required = false, example = "Success", description = "Hdr Status")
  @JsonbProperty("hdrStatus")
  public String hdrStatus;

  @Schema(required = true, example = "ABCD", description = "Req ChnlId")
  @JsonbProperty("reqChnlId")
  public String reqChnlId;

  @Schema(required = false, example = "2022-08-01", description = "Req TxnDt")
  @JsonbProperty("reqTxnDt")
  public String reqTxnDt;

  @Schema(required = false, example = "", description = "Req Interface Id")
  @JsonbProperty("reqInterfaceId")
  public String reqInterfaceId;

  @Schema(required = false, example = "12345", description = "Hdr Tran Id")
  @JsonbProperty("reqRefNo")
  public String reqRefNo;

  @Schema(required = false, example = "", description = "Req Echo Field")
  @JsonbProperty("reqEchoField")
  public String reqEchoField;

  @Schema(
      required = true,
      example = "KE0010001",
      description = "req UnitID",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("reqUnitId")
  public String reqUnitId;

  @Schema(required = false, example = "", description = "Req Card No")
  @JsonbProperty("reqCardNo")
  public String reqCardNo;

  @Schema(required = true, example = "1711460011", description = "Req AccNo")
  @JsonbProperty("reqAccNo")
  public String reqAccNo;

  @Schema(required = true, example = "200", description = "Req Amt")
  @JsonbProperty("reqAmt")
  public String reqAmt;

  @Schema(required = false, example = "KES", description = "Req Txn CCY")
  @JsonbProperty("reqTxnCCY")
  public String reqTxnCCY;

  @Schema(required = false, example = "", description = "Req ConvMode")
  @JsonbProperty("reqConvMode")
  public String reqConvMode;

  @Schema(required = false, example = "", description = "Req ConvRate")
  @JsonbProperty("reqConvRate")
  public String reqConvRate;

  @Schema(required = false, example = "", description = "Req CrConv Rate")
  @JsonbProperty("reqCrConvRate")
  public String reqCrConvRate;

  @Schema(required = false, example = "", description = "Req CrConv Mode")
  @JsonbProperty("reqCrConvMode")
  public String reqCrConvMode;

  @Schema(required = false, example = "", description = "Req IntRemarks")
  @JsonbProperty("reqIntRemarks")
  public String reqIntRemarks;

  @Schema(required = false, example = "", description = "Cust Reference")
  @JsonbProperty("custreference")
  public String custReference;

  @Schema(required = true, example = "TFA8090900999", description = "Req CBX RefNo")
  @JsonbProperty("reqCBXRefNo")
  public String reqCBXRefNo;

  @Schema(required = false, example = "2022-08-01", description = "Req ValDt")
  @JsonbProperty("reqValDt")
  public String reqValDt;

  @Schema(required = false, example = "", description = "Req RepostFlg")
  @JsonbProperty("reqRepostFlg")
  public String reqRepostFlg;

  @Schema(required = false, example = "", description = "Req Dealrefno")
  @JsonbProperty("reqdealrefno")
  public String reqDealrefno;

  @Schema(required = false, example = "", description = "Req Dealrate")
  @JsonbProperty("reqdealrate")
  public String reqDealrate;

  @Schema(required = false, example = "", description = "Req Dealamount")
  @JsonbProperty("reqdealamount")
  public String reqDealamount;

  @Schema(required = false, example = "", description = "Req Dealer Name")
  @JsonbProperty("reqdealername")
  public String reqDealername;

  @Schema(required = true, example = "", description = "Req Mobilenumber")
  @JsonbProperty("reqmobilenumber")
  public String reqMobilenumber;

  @Schema(required = false, example = "", description = "Req Wallet Provider")
  @JsonbProperty("reqwalletprovider")
  public String reqWalletprovider;

  @Schema(required = true, example = "", description = "Req Walletname")
  @JsonbProperty("reqwalletname")
  public String reqWalletname;

  @Schema(required = false, example = "", description = "Req Exrate1")
  @JsonbProperty("reqexrate1")
  public String reqExrate1;

  @Schema(required = false, example = "", description = "Req Drcrflag")
  @JsonbProperty("reqdrcrflag")
  public String reqDrcrflag;

  @Schema(required = true, example = "", description = "Req Debitccy")
  @JsonbProperty("reqDebitccy")
  public String reqDebitccy;

  @Schema(required = true, example = "100", description = "Req Debitamt")
  @JsonbProperty("reqDebitamt")
  public String reqDebitamt;

  @Schema(required = false, example = "", description = "Req Debitkeyin Amount")
  @JsonbProperty("reqDebitkeyinAmount")
  public String reqDebitkeyinAmount;

  @Schema(required = false, example = "", description = "Req Charge Debit AccountNo")
  @JsonbProperty("reqChargeDebitAccountNo")
  public String reqChargeDebitAccountNo;

  @Schema(required = false, example = "", description = "Req ChargeDebit AccCategory")
  @JsonbProperty("reqChargeDebitAccCategory")
  public String reqChargeDebitAccCategory;

  @Schema(required = false, example = "", description = "Req Charge DebitCurrency")
  @JsonbProperty("reqChargeDebitCurrency")
  public String reqChargeDebitCurrency;

  @Schema(required = false, example = "", description = "Req Charge DebitAmount")
  @JsonbProperty("reqChargeDebitAmount")
  public String reqChargeDebitAmount;

  @Schema(required = false, example = "", description = "Req ChargeDebit Narration")
  @JsonbProperty("reqChargeDebitNarration")
  public String reqChargeDebitNarration;

  @Schema(required = false, example = "", description = "Req ChargeForce DebitFlag")
  @JsonbProperty("reqChargeForceDebitFlag")
  public String reqChargeForceDebitFlag;

  @Schema(required = false, example = "", description = "Req Charge Conversion Rate")
  @JsonbProperty("reqChargeConversionRate")
  public String reqChargeConversionRate;

  @Schema(required = false, example = "", description = "Req ChargeVBuyRate")
  @JsonbProperty("reqChargeBuyRate")
  public String reqChargeBuyRate;

  @Schema(required = false, example = "", description = "Req Charge SellRate")
  @JsonbProperty("reqChargeSellRate")
  public String reqChargeSellRate;

  @Schema(required = false, example = "", description = "Req ChargeMidRate")
  @JsonbProperty("reqChargeMidRate")
  public String reqChargeMidRate;

  @Schema(required = false, example = "", description = "Req ChargeCredit AccountNo")
  @JsonbProperty("reqChargeCreditAccountNo")
  public String reqChargeCreditAccountNo;

  @Schema(required = false, example = "", description = "Req ChargeCredit AccCategory")
  @JsonbProperty("reqChargeCreditAccCategory")
  public String reqChargeCreditAccCategory;

  @Schema(required = false, example = "", description = "Req ChargeCredit Currency")
  @JsonbProperty("reqChargeCreditCurrency")
  public String reqChargeCreditCurrency;

  @Schema(required = false, example = "", description = "Req Charge CreditAmount")
  @JsonbProperty("reqChargeCreditAmount")
  public String reqChargeCreditAmount;

  @Schema(required = false, example = "", description = "Req Charge Credit Narration")
  @JsonbProperty("reqChargeCreditNarration")
  public String reqChargeCreditNarration;

  @Schema(required = true, example = "", description = "Req Beneficiary Name")
  @JsonbProperty("reqBeneficiaryName")
  public String reqBeneficiaryName;

  @Schema(required = false, example = "", description = "Req ChargeForce CreditFlag")
  @JsonbProperty("reqChargeForceCreditFlag")
  public String reqChargeForceCreditFlag;

  @Schema(required = false, example = "", description = "Org Crossrate value")
  @JsonbProperty("orgcrossratevalue")
  public String orgCrossratevalue;

  @Schema(required = false, example = "", description = "Req Taxamount")
  @JsonbProperty("reqtaxamount")
  public String reqTaxamount;

  @Schema(required = false, example = "", description = "Req Taxglaccount")
  @JsonbProperty("reqtaxglaccount")
  public String reqTaxglaccount;

  @Schema(required = false, example = "", description = "Req Taxcurrency")
  @JsonbProperty("reqtaxcurrency")
  public String reqTaxcurrency;

  @Schema(required = false, example = "", description = "Req Tax Credit Narration")
  @JsonbProperty("reqTaxCreditNarration")
  public String reqTaxCreditNarration;

  @Schema(required = false, example = "", description = "Req Crdramount")
  @JsonbProperty("reqcrdramount")
  public String reqCrdramount;

  @Schema(required = false, example = "", description = "Req Crdrindicator")
  @JsonbProperty("reqcrdrindicator")
  public String reqCrdrindicator;

  @Schema(required = false, example = "", description = "Hdr Tran Id")
  @JsonbProperty("reqSuspCrChgAmt")
  public String reqSuspCrChgAmt;

  @Schema(required = false, example = "", description = "Req Mkrid")
  @JsonbProperty("reqmkrid")
  public String reqMkrid;

  @Schema(required = false, example = "", description = "Req Authid")
  @JsonbProperty("reqauthid")
  public String reqAuthid;

  @Schema(required = false, example = "", description = "Req Mkrname")
  @JsonbProperty("reqmkrname")
  public String reqMkrname;

  @Schema(required = false, example = "", description = "Req Authname")
  @JsonbProperty("reqauthname")
  public String reqAuthname;

  @Schema(required = false, example = "", description = "Req Partnerfeeglacc")
  @JsonbProperty("reqpartnerfeeglacc")
  public String reqPartnerfeeglacc;

  @Schema(required = false, example = "", description = "Req Partnerfee")
  @JsonbProperty("reqpartnerfee")
  public String reqPartnerfee;

  @Schema(required = false, example = "", description = "Req Partnerfeeccy")
  @JsonbProperty("reqpartnerfeeccy")
  public String reqPartnerfeeccy;

  @Schema(required = false, example = "", description = "Req Charge ExchangeRate")
  @JsonbProperty("reqChargeExchangeRate")
  public String reqChargeExchangeRate;

  @Schema(required = false, example = "", description = "Req Debit Narration1")
  @JsonbProperty("reqDebitNarration1")
  public String reqDebitNarration1;

  @Schema(required = false, example = "", description = "Req Credit Narration1")
  @JsonbProperty("reqCreditNarration1")
  public String reqCreditNarration1;
}
