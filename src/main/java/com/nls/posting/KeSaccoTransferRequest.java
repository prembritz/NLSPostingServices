package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({
  "HdrTranId",
  "HdrStatus",
  "reqChnlId",
  "reqUnitid",
  "reqChannelname",
  "reqChannelreqtime",
  "reqPostingbranch",
  "reqTransactionreferenceno",
  "reqTransactioncode",
  "reqAccountno",
  "reqDebitaccurrency",
  "reqDebitamount",
  "reqDrnarration",
  "reqForcedebitflag",
  "reqConversionrate1",
  "reqConversionrate2",
  "reqBeneficiaryaccount",
  "reqCreditaccurrency",
  "reqCreditamount",
  "reqCrnarration",
  "reqForcecreditflag",
  "reqCardpayment",
  "reqPurposeCode",
  "reqTransactiondate",
  "reqValuedate",
  "reqChargedebitaccountnumber",
  "reqChargedebitaccountcurrency",
  "reqBenchargeamountinchargeaccountcurrency",
  "reqBenchargeglaccount",
  "reqBenchargeconversionrate",
  "reqBenchargedesc",
  "reqChargeamountourinchargeaccountcurrency",
  "reqChargeglaccountour",
  "reqChargeamountourcorespondenceglamount",
  "reqChargeourcorespondenceglaccount",
  "reqChargeourconversionrate",
  "reqChargeourdesc",
  "reqShachargedebitaccountnumber",
  "reqShachargeglaccount",
  "reqShachargeamountinchargeaccountcurrency",
  "reqShachargedebitaccountcurrency",
  "reqChargeshaconversionrate",
  "reqChargeshadesc",
  "reqBeneficiaryname",
  "reqBenecity",
  "reqBenecountry",
  "reqBeneficiaryaddress1",
  "reqBeneficiaryaddress2",
  "reqBeneficiaryaddress3",
  "reqCif",
  "reqBenebankname",
  "reqBeneficiarybankaddress1",
  "reqBenebankcountry",
  "reqBeneficiarybankbic",
  "reqAccountbankformat",
  "reqSwiftroutingcode",
  "reqRecieverbankbic",
  "reqClearingcode",
  "reqRemittanceinfo",
  "reqChargetype",
  "reqBanktobankinfo",
  "reqDealreference",
  "reqDealername",
  "reqDealrate",
  "reqTransferamt",
  "reqOrderingcustomername",
  "reqOrderingcustomeraccount",
  "reqOrderingcustomeraddress1",
  "reqOrderingcustomeraddress2",
  "reqOrderingcustomeraddress3",
  "reqOrderingcustomeraddress4",
  "reqPaycurrency",
  "reqDebitbranch",
  "reqChannelcode",
  "reqBeneficiaryaddress4",
  "reqChargeamountinlocalcurrency",
  "reqBatchreferenceno",
  "reqPymnttype",
  "reqFirstLegRefNo",
  "vipMarker",
  "waiveCharge"
})
@Schema(
    name = "KE Sacco request",
    description = "request object with HdrTranId, HdrStatus, reqUnitid, reqChannelname..etc")
public class KeSaccoTransferRequest {
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

  @Schema(required = true, example = "SACCO", description = "hdrTranId")
  @JsonbProperty("hdrTranId")
  public String HdrTranId;

  @Schema(required = false, example = "SUCCESS", description = "hdrStatus")
  @JsonbProperty("hdrStatus")
  public String HdrStatus;

  @Schema(required = true, example = "AFDFD", description = "reqChnlId")
  @JsonbProperty("reqChnlId")
  public String reqChnlId;

  @Schema(
      required = true,
      example = "KE0010001",
      description = "req UnitID",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("reqUnitID")
  public String reqUnitID;

  @Schema(required = true, example = "ABC", description = "reqChannelName")
  @JsonbProperty("reqChannelName")
  public String reqChannelName;

  @Schema(required = false, example = "", description = "reqChannelReqTime")
  @JsonbProperty("reqChannelReqTime")
  public String reqChannelReqTime;

  @Schema(required = false, example = "", description = "reqPostingBranch")
  @JsonbProperty("reqPostingBranch")
  public String reqPostingBranch;

  @Schema(required = true, example = "TFA8090900888", description = "reqTransactionReferenceNo")
  @JsonbProperty("reqTransactionReferenceNo")
  public String reqTransactionReferenceNo;

  @Schema(required = false, example = "", description = "reqTransactionCode")
  @JsonbProperty("reqTransactionCode")
  public String reqTransactionCode;

  @Schema(required = true, example = "1711460011", description = "reqAccountNo")
  @JsonbProperty("reqAccountNo")
  public String reqAccountNo;

  @Schema(required = true, example = "KES", description = "reqDebitAcCurrency")
  @JsonbProperty("reqDebitAcCurrency")
  public String reqDebitAcCurrency;

  @Schema(required = true, example = "100", description = "reqDebitAmount")
  @JsonbProperty("reqDebitAmount")
  public String reqDebitAmount;

  @Schema(required = false, example = "test", description = "reqDrNarration")
  @JsonbProperty("reqDrNarration")
  public String reqDrNarration;

  @Schema(required = false, example = "", description = "reqForceDebitFlag")
  @JsonbProperty("reqForceDebitFlag")
  public String reqForceDebitFlag;

  @Schema(required = false, example = "", description = "reqConversionRate1")
  @JsonbProperty("reqConversionRate1")
  public String reqConversionRate1;

  @Schema(required = false, example = "", description = "reqConversionRate2")
  @JsonbProperty("reqConversionRate2")
  public String reqConversionRate2;

  @Schema(required = true, example = "7519350046", description = "reqBeneficiaryAccount")
  @JsonbProperty("reqBeneficiaryAccount")
  public String reqBeneficiaryAccount;

  @Schema(required = false, example = "", description = "reqCreditAcCurrency")
  @JsonbProperty("reqCreditAcCurrency")
  public String reqCreditAcCurrency;

  @Schema(required = false, example = "", description = "reqCreditAmount")
  @JsonbProperty("reqCreditAmount")
  public String reqCreditAmount;

  @Schema(required = false, example = "", description = "reqCrNarration")
  @JsonbProperty("reqCrNarration")
  public String reqCrNarration;

  @Schema(required = false, example = "1232", description = "reqForceCreditFlag")
  @JsonbProperty("reqForceCreditFlag")
  public String reqForceCreditFlag;

  @Schema(required = false, example = "", description = "reqCardPayment")
  @JsonbProperty("reqCardPayment")
  public String reqCardPayment;

  @Schema(required = false, example = "", description = "reqPurposecode")
  @JsonbProperty("reqPurposecode")
  public String reqPurposecode;

  @Schema(required = false, example = "", description = "reqTransactionDate")
  @JsonbProperty("reqTransactionDate")
  public String reqTransactionDate;

  @Schema(required = false, example = "", description = "reqValueDate")
  @JsonbProperty("reqValueDate")
  public String reqValueDate;

  @Schema(required = false, example = "", description = "reqChargeDebitAccountNumber")
  @JsonbProperty("reqChargeDebitAccountNumber")
  public String reqChargeDebitAccountNumber;

  @Schema(required = false, example = "", description = "reqChargeDebitAccountCurrency")
  @JsonbProperty("reqChargeDebitAccountCurrency")
  public String reqChargeDebitAccountCurrency;

  @Schema(required = false, example = "", description = "reqBENChargeAmountInChargeAccountCurrency")
  @JsonbProperty("reqBENChargeAmountInChargeAccountCurrency")
  public String reqBENChargeAmountInChargeAccountCurrency;

  @Schema(required = false, example = "", description = "reqBENChargeGLAccount")
  @JsonbProperty("reqBENChargeGLAccount")
  public String reqBENChargeGLAccount;

  @Schema(required = false, example = "", description = "reqBENChargeConversionRate")
  @JsonbProperty("reqBENChargeConversionRate")
  public String reqBENChargeConversionRate;

  @Schema(required = false, example = "", description = "reqBENChargeDesc")
  @JsonbProperty("reqBENChargeDesc")
  public String reqBENChargeDesc;

  @Schema(required = false, example = "", description = "reqChargeAmountOURInChargeAccountCurrency")
  @JsonbProperty("reqChargeAmountOURInChargeAccountCurrency")
  public String reqChargeAmountOURInChargeAccountCurrency;

  @Schema(required = false, example = "", description = "reqChargeGLAccountOUR")
  @JsonbProperty("reqChargeGLAccountOUR")
  public String reqChargeGLAccountOUR;

  @Schema(required = false, example = "", description = "reqChargeAmountOURCorespondenceGLAmount")
  @JsonbProperty("reqChargeAmountOURCorespondenceGLAmount")
  public String reqChargeAmountOURCorespondenceGLAmount;

  @Schema(required = false, example = "", description = "reqChargeOURCorespondenceGLAccount")
  @JsonbProperty("reqChargeOURCorespondenceGLAccount")
  public String reqChargeOURCorespondenceGLAccount;

  @Schema(required = false, example = "", description = "reqChargeOURConversionRate")
  @JsonbProperty("reqChargeOURConversionRate")
  public String reqChargeOURConversionRate;

  @Schema(required = false, example = "", description = "reqChargeOURDesc")
  @JsonbProperty("reqChargeOURDesc")
  public String reqChargeOURDesc;

  @Schema(required = false, example = "", description = "reqSHAChargeDebitAccountNumber")
  @JsonbProperty("reqSHAChargeDebitAccountNumber")
  public String reqSHAChargeDebitAccountNumber;

  @Schema(required = false, example = "", description = "reqSHAChargeGLAccount")
  @JsonbProperty("reqSHAChargeGLAccount")
  public String reqSHAChargeGLAccount;

  @Schema(required = false, example = "", description = "reqSHAChargeAmountInChargeAccountCurrency")
  @JsonbProperty("reqSHAChargeAmountInChargeAccountCurrency")
  public String reqSHAChargeAmountInChargeAccountCurrency;

  @Schema(required = false, example = "", description = "reqSHAChargeDebitAccountCurrency")
  @JsonbProperty("reqSHAChargeDebitAccountCurrency")
  public String reqSHAChargeDebitAccountCurrency;

  @Schema(required = false, example = "", description = "reqChargeSHAConversionRate")
  @JsonbProperty("reqChargeSHAConversionRate")
  public String reqChargeSHAConversionRate;

  @Schema(required = false, example = "", description = "reqChargeSHADesc")
  @JsonbProperty("reqChargeSHADesc")
  public String reqChargeSHADesc;

  @Schema(required = false, example = "", description = "reqBeneficiaryName")
  @JsonbProperty("reqBeneficiaryName")
  public String reqBeneficiaryName;

  @Schema(required = false, example = "", description = "reqBeneCity")
  @JsonbProperty("reqBeneCity")
  public String reqBeneCity;

  @Schema(required = false, example = "", description = "reqBeneCountry")
  @JsonbProperty("reqBeneCountry")
  public String reqBeneCountry;

  @Schema(required = false, example = "", description = "reqBeneficiaryAddress1")
  @JsonbProperty("reqBeneficiaryAddress1")
  public String reqBeneficiaryAddress1;

  @Schema(required = false, example = "", description = "reqBeneficiaryAddress2")
  @JsonbProperty("reqBeneficiaryAddress2")
  public String reqBeneficiaryAddress2;

  @Schema(required = false, example = "", description = "reqBeneficiaryAddress3")
  @JsonbProperty("reqBeneficiaryAddress3")
  public String reqBeneficiaryAddress3;

  @Schema(required = false, example = "", description = "reqCIF")
  @JsonbProperty("reqCIF")
  public String reqCIF;

  @Schema(required = false, example = "", description = "reqBeneBankName")
  @JsonbProperty("reqBeneBankName")
  public String reqBeneBankName;

  @Schema(required = false, example = "", description = "reqBeneficiaryBankAddress1")
  @JsonbProperty("reqBeneficiaryBankAddress1")
  public String reqBeneficiaryBankAddress1;

  @Schema(required = false, example = "", description = "reqBeneBankCountry")
  @JsonbProperty("reqBeneBankCountry")
  public String reqBeneBankCountry;

  @Schema(required = true, example = "", description = "reqBeneficiaryBankBIC")
  @JsonbProperty("reqBeneficiaryBankBIC")
  public String reqBeneficiaryBankBIC;

  @Schema(required = false, example = "", description = "reqaccountBankFormat")
  @JsonbProperty("reqaccountBankFormat")
  public String reqaccountBankFormat;

  @Schema(required = false, example = "", description = "reqswiftRoutingCode")
  @JsonbProperty("reqswiftRoutingCode")
  public String reqswiftRoutingCode;

  @Schema(required = false, example = "", description = "reqRecieverBankBIC")
  @JsonbProperty("reqRecieverBankBIC")
  public String reqRecieverBankBIC;

  @Schema(required = false, example = "", description = "reqClearingCode")
  @JsonbProperty("reqClearingCode")
  public String reqClearingCode;

  @Schema(required = false, example = "", description = "reqRemittanceInfo")
  @JsonbProperty("reqRemittanceInfo")
  public String reqRemittanceInfo;

  @Schema(required = false, example = "", description = "reqChargeType")
  @JsonbProperty("reqChargeType")
  public String reqChargeType;

  @Schema(required = false, example = "", description = "reqBanktoBankInfo")
  @JsonbProperty("reqBanktoBankInfo")
  public String reqBanktoBankInfo;

  @Schema(required = false, example = "", description = "reqDealReference")
  @JsonbProperty("reqDealReference")
  public String reqDealReference;

  @Schema(required = false, example = "", description = "reqDealerName")
  @JsonbProperty("reqDealerName")
  public String reqDealerName;

  @Schema(required = false, example = "", description = "reqDealRate")
  @JsonbProperty("reqDealRate")
  public String reqDealRate;

  @Schema(required = false, example = "", description = "reqTransferAmt")
  @JsonbProperty("reqTransferAmt")
  public String reqTransferAmt;

  @Schema(required = false, example = "", description = "reqOrderingCustomerName")
  @JsonbProperty("reqOrderingCustomerName")
  public String reqOrderingCustomerName;

  @Schema(required = false, example = "", description = "reqOrderingCustomerAccount")
  @JsonbProperty("reqOrderingCustomerAccount")
  public String reqOrderingCustomerAccount;

  @Schema(required = false, example = "", description = "reqOrderingCustomerAddress1")
  @JsonbProperty("reqOrderingCustomerAddress1")
  public String reqOrderingCustomerAddress1;

  @Schema(required = false, example = "", description = "reqOrderingCustomerAddress2")
  @JsonbProperty("reqOrderingCustomerAddress2")
  public String reqOrderingCustomerAddress2;

  @Schema(required = false, example = "", description = "reqOrderingCustomerAddress3")
  @JsonbProperty("reqOrderingCustomerAddress3")
  public String reqOrderingCustomerAddress3;

  @Schema(required = false, example = "", description = "reqOrderingCustomerAddress4")
  @JsonbProperty("reqOrderingCustomerAddress4")
  public String reqOrderingCustomerAddress4;

  @Schema(required = false, example = "", description = "reqPayCurrency")
  @JsonbProperty("reqPayCurrency")
  public String reqPayCurrency;

  @Schema(required = false, example = "", description = "reqDebitBranch")
  @JsonbProperty("reqDebitBranch")
  public String reqDebitBranch;

  @Schema(required = false, example = "", description = "reqChannelCode")
  @JsonbProperty("reqChannelCode")
  public String reqChannelCode;

  @Schema(required = false, example = "", description = "reqBeneficiaryAddress4")
  @JsonbProperty("reqBeneficiaryAddress4")
  public String reqBeneficiaryAddress4;

  @Schema(required = false, example = "", description = "reqChargeAmountInLocalCurrency")
  @JsonbProperty("reqChargeAmountInLocalCurrency")
  public String reqChargeAmountInLocalCurrency;

  @Schema(required = false, example = "", description = "reqBatchReferenceNo")
  @JsonbProperty("reqBatchReferenceNo")
  public String reqBatchReferenceNo;

  @Schema(required = false, example = "", description = "reqPymntType")
  @JsonbProperty("reqPymntType")
  public String reqPymntType;

  @Schema(required = false, example = "", description = "reqFirstLegRefNo")
  @JsonbProperty("reqFirstLegRefNo")
  public String reqFirstLegRefNo;
}
