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
  "reqTransactionReferenceNo",
  "reqTransactionCode",
  "reqAccountNo",
  "reqDebitAcCurrency",
  "reqDebitAmount",
  "reqDrNarration",
  "reqConversionRate1",
  "reqConversionRate2",
  "reqBeneficiaryAccount",
  "reqCreditAcCurrency",
  "reqCreditAmount",
  "reqCrNarration",
  "reqPurposecode",
  "reqTransactionDate",
  "reqValueDate",
  "reqCIF",
  "vipMarker",
  "waiveCharge"
})
@Schema(
    name = "LoanRepaymentRequest",
    description =
        "request object with HdrTranId, hdrStatus, reqChnlId, reqUnitID, reqChannelName...etc")
public class LoanRepaymentRequest {

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

  @Schema(required = true, example = "TFA8090900999", description = "Hdr TranId")
  @JsonbProperty("hdrTranId")
  public String hdrTranId;

  @Schema(required = true, example = "SUCCESS", description = "Hdr Status")
  @JsonbProperty("hdrStatus")
  public String hdrStatus;

  @Schema(required = true, example = "AFDFD", description = "Req ChnlId")
  @JsonbProperty("reqChnlId")
  public String reqChnlId;

  @Schema(
      required = true,
      example = "KE0010001",
      description = "req UnitID",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("reqUnitID")
  public String reqUnitID;

  @Schema(required = true, example = "ABC", description = "Req ChannelName")
  @JsonbProperty("reqChannelName")
  public String reqChannelName;

  @Schema(required = false, example = "", description = "Req Channel Req Time")
  @JsonbProperty("reqChannelReqTime")
  public String reqChannelReqTime;

  @Schema(required = true, example = "TFA8090900999", description = "Req Transaction ReferenceNo")
  @JsonbProperty("reqTransactionReferenceNo")
  public String reqTransactionReferenceNo;

  @Schema(required = false, example = "", description = "Req Transaction Code")
  @JsonbProperty("reqTransactionCode")
  public String reqTransactionCode;

  @Schema(required = true, example = "1711460011", description = "Req AccountNo")
  @JsonbProperty("reqAccountNo")
  public String reqAccountNo;

  @Schema(required = true, example = "KES", description = "Req DebitAc Currency")
  @JsonbProperty("reqDebitAcCurrency")
  public String reqDebitAcCurrency;

  @Schema(required = true, example = "20", description = "Req DebitAmount")
  @JsonbProperty("reqDebitAmount")
  public String reqDebitAmount;

  @Schema(required = false, example = "test", description = "Req DrNarration")
  @JsonbProperty("reqDrNarration")
  public String reqDrNarration;

  @Schema(required = false, example = "", description = "Req Conversion Rate1")
  @JsonbProperty("reqConversionRate1")
  public String reqConversionRate1;

  @Schema(required = false, example = "", description = "Req Conversion Rate2")
  @JsonbProperty("reqConversionRate2")
  public String reqConversionRate2;

  @Schema(required = true, example = "7519350046", description = "Req Beneficiary Account")
  @JsonbProperty("reqBeneficiaryAccount")
  public String reqBeneficiaryAccount;

  @Schema(required = false, example = "", description = "Req CreditAcCurrency")
  @JsonbProperty("reqCreditAcCurrency")
  public String reqCreditAcCurrency;

  @Schema(required = false, example = "", description = "Req CreditAmount")
  @JsonbProperty("reqCreditAmount")
  public String reqCreditAmount;

  @Schema(required = false, example = "", description = "Req CrNarration")
  @JsonbProperty("reqCrNarration")
  public String reqCrNarration;

  @Schema(required = false, example = "", description = "Req Purposecode")
  @JsonbProperty("reqPurposecode")
  public String reqPurposecode;

  @Schema(required = false, example = "", description = "Req TransactionDate")
  @JsonbProperty("reqTransactionDate")
  public String reqTransactionDate;

  @Schema(required = false, example = "", description = "Req ValueDate")
  @JsonbProperty("reqValueDate")
  public String reqValueDate;

  @Schema(required = false, example = "", description = "Req CIF")
  @JsonbProperty("reqCIF")
  public String reqCIF;

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

  public String getHdrTranId() {
    return hdrTranId;
  }

  public void setHdrTranId(String hdrTranId) {
    this.hdrTranId = hdrTranId;
  }

  public String getHdrStatus() {
    return hdrStatus;
  }

  public void setHdrStatus(String hdrStatus) {
    this.hdrStatus = hdrStatus;
  }

  public String getReqChnlId() {
    return reqChnlId;
  }

  public void setReqChnlId(String reqChnlId) {
    this.reqChnlId = reqChnlId;
  }

  public String getReqUnitID() {
    return reqUnitID;
  }

  public void setReqUnitID(String reqUnitID) {
    this.reqUnitID = reqUnitID;
  }

  public String getReqChannelName() {
    return reqChannelName;
  }

  public void setReqChannelName(String reqChannelName) {
    this.reqChannelName = reqChannelName;
  }

  public String getReqChannelReqTime() {
    return reqChannelReqTime;
  }

  public void setReqChannelReqTime(String reqChannelReqTime) {
    this.reqChannelReqTime = reqChannelReqTime;
  }

  public String getReqTransactionReferenceNo() {
    return reqTransactionReferenceNo;
  }

  public void setReqTransactionReferenceNo(String reqTransactionReferenceNo) {
    this.reqTransactionReferenceNo = reqTransactionReferenceNo;
  }

  public String getReqTransactionCode() {
    return reqTransactionCode;
  }

  public void setReqTransactionCode(String reqTransactionCode) {
    this.reqTransactionCode = reqTransactionCode;
  }

  public String getReqAccountNo() {
    return reqAccountNo;
  }

  public void setReqAccountNo(String reqAccountNo) {
    this.reqAccountNo = reqAccountNo;
  }

  public String getReqDebitAcCurrency() {
    return reqDebitAcCurrency;
  }

  public void setReqDebitAcCurrency(String reqDebitAcCurrency) {
    this.reqDebitAcCurrency = reqDebitAcCurrency;
  }

  public String getReqDebitAmount() {
    return reqDebitAmount;
  }

  public void setReqDebitAmount(String reqDebitAmount) {
    this.reqDebitAmount = reqDebitAmount;
  }

  public String getReqDrNarration() {
    return reqDrNarration;
  }

  public void setReqDrNarration(String reqDrNarration) {
    this.reqDrNarration = reqDrNarration;
  }

  public String getReqConversionRate1() {
    return reqConversionRate1;
  }

  public void setReqConversionRate1(String reqConversionRate1) {
    this.reqConversionRate1 = reqConversionRate1;
  }

  public String getReqConversionRate2() {
    return reqConversionRate2;
  }

  public void setReqConversionRate2(String reqConversionRate2) {
    this.reqConversionRate2 = reqConversionRate2;
  }

  public String getReqBeneficiaryAccount() {
    return reqBeneficiaryAccount;
  }

  public void setReqBeneficiaryAccount(String reqBeneficiaryAccount) {
    this.reqBeneficiaryAccount = reqBeneficiaryAccount;
  }

  public String getReqCreditAcCurrency() {
    return reqCreditAcCurrency;
  }

  public void setReqCreditAcCurrency(String reqCreditAcCurrency) {
    this.reqCreditAcCurrency = reqCreditAcCurrency;
  }

  public String getReqCreditAmount() {
    return reqCreditAmount;
  }

  public void setReqCreditAmount(String reqCreditAmount) {
    this.reqCreditAmount = reqCreditAmount;
  }

  public String getReqCrNarration() {
    return reqCrNarration;
  }

  public void setReqCrNarration(String reqCrNarration) {
    this.reqCrNarration = reqCrNarration;
  }

  public String getReqPurposecode() {
    return reqPurposecode;
  }

  public void setReqPurposecode(String reqPurposecode) {
    this.reqPurposecode = reqPurposecode;
  }

  public String getReqTransactionDate() {
    return reqTransactionDate;
  }

  public void setReqTransactionDate(String reqTransactionDate) {
    this.reqTransactionDate = reqTransactionDate;
  }

  public String getReqValueDate() {
    return reqValueDate;
  }

  public void setReqValueDate(String reqValueDate) {
    this.reqValueDate = reqValueDate;
  }

  public String getReqCIF() {
    return reqCIF;
  }

  public void setReqCIF(String reqCIF) {
    this.reqCIF = reqCIF;
  }
}
