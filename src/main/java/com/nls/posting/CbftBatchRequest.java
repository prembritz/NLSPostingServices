package com.nls.posting;

import java.util.List;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({"reqFileRefNo", "reqBatchRefNo", "reqNoOfTxns", "reqUnitId", "batchData"})
@Schema(
    name = "CBFTBatchRequest",
    description = "request object with HdrTranId, hdrStatus, reqUnitID, reqChannelName...etc")
public class CbftBatchRequest {

  @Schema(required = true, example = "TBC1502211514001", description = "Req File Reference No")
  @JsonbProperty("reqFileRefNo")
  public String reqFileRefNo;

  @Schema(required = true, example = "TBC1502211512002", description = "Req Batch Ref No")
  @JsonbProperty("reqBatchRefNo")
  public String reqBatchRefNo;

  @Schema(required = true, example = "2", description = "Req No of Transactions")
  @JsonbProperty("reqNoOfTxns")
  public String reqNoOfTxns;

  @Schema(required = true, example = "KE0010001", description = "Req Unit Id")
  @JsonbProperty("reqUnitId")
  public String reqUnitId;

  public static class BatchData {

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

    @Schema(required = true, example = "CROSSBORDERFUNDTRAN", description = "Hdr TranId")
    @JsonbProperty("hdrTranId")
    public String hdrTranId;

    @Schema(required = false, example = "", description = "Hdr Status")
    @JsonbProperty("hdrStatus")
    public String hdrStatus;

    @Schema(required = true, example = "CMS", description = "req ChnlId")
    @JsonbProperty("reqChnlId")
    public String reqChnlId;

    @Schema(
        required = true,
        example = "KE0010001",
        description = "req UnitID",
        enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
    @JsonbProperty("reqUnitID")
    public String reqUnitID;

    @Schema(required = true, example = "CBX", description = "req ChannelName")
    @JsonbProperty("reqChannelName")
    public String reqChannelName;

    @Schema(required = false, example = "08052019", description = "req Channel req Time")
    @JsonbProperty("reqChannelReqTime")
    public String reqChannelReqTime;

    @Schema(required = false, example = "000", description = "Req PostingBranch")
    @JsonbProperty("reqPostingBranch")
    public String reqPostingBranch;

    @Schema(
        required = true,
        example = "5452019050801742",
        description = "Req Transaction ReferenceNo")
    @JsonbProperty("reqTransactionReferenceNo")
    public String reqTransactionReferenceNo;

    @Schema(required = false, example = "FC98", description = "req Transaction Code")
    @JsonbProperty("reqTransactionCode")
    public String reqTransactionCode;

    @Schema(required = false, example = "", description = "req DebitBranch")
    @JsonbProperty("reqDebitBranch")
    public String reqDebitBranch;

    @Schema(required = true, example = "1711460011", description = "req AccountNo")
    @JsonbProperty("reqAccountNo")
    public String reqAccountNo;

    @Schema(required = true, example = "KES", description = "req DebitAc Currency")
    @JsonbProperty("reqDebitAcCurrency")
    public String reqDebitAcCurrency;

    @Schema(required = true, example = "20", description = "req DebitAmount")
    @JsonbProperty("reqDebitAmount")
    public String reqDebitAmount;

    @Schema(required = false, example = "NA", description = "req DrNarration")
    @JsonbProperty("reqDrNarration")
    public String reqDrNarration;

    @Schema(required = false, example = "N", description = "req Force DebitFlag")
    @JsonbProperty("reqForceDebitFlag")
    public String reqForceDebitFlag;

    @Schema(required = false, example = "12.0", description = "req Conversion Rate1")
    @JsonbProperty("reqConversionRate1")
    public String reqConversionRate1;

    @Schema(required = false, example = "", description = "req Conversion Rate2")
    @JsonbProperty("reqConversionRate2")
    public String reqConversionRate2;

    @Schema(required = true, example = "7519350046", description = "req Beneficiary Account")
    @JsonbProperty("reqBeneficiaryAccount")
    public String reqBeneficiaryAccount;

    @Schema(required = false, example = "USD", description = "req CreditAcCurrency")
    @JsonbProperty("reqCreditAcCurrency")
    public String reqCreditAcCurrency;

    @Schema(required = false, example = "100", description = "req CreditAmount")
    @JsonbProperty("reqCreditAmount")
    public String reqCreditAmount;

    @Schema(required = false, example = "NA", description = "req CrNarration")
    @JsonbProperty("reqCrNarration")
    public String reqCrNarration;

    @Schema(required = false, example = "N", description = "req ForceCreditFlag")
    @JsonbProperty("reqForceCreditFlag")
    public String reqForceCreditFlag;

    @Schema(required = false, example = "59", description = "req Purposecode")
    @JsonbProperty("reqPurposecode")
    public String reqPurposecode;

    @Schema(required = false, example = "08082019", description = "req TransactionDate")
    @JsonbProperty("reqTransactionDate")
    public String reqTransactionDate;

    @Schema(required = false, example = "", description = "req ValueDate")
    @JsonbProperty("reqValueDate")
    public String reqValueDate;

    @Schema(required = false, example = "", description = "req ChargeDebit AccountNumber")
    @JsonbProperty("reqChargeDebitAccountNumber")
    public String reqChargeDebitAccountNumber;

    @Schema(required = false, example = "", description = "req ChargeDebit Account Currency")
    @JsonbProperty("reqChargeDebitAccountCurrency")
    public String reqChargeDebitAccountCurrency;

    @Schema(
        required = false,
        example = "",
        description = "req BENCharge Amount InCharge Account Currency")
    @JsonbProperty("reqBENChargeAmountInChargeAccountCurrency")
    public String reqBENChargeAmountInChargeAccountCurrency;

    @Schema(required = false, example = "", description = "req BENCharge GLAccount")
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

    @Schema(required = false, example = "USD", description = "req PayCurrency")
    @JsonbProperty("reqPayCurrency")
    public String reqPayCurrency;

    @Schema(required = false, example = "", description = "req BeneCity")
    @JsonbProperty("reqBeneCity")
    public String reqBeneCity;

    @Schema(required = false, example = "KE", description = "req BeneCountry")
    @JsonbProperty("reqBeneCountry")
    public String reqBeneCountry;

    @Schema(required = false, example = "NAIROBI", description = "req Beneficiary Address1")
    @JsonbProperty("reqBeneficiaryAddress1")
    public String reqBeneficiaryAddress1;

    @Schema(required = false, example = "KEYA", description = "req Beneficiary Address2")
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

    @Schema(required = false, example = "", description = "req Sender CorresBankBIC")
    @JsonbProperty("reqSenderCorresBankBIC")
    public String reqSenderCorresBankBIC;

    @Schema(required = false, example = "", description = "req Sender CorresBankAccNo")
    @JsonbProperty("reqSenderCorresBankAccNo")
    public String reqSenderCorresBankAccNo;

    @Schema(required = false, example = "", description = "req Reciever CorresBankBIC")
    @JsonbProperty("reqRecieverCorresBankBIC")
    public String reqRecieverCorresBankBIC;

    @Schema(required = false, example = "", description = "req Intermediary SWIFTCountryCode")
    @JsonbProperty("reqIntermediarySWIFTCountryCode")
    public String reqIntermediarySWIFTCountryCode;

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

    @Schema(required = false, example = "", description = "req Beneficiary BankAddress2")
    @JsonbProperty("reqBeneficiaryBankAddress2")
    public String reqBeneficiaryBankAddress2;

    @Schema(required = false, example = "", description = "req BatchReferenceNo")
    @JsonbProperty("reqBatchReferenceNo")
    public String reqBatchReferenceNo;

    @Schema(required = false, example = "", description = "req Intermediary BankAddress1")
    @JsonbProperty("reqIntermediaryBankAddress1")
    public String reqIntermediaryBankAddress1;

    @Schema(required = false, example = "", description = "req Intermediary BankAddress2")
    @JsonbProperty("reqIntermediaryBankAddress2")
    public String reqIntermediaryBankAddress2;

    @Schema(required = false, example = "", description = "req IntermediaryBankAddress3")
    @JsonbProperty("reqIntermediaryBankAddress3")
    public String reqIntermediaryBankAddress3;

    @Schema(required = false, example = "", description = "req Intermediary BankAddress4")
    @JsonbProperty("reqIntermediaryBankAddress4")
    public String reqIntermediaryBankAddress4;

    @Schema(required = false, example = "", description = "req IntermediaryBankBIC")
    @JsonbProperty("reqIntermediaryBankBIC")
    public String reqIntermediaryBankBIC;

    @Schema(required = false, example = "DIRECT DEBIT", description = "System DateTime")
    @JsonbProperty("reqpurposecodedesc")
    public String reqpurposecodedesc;

    @Schema(required = false, example = "", description = "req First LegRefNo")
    @JsonbProperty("reqFirstLegRefNo")
    public String reqFirstLegRefNo;
  }

  @JsonbProperty("CBFTBATCHDATA")
  public List<BatchData> batchData;

  /*
   * @JsonbProperty("CBFTBATCHHEADER") public Map<String, String> batchHeader;
   */

}
