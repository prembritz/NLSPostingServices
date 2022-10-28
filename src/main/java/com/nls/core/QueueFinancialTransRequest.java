package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
    name = "QueueFinancialTransRequest",
    description = "Request object with Unique Reference & Service Name")
@JsonbPropertyOrder({
  "uniqReference",
  "serviceName",
  "orgUnit",
  "debitAccNo",
  "transAmount",
  "transCurrency",
  "lockFunds",
  "transChannel",
  "t24FormatReqMsg"
})
public class QueueFinancialTransRequest {

  @Schema(required = true, example = "20220609AAC", description = "Unique Reference")
  @JsonbProperty("UniqueReference")
  private String uniqReference;

  @Schema(required = true, example = "InternalTransfer", description = "Service Name")
  @JsonbProperty("ServiceName")
  private String serviceName;

  @Schema(required = true, example = "KENYA", description = "Organization Unit")
  @JsonbProperty("OrganisationUnit")
  private String orgUnit;

  @Schema(required = true, example = "7548710058", description = "Debit Account")
  @JsonbProperty("DebitAcctNo")
  private String debitAccNo;

  // @JsonbDateFormat("yyyy-MM-dd")
  @Schema(required = true, example = "500", description = "Transaction Amount")
  @JsonbProperty("TransactionAmount")
  private String transAmount;

  // @JsonbDateFormat("yyyy-MM-dd")
  @Schema(required = true, example = "KES", description = "Transaction Currency")
  @JsonbProperty("TransactionCurrency")
  private String transCurrency;

  @Schema(required = true, example = "true", description = "Lock Funds")
  @JsonbProperty("LockFunds")
  private String lockFunds;

  // @JsonbDateFormat("yyyy-MM-dd")
  @Schema(required = true, example = "CIB", description = "Transaction Channel")
  @JsonbProperty("TransactionChannel")
  private String transChannel;

  // @JsonbDateFormat("yyyy-MM-dd")
  @Schema(required = true, example = "", description = "T24 Formatted Request Message")
  @JsonbProperty("T24FormattedRequestMessage")
  private String t24FormatReqMsg;

  public QueueFinancialTransRequest(
      String uniqReference,
      String serviceName,
      String orgUnit,
      String debitAccNo,
      String transAmount,
      String transCurrency,
      String lockFunds,
      String transChannel,
      String t24FormatReqMsg) {
    super();
    this.uniqReference = uniqReference;
    this.serviceName = serviceName;
    this.orgUnit = orgUnit;
    this.debitAccNo = debitAccNo;
    this.transAmount = transAmount;
    this.transCurrency = transCurrency;
    this.lockFunds = lockFunds;
    this.transChannel = transChannel;
    this.t24FormatReqMsg = t24FormatReqMsg;
  }

  public String getUniqReference() {
    return uniqReference;
  }

  public void setUniqReference(String uniqReference) {
    this.uniqReference = uniqReference;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getOrgUnit() {
    return orgUnit;
  }

  public void setOrgUnit(String orgUnit) {
    this.orgUnit = orgUnit;
  }

  public String getDebitAccNo() {
    return debitAccNo;
  }

  public void setDebitAccNo(String debitAccNo) {
    this.debitAccNo = debitAccNo;
  }

  public String getTransAmount() {
    return transAmount;
  }

  public void setTransAmount(String transAmount) {
    this.transAmount = transAmount;
  }

  public String getTransCurrency() {
    return transCurrency;
  }

  public void setTransCurrency(String transCurrency) {
    this.transCurrency = transCurrency;
  }

  public String getLockFunds() {
    return lockFunds;
  }

  public void setLockFunds(String lockFunds) {
    this.lockFunds = lockFunds;
  }

  public String getTransChannel() {
    return transChannel;
  }

  public void setTransChannel(String transChannel) {
    this.transChannel = transChannel;
  }

  public String getT24FormatReqMsg() {
    return t24FormatReqMsg;
  }

  public void setT24FormatReqMsg(String t24FormatReqMsg) {
    this.t24FormatReqMsg = t24FormatReqMsg;
  }
}
