package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
    name = "SalaryBatchRequestData",
    description = "request object with hdrTranId, hdrStatus, hdrStatus, cifNo...etc")
public class SalaryBatchRequestData {

  @Schema(required = false, example = "DFT", description = "Req ConversionRate1")
  @JsonbProperty("reqConversionRate1")
  public String reqConversionRate1;

  @Schema(required = false, example = "CROSSBORDERFUNDTRAN", description = "Req ConversionRate2")
  @JsonbProperty("reqConversionRate2")
  public String reqConversionRate2;

  @Schema(required = false, example = "+2547289182776", description = "Req Mobile Number")
  @JsonbProperty("reqMobileNumber")
  public String reqMobileNumber;

  @Schema(required = true, example = "7519350046", description = "Req Beneficiary Account")
  @JsonbProperty("reqBeneficiaryAccount")
  public String reqBeneficiaryAccount;

  @Schema(required = true, example = "KES", description = "Req DebitAcc Currency")
  @JsonbProperty("reqDebitAcCurrency")
  public String reqDebitAcCurrency;

  @Schema(required = true, example = "100", description = "Req Debit Amount")
  @JsonbProperty("reqDebitAmount")
  public String reqDebitAmount;

  @Schema(required = true, example = "KES", description = "Req CreditAcCurrency")
  @JsonbProperty("reqCreditAcCurrency")
  public String reqCreditAcCurrency;

  @Schema(required = false, example = "100", description = "Req Credit Amount")
  @JsonbProperty("reqCreditAmount")
  public String reqCreditAmount;

  @Schema(required = false, example = "NA", description = "Req CrNarration")
  @JsonbProperty("reqCrNarration")
  public String reqCrNarration;

  @Schema(required = false, example = "N", description = "Req Force Credit Flag")
  @JsonbProperty("reqForceCreditFlag")
  public String reqForceCreditFlag;

  @Schema(required = false, example = "000", description = "Req Card Payment")
  @JsonbProperty("reqCardPayment")
  public String reqCardPayment;

  @Schema(required = false, example = "59", description = "Req Purpose code")
  @JsonbProperty("reqPurposecode")
  public String reqPurposecode;

  @Schema(required = false, example = "08082019", description = "Req TransactionDate")
  @JsonbProperty("reqTransactionDate")
  public String reqTransactionDate;

  @Schema(required = false, example = "2022-09-10", description = "Req ValueDate")
  @JsonbProperty("reqValueDate")
  public String reqValueDate;

  @Schema(required = false, example = "", description = "Req ChargeDebitAccountNumber")
  @JsonbProperty("reqChargeDebitAccountNumber")
  public String reqChargeDebitAccountNumber;

  @Schema(required = false, example = "", description = "Req ChargeDebitAccountCurrency")
  @JsonbProperty("reqChargeDebitAccountCurrency")
  public String reqChargeDebitAccountCurrency;

  @Schema(
      required = false,
      example = "",
      description = "Req BENChargeAmountInChargeAccountCurrency")
  @JsonbProperty("reqBENChargeAmountInChargeAccountCurrency")
  public String reqBENChargeAmountInChargeAccountCurrency;

  @Schema(required = false, example = "", description = "Req BENChargeGLAccount")
  @JsonbProperty("reqBENChargeGLAccount")
  public String reqBENChargeGLAccount;

  @Schema(required = false, example = "", description = "req BENCharge ConversionRate")
  @JsonbProperty("reqBENChargeConversionRate")
  public String reqBENChargeConversionRate;

  @Schema(required = false, example = "", description = "req BENChargeDesc")
  @JsonbProperty("reqBENChargeDesc")
  public String reqBENChargeDesc;

  @Schema(
      required = false,
      example = "",
      description = "req ChargeAmount OUR InCharge Account Currency")
  @JsonbProperty("reqChargeAmountOURInChargeAccountCurrency")
  public String reqChargeAmountOURInChargeAccountCurrency;

  @Schema(required = false, example = "", description = "req ChargeGLAccountOUR")
  @JsonbProperty("reqChargeGLAccountOUR")
  public String reqChargeGLAccountOUR;

  @Schema(
      required = false,
      example = "",
      description = "req ChargeAmount OUR Corespondence GLAmount")
  @JsonbProperty("reqChargeAmountOURCorespondenceGLAmount")
  public String reqChargeAmountOURCorespondenceGLAmount;

  @Schema(required = false, example = "", description = "req Charge OUR Corespondence GLAccount")
  @JsonbProperty("reqChargeOURCorespondenceGLAccount")
  public String reqChargeOURCorespondenceGLAccount;

  @Schema(required = false, example = "", description = "req ChargeOURConversionRate")
  @JsonbProperty("reqChargeOURConversionRate")
  public String reqChargeOURConversionRate;

  @Schema(required = false, example = "", description = "req ChargeOURDesc")
  @JsonbProperty("reqChargeOURDesc")
  public String reqChargeOURDesc;

  @Schema(required = false, example = "", description = "req SHAChargeDebit AccountNumber")
  @JsonbProperty("reqSHAChargeDebitAccountNumber")
  public String reqSHAChargeDebitAccountNumber;

  @Schema(required = false, example = "", description = "req SHACharge GLAccount")
  @JsonbProperty("reqSHAChargeGLAccount")
  public String reqSHAChargeGLAccount;

  @Schema(
      required = false,
      example = "",
      description = "req SHAChargeAmount InCharge AccountCurrency")
  @JsonbProperty("reqSHAChargeAmountInChargeAccountCurrency")
  public String reqSHAChargeAmountInChargeAccountCurrency;

  @Schema(required = false, example = "", description = "req SHACharge Debit AccountCurrency")
  @JsonbProperty("reqSHAChargeDebitAccountCurrency")
  public String reqSHAChargeDebitAccountCurrency;

  @Schema(required = false, example = "", description = "req ChargeSHA ConversionRate")
  @JsonbProperty("reqChargeSHAConversionRate")
  public String reqChargeSHAConversionRate;

  @Schema(required = false, example = "", description = "req Charge SHADesc")
  @JsonbProperty("reqChargeSHADesc")
  public String reqChargeSHADesc;

  @Schema(required = false, example = "GILBERT NGENO", description = "req BeneficiaryName")
  @JsonbProperty("reqBeneficiaryName")
  public String reqBeneficiaryName;

  @Schema(required = false, example = "", description = "req BeneCity")
  @JsonbProperty("reqBeneCity")
  public String reqBeneCity;

  @Schema(required = false, example = "KE", description = "req BeneCountry")
  @JsonbProperty("reqBeneCountry")
  public String reqBeneCountry;

  @Schema(required = false, example = "NAIROBI", description = "req Beneficiary Address1")
  @JsonbProperty("reqBeneficiaryAddress1")
  public String reqBeneficiaryAddress1;

  @Schema(required = false, example = "KENYA", description = "req Beneficiary Address2")
  @JsonbProperty("reqBeneficiaryAddress2")
  public String reqBeneficiaryAddress2;

  @Schema(required = false, example = "", description = "req Beneficiary Address3")
  @JsonbProperty("reqBeneficiaryAddress3")
  public String reqBeneficiaryAddress3;

  @Schema(required = false, example = "167218", description = "req CIF")
  @JsonbProperty("reqCIF")
  public String reqCIF;

  @Schema(required = false, example = "NCBA", description = "req BeneBankName")
  @JsonbProperty("reqBeneBankName")
  public String reqBeneBankName;

  @Schema(required = false, example = "", description = "req Beneficiary BankAddress1")
  @JsonbProperty("reqBeneficiaryBankAddress1")
  public String reqBeneficiaryBankAddress1;

  @Schema(required = false, example = "", description = "req BeneBank Country")
  @JsonbProperty("reqBeneBankCountry")
  public String reqBeneBankCountry;

  @Schema(required = true, example = "ICRAITR1TTI", description = "req Beneficiary BankBIC")
  @JsonbProperty("reqBeneficiaryBankBIC")
  public String reqBeneficiaryBankBIC;

  @Schema(required = false, example = "", description = "req account BankFormat")
  @JsonbProperty("reqAccountBankFormat")
  public String reqAccountBankFormat;

  @Schema(required = false, example = "", description = "req SwiftRoutingCode")
  @JsonbProperty("reqSwiftRoutingCode")
  public String reqSwiftRoutingCode;

  @Schema(required = false, example = "", description = "req Reciever BankBIC")
  @JsonbProperty("reqRecieverBankBIC")
  public String reqRecieverBankBIC;

  @Schema(required = false, example = "", description = "req ClearingCode")
  @JsonbProperty("reqClearingCode")
  public String reqClearingCode;

  @Schema(required = false, example = "", description = "req RemittanceInfo")
  @JsonbProperty("reqRemittanceInfo")
  public String reqRemittanceInfo;

  @Schema(required = false, example = "", description = "req ChargeType")
  @JsonbProperty("reqChargeType")
  public String reqChargeType;

  @Schema(required = false, example = "", description = "req BanktoBankInfo")
  @JsonbProperty("reqBanktoBankInfo")
  public String reqBanktoBankInfo;

  @Schema(required = false, example = "", description = "req DealReference")
  @JsonbProperty("reqDealReference")
  public String reqDealReference;

  @Schema(required = false, example = "", description = "req DealerName")
  @JsonbProperty("reqDealerName")
  public String reqDealerName;

  @Schema(required = false, example = "", description = "req DealRate")
  @JsonbProperty("reqDealRate")
  public String reqDealRate;

  @Schema(required = false, example = "", description = "req TransferAmt")
  @JsonbProperty("reqTransferAmt")
  public String reqTransferAmt;

  @Schema(required = false, example = "", description = "req Ordering CustomerName")
  @JsonbProperty("reqOrderingCustomerName")
  public String reqOrderingCustomerName;

  @Schema(required = false, example = "", description = "req Ordering Customer Account")
  @JsonbProperty("reqOrderingCustomerAccount")
  public String reqOrderingCustomerAccount;

  @Schema(required = false, example = "", description = "req Ordering Customer Address1")
  @JsonbProperty("reqOrderingCustomerAddress1")
  public String reqOrderingCustomerAddress1;

  @Schema(required = false, example = "", description = "req Ordering Customer Address2")
  @JsonbProperty("reqOrderingCustomerAddress2")
  public String reqOrderingCustomerAddress2;

  @Schema(required = false, example = "", description = "req Ordering Customer Address3")
  @JsonbProperty("reqOrderingCustomerAddress3")
  public String reqOrderingCustomerAddress3;

  @Schema(required = false, example = "", description = "req Ordering Customer Address4")
  @JsonbProperty("reqOrderingCustomerAddress4")
  public String reqOrderingCustomerAddress4;

  @Schema(required = false, example = "USD", description = "req PayCurrency")
  @JsonbProperty("reqPayCurrency")
  public String reqPayCurrency;

  @Schema(required = false, example = "", description = "req DebitBranch")
  @JsonbProperty("reqDebitBranch")
  public String reqDebitBranch;

  @Schema(required = false, example = "", description = "req Channel Code")
  @JsonbProperty("reqChannelCode")
  public String reqChannelCode;

  @Schema(required = false, example = "", description = "req Beneficiary Address4")
  @JsonbProperty("reqBeneficiaryAddress4")
  public String reqBeneficiaryAddress4;

  @Schema(required = false, example = "", description = "req Charge Amount In Local Currency")
  @JsonbProperty("reqChargeAmountInLocalCurrency")
  public String reqChargeAmountInLocalCurrency;

  @Schema(required = false, example = "20208ABC", description = "req Batch ReferenceNo")
  @JsonbProperty("reqBatchReferenceNo")
  public String reqBatchReferenceNo;

  @Schema(
      required = true,
      example = "",
      description = "req Pymnt Type",
      enumeration = {"RTGS", "SaccoTransfer", "EFT", "PesaLink", "MobileAirtimeTransfer"})
  @JsonbProperty("reqPymntType")
  public String reqPymntType;

  @Schema(required = false, example = "", description = "req First Leg Ref No")
  @JsonbProperty("reqFirstLegRefNo")
  public String reqFirstLegRefNo;
}
