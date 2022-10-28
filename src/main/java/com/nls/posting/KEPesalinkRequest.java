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
    name = "KEPesalinkRequest",
    description =
        "Request object with  HdrTranId, hdrStatus, reqChnlId, reqUnitID, reqChannelName...etc")
public class KEPesalinkRequest {

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
      title = "Charges are false/true")
  @JsonbProperty("WaiveCharge")
  public String waiveCharge;

  @Schema(required = true, example = "DFTOUTONLINEINTER", description = "Hdr Tran Id")
  @JsonbProperty("hdrTranId")
  public String hdrTranId;

  @Schema(required = true, example = "Success", description = "Hdr Status")
  @JsonbProperty("hdrStatus")
  public String hdrStatus;

  @Schema(required = true, example = "ABCD", description = "Req Chnl Id")
  @JsonbProperty("reqChnlId")
  public String reqChnlId;

  @Schema(required = true, example = "KE0010001", description = "Req UnitID")
  @JsonbProperty("reqUnitID")
  public String reqUnitID;

  @Schema(required = true, example = "PESA", description = "Req ChannelName")
  @JsonbProperty("reqChannelName")
  public String reqChannelName;

  @Schema(required = true, example = "", description = "Req Channel ReqTime")
  @JsonbProperty("reqChannelReqTime")
  public String reqChannelReqTime;

  @Schema(required = true, example = "", description = "Req PostingBranch")
  @JsonbProperty("reqPostingBranch")
  public String reqPostingBranch;

  @Schema(required = true, example = "TFA8090900999", description = "Req Transaction ReferenceNo")
  @JsonbProperty("reqTransactionReferenceNo")
  public String reqTransactionReferenceNo;

  @Schema(required = true, example = "", description = "Req TransactionCode")
  @JsonbProperty("reqTransactionCode")
  public String reqTransactionCode;

  @Schema(required = true, example = "7519350046", description = "Req AccountNo")
  @JsonbProperty("reqAccountNo")
  public String reqDebtAccountNo;

  @Schema(required = true, example = "", description = "Req DebitAcCurrency")
  @JsonbProperty("reqDebitAcCurrency")
  public String reqDebitAcCurrency;

  @Schema(required = true, example = "100", description = "Req DebitAmount")
  @JsonbProperty("reqDebitAmount")
  public String reqDebitAmount;

  @Schema(required = true, example = "test", description = "Req DrNarration")
  @JsonbProperty("reqDrNarration")
  public String reqDrNarration;

  @Schema(required = true, example = "", description = "Req ForceDebitFlag")
  @JsonbProperty("reqForceDebitFlag")
  public String reqForceDebitFlag;

  @Schema(required = true, example = "", description = "Req ConversionRate1")
  @JsonbProperty("reqConversionRate1")
  public String reqConversionRate1;

  @Schema(required = true, example = "", description = "Req ConversionRate2")
  @JsonbProperty("reqConversionRate2")
  public String reqConversionRate2;

  @Schema(required = true, example = "6493320028", description = "Req Credit Beneficiary Account")
  @JsonbProperty("reqBeneficiaryAccount")
  public String reqCreditBeneficiaryAccount;

  @Schema(required = true, example = "KES", description = "Req Credit AcCurrency")
  @JsonbProperty("reqCreditAcCurrency")
  public String reqCreditAcCurrency;

  @Schema(required = true, example = "", description = "Req CreditAmount")
  @JsonbProperty("reqCreditAmount")
  public String reqCreditAmount;

  @Schema(required = true, example = "", description = "Req CrNarration")
  @JsonbProperty("reqCrNarration")
  public String reqCrNarration;

  @Schema(required = true, example = "", description = "Req ForceCreditFlag")
  @JsonbProperty("reqForceCreditFlag")
  public String reqForceCreditFlag;

  @Schema(required = true, example = "", description = "Req CardPayment")
  @JsonbProperty("reqCardPayment")
  public String reqCardPayment;

  @Schema(required = true, example = "", description = "Req Purposecode")
  @JsonbProperty("reqPurposecode")
  public String reqPurposecode;

  @Schema(required = true, example = "", description = "ReqTransactionDate")
  @JsonbProperty("reqTransactionDate")
  public String reqTransactionDate;

  @Schema(required = true, example = "", description = "Req ValueDate")
  @JsonbProperty("reqValueDate")
  public String reqValueDate;

  @Schema(required = true, example = "", description = "Req ChargeDebit AccountNumber")
  @JsonbProperty("reqChargeDebitAccountNumber")
  public String reqChargeDebitAccountNumber;

  @Schema(required = true, example = "", description = "Req ChargeDebit AccountCurrency")
  @JsonbProperty("reqChargeDebitAccountCurrency")
  public String reqChargeDebitAccountCurrency;

  @Schema(
      required = true,
      example = "",
      description = "Req BENChargeAmount InChargeAccountCurrency")
  @JsonbProperty("reqBENChargeAmountInChargeAccountCurrency")
  public String reqBENChargeAmountInChargeAccountCurrency;

  @Schema(required = true, example = "", description = "Req BENChargeGLAccount")
  @JsonbProperty("reqBENChargeGLAccount")
  public String reqBENChargeGLAccount;

  @Schema(required = true, example = "", description = "Req BENChargeConversionRate")
  @JsonbProperty("reqBENChargeConversionRate")
  public String reqBENChargeConversionRate;

  @Schema(required = true, example = "", description = "Req BENChargeDesc")
  @JsonbProperty("reqBENChargeDesc")
  public String reqBENChargeDesc;

  @Schema(
      required = true,
      example = "",
      description = "Req ChargeAmountOUR InChargeAccountCurrency")
  @JsonbProperty("reqChargeAmountOURInChargeAccountCurrency")
  public String reqChargeAmountOURInChargeAccountCurrency;

  @Schema(required = true, example = "", description = "Req ChargeGLAccountOUR")
  @JsonbProperty("reqChargeGLAccountOUR")
  public String reqChargeGLAccountOUR;

  @Schema(required = true, example = "", description = "Req ChargeAmountOUR CorespondenceGLAmount")
  @JsonbProperty("reqChargeAmountOURCorespondenceGLAmount")
  public String reqChargeAmountOURCorespondenceGLAmount;

  @Schema(required = true, example = "", description = "Req ChargeOURCorespondenceGLAccount")
  @JsonbProperty("reqChargeOURCorespondenceGLAccount")
  public String reqChargeOURCorespondenceGLAccount;

  @Schema(required = true, example = "", description = "Req ChargeOURConversionRate")
  @JsonbProperty("reqChargeOURConversionRate")
  public String reqChargeOURConversionRate;

  @Schema(required = true, example = "", description = "Req ChargeOURDesc")
  @JsonbProperty("reqChargeOURDesc")
  public String reqChargeOURDesc;

  @Schema(required = true, example = "", description = "Req SHAChargeDebit AccountNumber")
  @JsonbProperty("reqSHAChargeDebitAccountNumber")
  public String reqSHAChargeDebitAccountNumber;

  @Schema(required = true, example = "", description = "Req SHAChargeGLAccount")
  @JsonbProperty("reqSHAChargeGLAccount")
  public String reqSHAChargeGLAccount;

  @Schema(
      required = true,
      example = "",
      description = "Req SHAChargeAmount InChargeAccountCurrency")
  @JsonbProperty("reqSHAChargeAmountInChargeAccountCurrency")
  public String reqSHAChargeAmountInChargeAccountCurrency;

  @Schema(required = true, example = "", description = "Req SHACharge DebitAccountCurrency")
  @JsonbProperty("reqSHAChargeDebitAccountCurrency")
  public String reqSHAChargeDebitAccountCurrency;

  @Schema(required = true, example = "", description = "Req ChargeSHA ConversionRate")
  @JsonbProperty("reqChargeSHAConversionRate")
  public String reqChargeSHAConversionRate;

  @Schema(required = true, example = "", description = "Req ChargeSHADesc")
  @JsonbProperty("reqChargeSHADesc")
  public String reqChargeSHADesc;

  @Schema(required = true, example = "", description = "Req BeneficiaryName")
  @JsonbProperty("reqBeneficiaryName")
  public String reqBeneficiaryName;

  @Schema(required = true, example = "", description = "Req BeneCity")
  @JsonbProperty("reqBeneCity")
  public String reqBeneCity;

  @Schema(required = true, example = "", description = "Req BeneCountry")
  @JsonbProperty("reqBeneCountry")
  public String reqBeneCountry;

  @Schema(required = true, example = "", description = "Req Beneficiary Address1")
  @JsonbProperty("reqBeneficiaryAddress1")
  public String reqBeneficiaryAddress1;

  @Schema(required = true, example = "", description = "Req Beneficiary Address2")
  @JsonbProperty("reqBeneficiaryAddress2")
  public String reqBeneficiaryAddress2;

  @Schema(required = true, example = "", description = "Req Beneficiary Address3")
  @JsonbProperty("reqBeneficiaryAddress3")
  public String reqBeneficiaryAddress3;

  @Schema(required = true, example = "847545", description = "Req CIF")
  @JsonbProperty("reqCIF")
  public String reqCIF;

  @Schema(required = true, example = "", description = "Req BeneBankName")
  @JsonbProperty("reqBeneBankName")
  public String reqBeneBankName;

  @Schema(required = true, example = "", description = "Req Beneficiary BankAddress1")
  @JsonbProperty("reqBeneficiaryBankAddress1")
  public String reqBeneficiaryBankAddress1;

  @Schema(required = true, example = "", description = "Req BeneBankCountry")
  @JsonbProperty("reqBeneBankCountry")
  public String reqBeneBankCountry;

  @Schema(required = true, example = "", description = "Req BeneficiaryBankBIC")
  @JsonbProperty("reqBeneficiaryBankBIC")
  public String reqBeneficiaryBankBIC;

  @Schema(required = true, example = "", description = "Req AccountBankFormat")
  @JsonbProperty("reqaccountBankFormat")
  public String reqAccountBankFormat;

  @Schema(required = true, example = "", description = "ReqSwiftRoutingCode")
  @JsonbProperty("reqswiftRoutingCode")
  public String reqSwiftRoutingCode;

  @Schema(required = true, example = "", description = "Req RecieverBankBIC")
  @JsonbProperty("reqRecieverBankBIC")
  public String reqRecieverBankBIC;

  @Schema(required = true, example = "", description = "Req ClearingCode")
  @JsonbProperty("reqClearingCode")
  public String reqClearingCode;

  @Schema(required = true, example = "", description = "Req RemittanceInfo")
  @JsonbProperty("reqRemittanceInfo")
  public String reqRemittanceInfo;

  @Schema(required = true, example = "", description = "Req ChargeType")
  @JsonbProperty("reqChargeType")
  public String reqChargeType;

  @Schema(required = true, example = "", description = "Req BanktoBankInfo")
  @JsonbProperty("reqBanktoBankInfo")
  public String reqBanktoBankInfo;

  @Schema(required = true, example = "", description = "Req DealReference")
  @JsonbProperty("reqDealReference")
  public String reqDealReference;

  @Schema(required = true, example = "", description = "Req DealerName")
  @JsonbProperty("reqDealerName")
  public String reqDealerName;

  @Schema(required = true, example = "", description = "Req DealRate")
  @JsonbProperty("reqDealRate")
  public String reqDealRate;

  @Schema(required = true, example = "", description = "Req TransferAmt")
  @JsonbProperty("reqTransferAmt")
  public String reqTransferAmt;

  @Schema(required = true, example = "", description = "Req Ordering CustomerName")
  @JsonbProperty("reqOrderingCustomerName")
  public String reqOrderingCustomerName;

  @Schema(required = true, example = "", description = "Req Ordering Customer Account")
  @JsonbProperty("reqOrderingCustomerAccount")
  public String reqOrderingCustomerAccount;

  @Schema(required = true, example = "", description = "Req Ordering Customer Address1")
  @JsonbProperty("reqOrderingCustomerAddress1")
  public String reqOrderingCustomerAddress1;

  @Schema(required = true, example = "", description = "Req Ordering Customer Address2")
  @JsonbProperty("reqOrderingCustomerAddress2")
  public String reqOrderingCustomerAddress2;

  @Schema(required = true, example = "", description = "Req Ordering Customer Address3")
  @JsonbProperty("reqOrderingCustomerAddress3")
  public String reqOrderingCustomerAddress3;

  @Schema(required = true, example = "", description = "Req Ordering Customer Address4")
  @JsonbProperty("reqOrderingCustomerAddress4")
  public String reqOrderingCustomerAddress4;

  @Schema(required = true, example = "", description = "Req PayCurrency")
  @JsonbProperty("reqPayCurrency")
  public String reqPayCurrency;

  @Schema(required = true, example = "", description = "Req DebitBranch")
  @JsonbProperty("reqDebitBranch")
  public String reqDebitBranch;

  @Schema(required = true, example = "", description = "Req ChannelCode")
  @JsonbProperty("reqChannelCode")
  public String reqChannelCode;

  @Schema(required = true, example = "", description = "Req Beneficiary Address4")
  @JsonbProperty("reqBeneficiaryAddress4")
  public String reqBeneficiaryAddress4;

  @Schema(required = true, example = "", description = "Req ChargeAmount In LocalCurrency")
  @JsonbProperty("reqChargeAmountInLocalCurrency")
  public String reqChargeAmountInLocalCurrency;

  @Schema(required = true, example = "", description = "Req BatchReferenceNo")
  @JsonbProperty("reqBatchReferenceNo")
  public String reqBatchReferenceNo;

  @Schema(required = true, example = "", description = "Req PymntType")
  @JsonbProperty("reqPymntType")
  public String reqPymntType;

  @Schema(required = true, example = "", description = "Req First LegRefNo")
  @JsonbProperty("reqFirstLegRefNo")
  public String reqFirstLegRefNo;
}
