package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({
  "hdrTranId",
  "hdrStatus",
  "reqChnlId",
  "reqUnitID",
  "reqChannelName",
  "reqChannelReqTime",
  "reqPostingBranch",
  "reqTransactionReferenceNo",
  "reqTransactionCode",
  "reqDebtAccountNo",
  "reqDebitAcCurrency",
  "reqDebitAmount",
  "reqDrNarration",
  "reqForceDebitFlag",
  "reqConversionRate1",
  "reqConversionRate2",
  "reqCreditBeneficiaryAccount",
  "reqCreditAcCurrency",
  "reqCreditAmount",
  "reqCrNarration",
  "reqForceCreditFlag",
  "reqCardPayment",
  "reqPurposecode",
  "reqTransactionDate",
  "reqValueDate",
  "reqChargeDebitAccountNumber",
  "reqChargeDebitAccountCurrency",
  "reqBENChargeAmountInChargeAccountCurrency",
  "reqBENChargeGLAccount",
  "reqBENChargeConversionRate",
  "reqBENChargeDesc",
  "reqChargeAmountOURInChargeAccountCurrency",
  "reqChargeGLAccountOUR",
  "reqChargeAmountOURCorespondenceGLAmount",
  "reqChargeOURCorespondenceGLAccount",
  "reqChargeOURConversionRate",
  "reqChargeOURDesc",
  "reqSHAChargeDebitAccountNumber",
  "reqSHAChargeGLAccount",
  "reqSHAChargeAmountInChargeAccountCurrency",
  "reqSHAChargeDebitAccountCurrency",
  "reqChargeSHAConversionRate",
  "reqChargeSHADesc",
  "reqBeneficiaryName",
  "reqBeneCity",
  "reqBeneCountry",
  "reqBeneficiaryAddress1",
  "reqBeneficiaryAddress2",
  "reqBeneficiaryAddress3",
  "reqCIF",
  "reqBeneBankName",
  "reqBeneficiaryBankAddress1",
  "reqBeneBankCountry",
  "reqBeneficiaryBankBIC",
  "reqAccountBankFormat",
  "reqSwiftRoutingCode",
  "reqRecieverBankBIC",
  "reqClearingCode",
  "reqRemittanceInfo",
  "reqChargeType",
  "reqBanktoBankInfo",
  "reqDealReference",
  "reqDealerName",
  "reqDealRate",
  "reqTransferAmt",
  "reqOrderingCustomerName",
  "reqOrderingCustomerAccount",
  "reqOrderingCustomerAddress1",
  "reqOrderingCustomerAddress2",
  "reqOrderingCustomerAddress3",
  "reqOrderingCustomerAddress4",
  "reqPayCurrency",
  "reqDebitBranch",
  "reqChannelCode",
  "reqBeneficiaryAddress4",
  "reqChargeAmountInLocalCurrency",
  "reqBatchReferenceNo",
  "reqPymntType",
  "reqFirstLegRefNo",
  "vipMarker",
  "waiveCharge"
})
@Schema(
    name = "KE FTTransfer Request",
    description =
        "request object with HdrTranId, hdrStatus, reqChnlId, reqUnitID, reqChannelName...etc")
public class KEFTransferRequest {

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

  @Schema(required = true, example = "DFTOUTONLINEINTER", description = "Hdr Tran Id")
  @JsonbProperty("hdrTranId")
  public String hdrTranId;

  @Schema(required = false, example = "Succcess", description = "Hdr Status")
  @JsonbProperty("hdrStatus")
  public String hdrStatus;

  @Schema(required = true, example = "ABCD", description = "Req ChnlId")
  @JsonbProperty("reqChnlId")
  public String reqChnlId;

  @Schema(
      required = true,
      example = "KE0010001",
      description = "req UnitID",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("reqUnitID")
  public String reqUnitID;

  @Schema(required = true, example = "ABCD", description = "Req Channel Name")
  @JsonbProperty("reqChannelName")
  public String reqChannelName;

  @Schema(required = false, example = "", description = "Req Channel ReqTime")
  @JsonbProperty("reqChannelReqTime")
  public String reqChannelReqTime;

  @Schema(required = false, example = "", description = "Req Posting Branch")
  @JsonbProperty("reqPostingBranch")
  public String reqPostingBranch;

  @Schema(required = true, example = "TFA8090900999", description = "Req Transaction ReferenceNo")
  @JsonbProperty("reqTransactionReferenceNo")
  public String reqTransactionReferenceNo;

  @Schema(required = false, example = "", description = "Req Transaction Code")
  @JsonbProperty("reqTransactionCode")
  public String reqTransactionCode;

  @Schema(required = true, example = "7519350046", description = "Req Debt AccountNo")
  @JsonbProperty("reqAccountNo")
  public String reqDebtAccountNo;

  @Schema(required = true, example = "KES", description = "Req Debit AcCurrency")
  @JsonbProperty("reqDebitAcCurrency")
  public String reqDebitAcCurrency;

  @Schema(required = true, example = "100", description = "Req Debit Amount")
  @JsonbProperty("reqDebitAmount")
  public String reqDebitAmount;

  @Schema(required = false, example = "test", description = "Req DrNarration")
  @JsonbProperty("reqDrNarration")
  public String reqDrNarration;

  @Schema(required = false, example = "", description = "Req Force DebitFlag")
  @JsonbProperty("reqForceDebitFlag")
  public String reqForceDebitFlag;

  @Schema(required = false, example = "", description = "Req Conversion Rate1")
  @JsonbProperty("reqConversionRate1")
  public String reqConversionRate1;

  @Schema(required = false, example = "", description = "Req Conversion Rate2")
  @JsonbProperty("reqConversionRate2")
  public String reqConversionRate2;

  @Schema(required = true, example = "", description = "Req Credit Beneficiary Account")
  @JsonbProperty("reqBeneficiaryAccount")
  public String reqCreditBeneficiaryAccount;

  @Schema(required = false, example = "KES", description = "Req Credit AcCurrency")
  @JsonbProperty("reqCreditAcCurrency")
  public String reqCreditAcCurrency;

  @Schema(required = false, example = "", description = "Req CreditAmount")
  @JsonbProperty("reqCreditAmount")
  public String reqCreditAmount;

  @Schema(required = false, example = "", description = "Req CrNarration")
  @JsonbProperty("reqCrNarration")
  public String reqCrNarration;

  @Schema(required = false, example = "", description = "Req Force CreditFlag")
  @JsonbProperty("reqForceCreditFlag")
  public String reqForceCreditFlag;

  @Schema(required = false, example = "", description = "Req CardPayment")
  @JsonbProperty("reqCardPayment")
  public String reqCardPayment;

  @Schema(required = false, example = "", description = "Req Purposecode")
  @JsonbProperty("reqPurposecode")
  public String reqPurposecode;

  @Schema(required = false, example = "", description = "Req Transaction Date")
  @JsonbProperty("reqTransactionDate")
  public String reqTransactionDate;

  @Schema(required = false, example = "", description = "Req ValueDate")
  @JsonbProperty("reqValueDate")
  public String reqValueDate;

  @Schema(required = false, example = "", description = "Req Charge Debit Account Number")
  @JsonbProperty("reqChargeDebitAccountNumber")
  public String reqChargeDebitAccountNumber;

  @Schema(required = false, example = "", description = "Req ChargeDebit AccountCurrency")
  @JsonbProperty("reqChargeDebitAccountCurrency")
  public String reqChargeDebitAccountCurrency;

  @Schema(
      required = false,
      example = "",
      description = "ReqBENCharge AmountInCharge AccountCurrency")
  @JsonbProperty("reqBENChargeAmountInChargeAccountCurrency")
  public String reqBENChargeAmountInChargeAccountCurrency;

  @Schema(required = false, example = "", description = "Req BENCharge GLAccount")
  @JsonbProperty("reqBENChargeGLAccount")
  public String reqBENChargeGLAccount;

  @Schema(required = false, example = "", description = "Req BENCharge ConversionRate")
  @JsonbProperty("reqBENChargeConversionRate")
  public String reqBENChargeConversionRate;

  @Schema(required = false, example = "", description = "Req BENCharge Desc")
  @JsonbProperty("reqBENChargeDesc")
  public String reqBENChargeDesc;

  @Schema(
      required = false,
      example = "",
      description = "Req ChargeAmount OURInCharge AccountCurrency")
  @JsonbProperty("reqChargeAmountOURInChargeAccountCurrency")
  public String reqChargeAmountOURInChargeAccountCurrency;

  @Schema(required = false, example = "", description = "ReqChargeGLAccountOUR")
  @JsonbProperty("reqChargeGLAccountOUR")
  public String reqChargeGLAccountOUR;

  @Schema(
      required = false,
      example = "",
      description = "Req ChargeAmount OURCorespondence GLAmount")
  @JsonbProperty("reqChargeAmountOURCorespondenceGLAmount")
  public String reqChargeAmountOURCorespondenceGLAmount;

  @Schema(required = false, example = "", description = "Req ChargeOUR CorespondenceGL Account")
  @JsonbProperty("reqChargeOURCorespondenceGLAccount")
  public String reqChargeOURCorespondenceGLAccount;

  @Schema(required = false, example = "", description = "Req ChargeOUR ConversionRate")
  @JsonbProperty("reqChargeOURConversionRate")
  public String reqChargeOURConversionRate;

  @Schema(required = false, example = "", description = "Req Charge OURDesc")
  @JsonbProperty("reqChargeOURDesc")
  public String reqChargeOURDesc;

  @Schema(required = false, example = "", description = "Req SHACharge DebitAccountNumber")
  @JsonbProperty("reqSHAChargeDebitAccountNumber")
  public String reqSHAChargeDebitAccountNumber;

  @Schema(required = false, example = "", description = "Req SHACharge GLAccount")
  @JsonbProperty("reqSHAChargeGLAccount")
  public String reqSHAChargeGLAccount;

  @Schema(
      required = false,
      example = "",
      description = "Req SHACharge AmountInCharge AccountCurrency")
  @JsonbProperty("reqSHAChargeAmountInChargeAccountCurrency")
  public String reqSHAChargeAmountInChargeAccountCurrency;

  @Schema(required = false, example = "", description = "Req SHAChargeDebit AccountCurrency")
  @JsonbProperty("reqSHAChargeDebitAccountCurrency")
  public String reqSHAChargeDebitAccountCurrency;

  @Schema(required = false, example = "", description = "Req ChargeSHA ConversionRate")
  @JsonbProperty("reqChargeSHAConversionRate")
  public String reqChargeSHAConversionRate;

  @Schema(required = false, example = "", description = "ReqChargeSHADesc")
  @JsonbProperty("reqChargeSHADesc")
  public String reqChargeSHADesc;

  @Schema(required = false, example = "", description = "Req Beneficiary Name")
  @JsonbProperty("reqBeneficiaryName")
  public String reqBeneficiaryName;

  @Schema(required = false, example = "", description = "Req Bene City")
  @JsonbProperty("reqBeneCity")
  public String reqBeneCity;

  @Schema(required = false, example = "", description = "Req Bene Country")
  @JsonbProperty("reqBeneCountry")
  public String reqBeneCountry;

  @Schema(required = false, example = "", description = "Req Beneficiary Address1")
  @JsonbProperty("reqBeneficiaryAddress1")
  public String reqBeneficiaryAddress1;

  @Schema(required = false, example = "", description = "Req Beneficiary Address2")
  @JsonbProperty("reqBeneficiaryAddress2")
  public String reqBeneficiaryAddress2;

  @Schema(required = false, example = "", description = "Req Beneficiary Address3")
  @JsonbProperty("reqBeneficiaryAddress3")
  public String reqBeneficiaryAddress3;

  @Schema(required = false, example = "", description = "ReqCIF")
  @JsonbProperty("reqCIF")
  public String reqCIF;

  @Schema(required = false, example = "", description = "Req Bene BankName")
  @JsonbProperty("reqBeneBankName")
  public String reqBeneBankName;

  @Schema(required = false, example = "", description = "Req Beneficiary BankAddress1")
  @JsonbProperty("reqBeneficiaryBankAddress1")
  public String reqBeneficiaryBankAddress1;

  @Schema(required = false, example = "", description = "Req Bene Bank Country")
  @JsonbProperty("reqBeneBankCountry")
  public String reqBeneBankCountry;

  @Schema(required = true, example = "03107", description = "Req Beneficiary BankBIC")
  @JsonbProperty("reqBeneficiaryBankBIC")
  public String reqBeneficiaryBankBIC;

  @Schema(required = false, example = "", description = "Req Account BankFormat")
  @JsonbProperty("reqaccountBankFormat")
  public String reqAccountBankFormat;

  @Schema(required = false, example = "", description = "Req Swift RoutingCode")
  @JsonbProperty("reqswiftRoutingCode")
  public String reqSwiftRoutingCode;

  @Schema(required = false, example = "", description = "Req RecieverBankBIC")
  @JsonbProperty("reqRecieverBankBIC")
  public String reqRecieverBankBIC;

  @Schema(required = false, example = "", description = "ReqClearingCode")
  @JsonbProperty("reqClearingCode")
  public String reqClearingCode;

  @Schema(required = false, example = "", description = "Req Remittance Info")
  @JsonbProperty("reqRemittanceInfo")
  public String reqRemittanceInfo;

  @Schema(required = false, example = "", description = "Req Charge Type")
  @JsonbProperty("reqChargeType")
  public String reqChargeType;

  @Schema(required = false, example = "", description = "Req Bankto BankInfo")
  @JsonbProperty("reqBanktoBankInfo")
  public String reqBanktoBankInfo;

  @Schema(required = false, example = "", description = "Req Deal Reference")
  @JsonbProperty("reqDealReference")
  public String reqDealReference;

  @Schema(required = false, example = "", description = "Req Dealer Name")
  @JsonbProperty("reqDealerName")
  public String reqDealerName;

  @Schema(required = false, example = "", description = "Req Deal Rate")
  @JsonbProperty("reqDealRate")
  public String reqDealRate;

  @Schema(required = false, example = "", description = "Req Transfer Amt")
  @JsonbProperty("reqTransferAmt")
  public String reqTransferAmt;

  @Schema(required = false, example = "", description = "Req Ordering CustomerName")
  @JsonbProperty("reqOrderingCustomerName")
  public String reqOrderingCustomerName;

  @Schema(required = false, example = "", description = "Req Ordering Customer Account")
  @JsonbProperty("reqOrderingCustomerAccount")
  public String reqOrderingCustomerAccount;

  @Schema(required = false, example = "", description = "Req Ordering Customer Address1")
  @JsonbProperty("reqOrderingCustomerAddress1")
  public String reqOrderingCustomerAddress1;

  @Schema(required = false, example = "", description = "Req Ordering Customer Address2")
  @JsonbProperty("reqOrderingCustomerAddress2")
  public String reqOrderingCustomerAddress2;

  @Schema(required = false, example = "", description = "Req Ordering Customer Address3")
  @JsonbProperty("reqOrderingCustomerAddress3")
  public String reqOrderingCustomerAddress3;

  @Schema(required = false, example = "", description = "Req Ordering Customer Address4")
  @JsonbProperty("reqOrderingCustomerAddress4")
  public String reqOrderingCustomerAddress4;

  @Schema(required = false, example = "", description = "Req Pay Currency")
  @JsonbProperty("reqPayCurrency")
  public String reqPayCurrency;

  @Schema(required = false, example = "", description = "Req Debit Branch")
  @JsonbProperty("reqDebitBranch")
  public String reqDebitBranch;

  @Schema(required = false, example = "", description = "Req Channel Code")
  @JsonbProperty("reqChannelCode")
  public String reqChannelCode;

  @Schema(required = false, example = "", description = "Req Beneficiary Address4")
  @JsonbProperty("reqBeneficiaryAddress4")
  public String reqBeneficiaryAddress4;

  @Schema(required = false, example = "", description = "Req ChargeAmount InLocal Currency")
  @JsonbProperty("reqChargeAmountInLocalCurrency")
  public String reqChargeAmountInLocalCurrency;

  @Schema(required = false, example = "", description = "Req Batch Reference No")
  @JsonbProperty("reqBatchReferenceNo")
  public String reqBatchReferenceNo;

  @Schema(required = false, example = "", description = "Req Pymnt Type")
  @JsonbProperty("reqPymntType")
  public String reqPymntType;

  @Schema(required = false, example = "", description = "Req First LegRef No")
  @JsonbProperty("reqFirstLegRefNo")
  public String reqFirstLegRefNo;
}
