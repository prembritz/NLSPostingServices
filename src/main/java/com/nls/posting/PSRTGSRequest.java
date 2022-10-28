package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({
  "transactionId",
  "transStatus",
  "channelId",
  "unitId",
  "channelName",
  "channelRequestTime",
  "postingBranch",
  "transReference",
  "transCode",
  "accountNo",
  "debitAccCurrency",
  "debitAmt",
  "debitNarration",
  "forceDebitFlag",
  "conversionRate1",
  "conversionRate2",
  "benefAccount",
  "creditAccCurrency",
  "creditAmt",
  "creditNarration",
  "forceCreditFlag",
  "cardPayment",
  "purposeCode",
  "transactionDate",
  "valueDate",
  "chargeDebtAccNumber",
  "chargeDebtAccCurrency",
  "benefChargeAmt",
  "benChrgGLAccount",
  "benchrgConversionRate",
  "benChargeDesc",
  "chargeAmtOurChrgAccCurrency",
  "chargeGLAccOur",
  "chrgAmtOurCorresGLAmt",
  "chrgOurCorresGLAmt",
  "chrgOurConversionRate",
  "chrgOurDesc",
  "chrgDebitAccNumber",
  "chargeGLAccount",
  "chrgAmtChrgAccCurrency",
  "chrgDebitAccCurrency",
  "chrgSHAConversionRate",
  "chrgSHADesc",
  "benefName",
  "benefCity",
  "benefCountry",
  "benefAddress1",
  "benefAddress2",
  "benefAddress3",
  "cif",
  "benefBankName",
  "benefBankAddress1",
  "benefBankCurrency",
  "benefBankBIC",
  "accountBankFormat",
  "swiftRoutingCode",
  "receiverBankBIC",
  "clearingCode",
  "remittenceInfo",
  "chargeType",
  "banktobankInfo",
  "dealReference",
  "dealerName",
  "dealRate",
  "transferAmt",
  "orderingCustomerName",
  "orderingCustomerAccount",
  "orderingCustomerAddress1",
  "orderingCustomerAddress2",
  "orderingCustomerAddress3",
  "orderingCustomerAddress4",
  "payCurrency",
  "debitBranch",
  "channelCode",
  "benefAddress4",
  "chrgAmtLocalcurrency",
  "batchReferenceNo",
  "paymentType",
  "firstLegRefno",
  "vipMarker",
  "waiveCharge"
})
@Schema(
    name = "PSRTGSRequest",
    description =
        "request object with HdrTranId, hdrStatus, reqChnlId, reqUnitID, reqChannelName...etc")
public class PSRTGSRequest {

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

  @Schema(required = true, example = "27209ABC", description = "Transaction Id")
  @JsonbProperty("hdrTranId")
  public String transactionId;

  @Schema(required = false, example = "success", description = "Status")
  @JsonbProperty("hdrStatus")
  public String transStatus;

  @Schema(required = true, example = "CMS", description = "Channel Id")
  @JsonbProperty("reqChnlId")
  public String channelId;

  @Schema(
      required = true,
      example = "KE0010001",
      description = "req UnitID",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("reqUnitID")
  public String unitId;

  @Schema(required = true, example = "CIB", description = "Channel Name")
  @JsonbProperty("reqChannelName")
  public String channelName;

  @Schema(required = false, example = "", description = "Channel Request Time")
  @JsonbProperty("reqChannelReqTime")
  public String channelRequestTime;

  @Schema(required = false, example = "", description = "Posting Branch")
  @JsonbProperty("reqPostingBranch")
  public String postingBranch;

  @Schema(required = true, example = "TFA8090900999", description = "Transaction Reference No")
  @JsonbProperty("reqTransactionReferenceNo")
  public String transReference;

  @Schema(required = false, example = "", description = "Transaction Code")
  @JsonbProperty("reqTransactionCode")
  public String transCode;

  @Schema(required = true, example = "1711460011", description = "Debit Account Number")
  @JsonbProperty("reqAccountNo")
  public String accountNo;

  @Schema(required = true, example = "KES", description = "Debit Account Currency")
  @JsonbProperty("reqDebitAcCurrency")
  public String debitAccCurrency;

  @Schema(required = true, example = "50", description = "Debit Amount")
  @JsonbProperty("reqDebitAmount")
  public String debitAmt;

  @Schema(required = false, example = "Testing RTGS", description = "Debit Narration")
  @JsonbProperty("reqDrNarration")
  public String debitNarration;

  @Schema(required = false, example = "", description = "Force Debit Flag")
  @JsonbProperty("reqForceDebitFlag")
  public String forceDebitFlag;

  @Schema(required = false, example = "", description = "Conversion Rate 1")
  @JsonbProperty("reqConversionRate1")
  public String conversionRate1;

  @Schema(required = false, example = "", description = "Conversion Rate 2")
  @JsonbProperty("reqConversionRate2")
  public String conversionRate2;

  @Schema(required = true, example = "7519350046", description = "Beneficiary Account")
  @JsonbProperty("reqBeneficiaryAccount")
  public String benefAccount;

  @Schema(required = false, example = "KES", description = "Credit Account currency")
  @JsonbProperty("reqCreditAcCurrency")
  public String creditAccCurrency;

  @Schema(required = false, example = "", description = "Credit Amount")
  @JsonbProperty("reqCreditAmount")
  public String creditAmt;

  @Schema(required = false, example = "", description = "Credit Narration")
  @JsonbProperty("reqCrNarration")
  public String creditNarration;

  @Schema(required = false, example = "", description = "Force Credit Flag")
  @JsonbProperty("reqForceCreditFlag")
  public String forceCreditFlag;

  @Schema(required = false, example = "", description = "Card Payment")
  @JsonbProperty("reqCardPayment")
  public String cardPayment;

  @Schema(required = false, example = "", description = "Purpose code")
  @JsonbProperty("reqPurposecode")
  public String purposeCode;

  @Schema(required = false, example = "", description = "Transaction Date")
  @JsonbProperty("reqTransactionDate")
  public String transactionDate;

  @Schema(required = false, example = "", description = "Value Date")
  @JsonbProperty("reqValueDate")
  public String valueDate;

  @Schema(required = false, example = "", description = "Charge Debit Account Number")
  @JsonbProperty("reqChargeDebitAccountNumber")
  public String chargeDebtAccNumber;

  @Schema(required = false, example = "", description = "Charge Debit Account Currency")
  @JsonbProperty("reqChargeDebitAccountCurrency")
  public String chargeDebtAccCurrency;

  @Schema(
      required = false,
      example = "",
      description = "Beneficiary Charge Amount Charge Account Currency")
  @JsonbProperty("reqBENChargeAmountInChargeAccountCurrency")
  public String benefChargeAmt;

  @Schema(required = false, example = "", description = "Beneficiary Charge GL Account")
  @JsonbProperty("reqBENChargeGLAccount")
  public String benChrgGLAccount;

  @Schema(required = false, example = "", description = "Beneficiary Charge Conversion Rate")
  @JsonbProperty("reqBENChargeConversionRate")
  public String benchrgConversionRate;

  @Schema(required = false, example = "", description = "Beneficiary Charge Desc")
  @JsonbProperty("reqBENChargeDesc")
  public String benChargeDesc;

  @Schema(required = false, example = "", description = "Charge Amount OUR charge Account Currency")
  @JsonbProperty("reqChargeAmountOURInChargeAccountCurrency")
  public String chargeAmtOurChrgAccCurrency;

  @Schema(required = false, example = "", description = "Charge GL Account OUR")
  @JsonbProperty("reqChargeGLAccountOUR")
  public String chargeGLAccOur;

  @Schema(
      required = false,
      example = "",
      description = "Charge Amount OUR correspondence GL Amount")
  @JsonbProperty("reqChargeAmountOURCorespondenceGLAmount")
  public String chrgAmtOurCorresGLAmt;

  @Schema(required = false, example = "", description = "Charge OUR Correspondence GL Account")
  @JsonbProperty("reqChargeOURCorespondenceGLAccount")
  public String chrgOurCorresGLAmt;

  @Schema(required = false, example = "", description = "Charge Our Conversion Rate")
  @JsonbProperty("reqChargeOURConversionRate")
  public String chrgOurConversionRate;

  @Schema(required = false, example = "", description = "Charge OUR Desc")
  @JsonbProperty("reqChargeOURDesc")
  public String chrgOurDesc;

  @Schema(required = false, example = "", description = "SHA Charge Debit Accouunt number")
  @JsonbProperty("reqSHAChargeDebitAccountNumber")
  public String chrgDebitAccNumber;

  @Schema(required = false, example = "", description = "SHA Charge GL Account")
  @JsonbProperty("reqSHAChargeGLAccount")
  public String chargeGLAccount;

  @Schema(required = false, example = "", description = "Charge Amount in Charge Account Currency")
  @JsonbProperty("reqSHAChargeAmountInChargeAccountCurrency")
  public String chrgAmtChrgAccCurrency;

  @Schema(required = false, example = "", description = "SHA charge Debit Account Currency")
  @JsonbProperty("reqSHAChargeDebitAccountCurrency")
  public String chrgDebitAccCurrency;

  @Schema(required = false, example = "", description = "Charge SHA Conversion Rate")
  @JsonbProperty("reqChargeSHAConversionRate")
  public String chrgSHAConversionRate;

  @Schema(required = false, example = "", description = "Charge SHA Desc")
  @JsonbProperty("reqChargeSHADesc")
  public String chrgSHADesc;

  @Schema(required = false, example = "", description = "Beneficiary Name")
  @JsonbProperty("reqBeneficiaryName")
  public String benefName;

  @Schema(required = false, example = "", description = "Beneficiary City")
  @JsonbProperty("reqBeneCity")
  public String benefCity;

  @Schema(required = false, example = "", description = "Beneficiary Country")
  @JsonbProperty("reqBeneCountry")
  public String benefCountry;

  @Schema(required = false, example = "", description = "Beneficiary Address 1")
  @JsonbProperty("reqBeneficiaryAddress1")
  public String benefAddress1;

  @Schema(required = false, example = "", description = "Beneficiary Address 2")
  @JsonbProperty("reqBeneficiaryAddress2")
  public String benefAddress2;

  @Schema(required = false, example = "", description = "Beneficiary Address 3")
  @JsonbProperty("reqBeneficiaryAddress3")
  public String benefAddress3;

  @Schema(required = false, example = "", description = "Customer Id")
  @JsonbProperty("reqCIF")
  public String cif;

  @Schema(required = false, example = "", description = "Beneficiary Bank Name")
  @JsonbProperty("reqBeneBankName")
  public String benefBankName;

  @Schema(required = false, example = "", description = "Beneficiary Bank Address 1")
  @JsonbProperty("reqBeneficiaryBankAddress1")
  public String benefBankAddress1;

  @Schema(required = false, example = "", description = "Beneficiary Bank Country")
  @JsonbProperty("reqBeneBankCountry")
  public String benefBankCurrency;

  @Schema(required = true, example = "EQBLKENA", description = "Beneficiary Bank BIC")
  @JsonbProperty("reqBeneficiaryBankBIC")
  public String benefBankBIC;

  @Schema(required = false, example = "", description = "Account BankFormat")
  @JsonbProperty("reqaccountBankFormat")
  public String accountBankFormat;

  @Schema(required = false, example = "", description = "Swift Routing Code")
  @JsonbProperty("reqswiftRoutingCode")
  public String swiftRoutingCode;

  @Schema(required = false, example = "", description = "Receiver Bank BIC")
  @JsonbProperty("reqRecieverBankBIC")
  public String receiverBankBIC;

  @Schema(required = false, example = "", description = "Clearing Code")
  @JsonbProperty("reqClearingCode")
  public String clearingCode;

  @Schema(required = false, example = "", description = "Remittence Info")
  @JsonbProperty("reqRemittanceInfo")
  public String remittenceInfo;

  @Schema(required = false, example = "", description = "Charge Type")
  @JsonbProperty("reqChargeType")
  public String chargeType;

  @Schema(required = false, example = "", description = "Bank to Bank Info")
  @JsonbProperty("reqBanktoBankInfo")
  public String banktobankInfo;

  @Schema(required = false, example = "", description = "Deal Reference")
  @JsonbProperty("reqDealReference")
  public String dealReference;

  @Schema(required = false, example = "", description = "Dealer Name")
  @JsonbProperty("reqDealerName")
  public String dealerName;

  @Schema(required = false, example = "", description = "Deal Rate")
  @JsonbProperty("reqDealRate")
  public String dealRate;

  @Schema(required = false, example = "", description = "Tranfer Amount")
  @JsonbProperty("reqTransferAmt")
  public String transferAmt;

  @Schema(required = false, example = "", description = "Ordering Customer Name")
  @JsonbProperty("reqOrderingCustomerName")
  public String orderingCustomerName;

  @Schema(required = false, example = "", description = "Ordering Customer Account")
  @JsonbProperty("reqOrderingCustomerAccount")
  public String orderingCustomerAccount;

  @Schema(required = false, example = "", description = "Ordering Customer Address 1")
  @JsonbProperty("reqOrderingCustomerAddress1")
  public String orderingCustomerAddress1;

  @Schema(required = false, example = "", description = "Ordering Customer Address 2")
  @JsonbProperty("reqOrderingCustomerAddress2")
  public String orderingCustomerAddress2;

  @Schema(required = false, example = "", description = "Ordering Customer Address 3")
  @JsonbProperty("reqOrderingCustomerAddress3")
  public String orderingCustomerAddress3;

  @Schema(required = false, example = "", description = "Ordering Customer Address 4")
  @JsonbProperty("reqOrderingCustomerAddress4")
  public String orderingCustomerAddress4;

  @Schema(required = false, example = "", description = "Payment Currency")
  @JsonbProperty("reqPayCurrency")
  public String payCurrency;

  @Schema(required = false, example = "", description = "Debit Branch")
  @JsonbProperty("reqDebitBranch")
  public String debitBranch;

  @Schema(required = false, example = "", description = "Account Number")
  @JsonbProperty("reqChannelCode")
  public String channelCode;

  @Schema(required = false, example = "", description = "Beneficiary Address 4")
  @JsonbProperty("reqBeneficiaryAddress4")
  public String benefAddress4;

  @Schema(required = false, example = "", description = "Charge Amount in Local Currency")
  @JsonbProperty("reqChargeAmountInLocalCurrency")
  public String chrgAmtLocalcurrency;

  @Schema(required = false, example = "", description = "Batch Reference Number")
  @JsonbProperty("reqBatchReferenceNo")
  public String batchReferenceNo;

  @Schema(required = false, example = "", description = "Payment Type")
  @JsonbProperty("reqPymntType")
  public String paymentType;

  @Schema(required = false, example = "", description = "First Leg Reference Number")
  @JsonbProperty("reqFirstLegRefNo")
  public String firstLegRefno;

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getTransStatus() {
    return transStatus;
  }

  public void setTransStatus(String transStatus) {
    this.transStatus = transStatus;
  }

  public String getChannelId() {
    return channelId;
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }

  public String getChannelRequestTime() {
    return channelRequestTime;
  }

  public void setChannelRequestTime(String channelRequestTime) {
    this.channelRequestTime = channelRequestTime;
  }

  public String getPostingBranch() {
    return postingBranch;
  }

  public void setPostingBranch(String postingBranch) {
    this.postingBranch = postingBranch;
  }

  public String getTransReference() {
    return transReference;
  }

  public void setTransReference(String transReference) {
    this.transReference = transReference;
  }

  public String getTransCode() {
    return transCode;
  }

  public void setTransCode(String transCode) {
    this.transCode = transCode;
  }

  public String getAccountNo() {
    return accountNo;
  }

  public void setAccountNo(String accountNo) {
    this.accountNo = accountNo;
  }

  public String getDebitAccCurrency() {
    return debitAccCurrency;
  }

  public void setDebitAccCurrency(String debitAccCurrency) {
    this.debitAccCurrency = debitAccCurrency;
  }

  public String getDebitAmt() {
    return debitAmt;
  }

  public void setDebitAmt(String debitAmt) {
    this.debitAmt = debitAmt;
  }

  public String getDebitNarration() {
    return debitNarration;
  }

  public void setDebitNarration(String debitNarration) {
    this.debitNarration = debitNarration;
  }

  public String getForceDebitFlag() {
    return forceDebitFlag;
  }

  public void setForceDebitFlag(String forceDebitFlag) {
    this.forceDebitFlag = forceDebitFlag;
  }

  public String getConversionRate1() {
    return conversionRate1;
  }

  public void setConversionRate1(String conversionRate1) {
    this.conversionRate1 = conversionRate1;
  }

  public String getConversionRate2() {
    return conversionRate2;
  }

  public void setConversionRate2(String conversionRate2) {
    this.conversionRate2 = conversionRate2;
  }

  public String getBenefAccount() {
    return benefAccount;
  }

  public void setBenefAccount(String benefAccount) {
    this.benefAccount = benefAccount;
  }

  public String getCreditAccCurrency() {
    return creditAccCurrency;
  }

  public void setCreditAccCurrency(String creditAccCurrency) {
    this.creditAccCurrency = creditAccCurrency;
  }

  public String getCreditAmt() {
    return creditAmt;
  }

  public void setCreditAmt(String creditAmt) {
    this.creditAmt = creditAmt;
  }

  public String getCreditNarration() {
    return creditNarration;
  }

  public void setCreditNarration(String creditNarration) {
    this.creditNarration = creditNarration;
  }

  public String getForceCreditFlag() {
    return forceCreditFlag;
  }

  public void setForceCreditFlag(String forceCreditFlag) {
    this.forceCreditFlag = forceCreditFlag;
  }

  public String getCardPayment() {
    return cardPayment;
  }

  public void setCardPayment(String cardPayment) {
    this.cardPayment = cardPayment;
  }

  public String getPurposeCode() {
    return purposeCode;
  }

  public void setPurposeCode(String purposeCode) {
    this.purposeCode = purposeCode;
  }

  public String getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(String transactionDate) {
    this.transactionDate = transactionDate;
  }

  public String getValueDate() {
    return valueDate;
  }

  public void setValueDate(String valueDate) {
    this.valueDate = valueDate;
  }

  public String getChargeDebtAccNumber() {
    return chargeDebtAccNumber;
  }

  public void setChargeDebtAccNumber(String chargeDebtAccNumber) {
    this.chargeDebtAccNumber = chargeDebtAccNumber;
  }

  public String getChargeDebtAccCurrency() {
    return chargeDebtAccCurrency;
  }

  public void setChargeDebtAccCurrency(String chargeDebtAccCurrency) {
    this.chargeDebtAccCurrency = chargeDebtAccCurrency;
  }

  public String getBenefChargeAmt() {
    return benefChargeAmt;
  }

  public void setBenefChargeAmt(String benefChargeAmt) {
    this.benefChargeAmt = benefChargeAmt;
  }

  public String getBenChrgGLAccount() {
    return benChrgGLAccount;
  }

  public void setBenChrgGLAccount(String benChrgGLAccount) {
    this.benChrgGLAccount = benChrgGLAccount;
  }

  public String getBenchrgConversionRate() {
    return benchrgConversionRate;
  }

  public void setBenchrgConversionRate(String benchrgConversionRate) {
    this.benchrgConversionRate = benchrgConversionRate;
  }

  public String getBenChargeDesc() {
    return benChargeDesc;
  }

  public void setBenChargeDesc(String benChargeDesc) {
    this.benChargeDesc = benChargeDesc;
  }

  public String getChargeAmtOurChrgAccCurrency() {
    return chargeAmtOurChrgAccCurrency;
  }

  public void setChargeAmtOurChrgAccCurrency(String chargeAmtOurChrgAccCurrency) {
    this.chargeAmtOurChrgAccCurrency = chargeAmtOurChrgAccCurrency;
  }

  public String getChargeGLAccOur() {
    return chargeGLAccOur;
  }

  public void setChargeGLAccOur(String chargeGLAccOur) {
    this.chargeGLAccOur = chargeGLAccOur;
  }

  public String getChrgAmtOurCorresGLAmt() {
    return chrgAmtOurCorresGLAmt;
  }

  public void setChrgAmtOurCorresGLAmt(String chrgAmtOurCorresGLAmt) {
    this.chrgAmtOurCorresGLAmt = chrgAmtOurCorresGLAmt;
  }

  public String getChrgOurCorresGLAmt() {
    return chrgOurCorresGLAmt;
  }

  public void setChrgOurCorresGLAmt(String chrgOurCorresGLAmt) {
    this.chrgOurCorresGLAmt = chrgOurCorresGLAmt;
  }

  public String getChrgOurConversionRate() {
    return chrgOurConversionRate;
  }

  public void setChrgOurConversionRate(String chrgOurConversionRate) {
    this.chrgOurConversionRate = chrgOurConversionRate;
  }

  public String getChrgOurDesc() {
    return chrgOurDesc;
  }

  public void setChrgOurDesc(String chrgOurDesc) {
    this.chrgOurDesc = chrgOurDesc;
  }

  public String getChrgDebitAccNumber() {
    return chrgDebitAccNumber;
  }

  public void setChrgDebitAccNumber(String chrgDebitAccNumber) {
    this.chrgDebitAccNumber = chrgDebitAccNumber;
  }

  public String getChargeGLAccount() {
    return chargeGLAccount;
  }

  public void setChargeGLAccount(String chargeGLAccount) {
    this.chargeGLAccount = chargeGLAccount;
  }

  public String getChrgAmtChrgAccCurrency() {
    return chrgAmtChrgAccCurrency;
  }

  public void setChrgAmtChrgAccCurrency(String chrgAmtChrgAccCurrency) {
    this.chrgAmtChrgAccCurrency = chrgAmtChrgAccCurrency;
  }

  public String getChrgDebitAccCurrency() {
    return chrgDebitAccCurrency;
  }

  public void setChrgDebitAccCurrency(String chrgDebitAccCurrency) {
    this.chrgDebitAccCurrency = chrgDebitAccCurrency;
  }

  public String getChrgSHAConversionRate() {
    return chrgSHAConversionRate;
  }

  public void setChrgSHAConversionRate(String chrgSHAConversionRate) {
    this.chrgSHAConversionRate = chrgSHAConversionRate;
  }

  public String getChrgSHADesc() {
    return chrgSHADesc;
  }

  public void setChrgSHADesc(String chrgSHADesc) {
    this.chrgSHADesc = chrgSHADesc;
  }

  public String getBenefName() {
    return benefName;
  }

  public void setBenefName(String benefName) {
    this.benefName = benefName;
  }

  public String getBenefCity() {
    return benefCity;
  }

  public void setBenefCity(String benefCity) {
    this.benefCity = benefCity;
  }

  public String getBenefCountry() {
    return benefCountry;
  }

  public void setBenefCountry(String benefCountry) {
    this.benefCountry = benefCountry;
  }

  public String getBenefAddress1() {
    return benefAddress1;
  }

  public void setBenefAddress1(String benefAddress1) {
    this.benefAddress1 = benefAddress1;
  }

  public String getBenefAddress2() {
    return benefAddress2;
  }

  public void setBenefAddress2(String benefAddress2) {
    this.benefAddress2 = benefAddress2;
  }

  public String getBenefAddress3() {
    return benefAddress3;
  }

  public void setBenefAddress3(String benefAddress3) {
    this.benefAddress3 = benefAddress3;
  }

  public String getCif() {
    return cif;
  }

  public void setCif(String cif) {
    this.cif = cif;
  }

  public String getBenefBankName() {
    return benefBankName;
  }

  public void setBenefBankName(String benefBankName) {
    this.benefBankName = benefBankName;
  }

  public String getBenefBankAddress1() {
    return benefBankAddress1;
  }

  public void setBenefBankAddress1(String benefBankAddress1) {
    this.benefBankAddress1 = benefBankAddress1;
  }

  public String getBenefBankCurrency() {
    return benefBankCurrency;
  }

  public void setBenefBankCurrency(String benefBankCurrency) {
    this.benefBankCurrency = benefBankCurrency;
  }

  public String getBenefBankBIC() {
    return benefBankBIC;
  }

  public void setBenefBankBIC(String benefBankBIC) {
    this.benefBankBIC = benefBankBIC;
  }

  public String getAccountBankFormat() {
    return accountBankFormat;
  }

  public void setAccountBankFormat(String accountBankFormat) {
    this.accountBankFormat = accountBankFormat;
  }

  public String getSwiftRoutingCode() {
    return swiftRoutingCode;
  }

  public void setSwiftRoutingCode(String swiftRoutingCode) {
    this.swiftRoutingCode = swiftRoutingCode;
  }

  public String getReceiverBankBIC() {
    return receiverBankBIC;
  }

  public void setReceiverBankBIC(String receiverBankBIC) {
    this.receiverBankBIC = receiverBankBIC;
  }

  public String getClearingCode() {
    return clearingCode;
  }

  public void setClearingCode(String clearingCode) {
    this.clearingCode = clearingCode;
  }

  public String getRemittenceInfo() {
    return remittenceInfo;
  }

  public void setRemittenceInfo(String remittenceInfo) {
    this.remittenceInfo = remittenceInfo;
  }

  public String getChargeType() {
    return chargeType;
  }

  public void setChargeType(String chargeType) {
    this.chargeType = chargeType;
  }

  public String getBanktobankInfo() {
    return banktobankInfo;
  }

  public void setBanktobankInfo(String banktobankInfo) {
    this.banktobankInfo = banktobankInfo;
  }

  public String getDealReference() {
    return dealReference;
  }

  public void setDealReference(String dealReference) {
    this.dealReference = dealReference;
  }

  public String getDealerName() {
    return dealerName;
  }

  public void setDealerName(String dealerName) {
    this.dealerName = dealerName;
  }

  public String getDealRate() {
    return dealRate;
  }

  public void setDealRate(String dealRate) {
    this.dealRate = dealRate;
  }

  public String getTransferAmt() {
    return transferAmt;
  }

  public void setTransferAmt(String transferAmt) {
    this.transferAmt = transferAmt;
  }

  public String getOrderingCustomerName() {
    return orderingCustomerName;
  }

  public void setOrderingCustomerName(String orderingCustomerName) {
    this.orderingCustomerName = orderingCustomerName;
  }

  public String getOrderingCustomerAccount() {
    return orderingCustomerAccount;
  }

  public void setOrderingCustomerAccount(String orderingCustomerAccount) {
    this.orderingCustomerAccount = orderingCustomerAccount;
  }

  public String getOrderingCustomerAddress1() {
    return orderingCustomerAddress1;
  }

  public void setOrderingCustomerAddress1(String orderingCustomerAddress1) {
    this.orderingCustomerAddress1 = orderingCustomerAddress1;
  }

  public String getOrderingCustomerAddress2() {
    return orderingCustomerAddress2;
  }

  public void setOrderingCustomerAddress2(String orderingCustomerAddress2) {
    this.orderingCustomerAddress2 = orderingCustomerAddress2;
  }

  public String getOrderingCustomerAddress3() {
    return orderingCustomerAddress3;
  }

  public void setOrderingCustomerAddress3(String orderingCustomerAddress3) {
    this.orderingCustomerAddress3 = orderingCustomerAddress3;
  }

  public String getOrderingCustomerAddress4() {
    return orderingCustomerAddress4;
  }

  public void setOrderingCustomerAddress4(String orderingCustomerAddress4) {
    this.orderingCustomerAddress4 = orderingCustomerAddress4;
  }

  public String getPayCurrency() {
    return payCurrency;
  }

  public void setPayCurrency(String payCurrency) {
    this.payCurrency = payCurrency;
  }

  public String getDebitBranch() {
    return debitBranch;
  }

  public void setDebitBranch(String debitBranch) {
    this.debitBranch = debitBranch;
  }

  public String getChannelCode() {
    return channelCode;
  }

  public void setChannelCode(String channelCode) {
    this.channelCode = channelCode;
  }

  public String getBenefAddress4() {
    return benefAddress4;
  }

  public void setBenefAddress4(String benefAddress4) {
    this.benefAddress4 = benefAddress4;
  }

  public String getChrgAmtLocalcurrency() {
    return chrgAmtLocalcurrency;
  }

  public void setChrgAmtLocalcurrency(String chrgAmtLocalcurrency) {
    this.chrgAmtLocalcurrency = chrgAmtLocalcurrency;
  }

  public String getBatchReferenceNo() {
    return batchReferenceNo;
  }

  public void setBatchReferenceNo(String batchReferenceNo) {
    this.batchReferenceNo = batchReferenceNo;
  }

  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  public String getFirstLegRefno() {
    return firstLegRefno;
  }

  public void setFirstLegRefno(String firstLegRefno) {
    this.firstLegRefno = firstLegRefno;
  }

  public String getVipMarker() {
    return vipMarker;
  }

  public void setVipMarker(String vipMarker) {
    this.vipMarker = vipMarker;
  }

  public String getWaiveCharge() {
    return waiveCharge;
  }

  public void setWaiveCharge(String waiveCharge) {
    this.waiveCharge = waiveCharge;
  }
}
