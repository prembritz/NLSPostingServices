package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "PesaLinkCoreRequest", description = "Request object with sendername, bankid...etc")
@JsonbPropertyOrder({
  "senderName",
  "bankId",
  "amount",
  "sourceaccount",
  "purpose",
  "beneficiaryName",
  "customerId",
  "senderMobile",
  "DestinationAccount",
  "currency",
  "transactiontype",
  "uniqueId"
})
public class PesaLinkCoreRequest {

  @Schema(required = true, example = "SYLVIAH SELEYIAN TIPAPE", description = "Sender name")
  @JsonbProperty("senderName")
  public String senderName;

  @Schema(required = true, example = "0001", description = "Bankid")
  @JsonbProperty("bankId")
  public String bankId;

  @Schema(required = true, example = "1000", description = "Amount")
  @JsonbProperty("amount")
  public String amount;

  @Schema(required = true, example = "8463230014", description = "Sourceaccount")
  @JsonbProperty("sourceaccount")
  public String sourceaccount;

  @Schema(required = true, example = "CPE", description = "Purpose")
  @JsonbProperty("purpose")
  public String purpose;

  @Schema(required = true, example = "beneficiaryName", description = "Beneficiary name")
  @JsonbProperty("beneficiaryName")
  public String beneficiaryName;

  @Schema(required = true, example = "846323", description = "Customer id")
  @JsonbProperty("customerId")
  public String customerId;

  @Schema(required = true, example = "254711792222", description = "Sender mobile")
  @JsonbProperty("senderMobile")
  public String senderMobile;

  @Schema(required = true, example = "1289254850", description = "Destination account")
  @JsonbProperty("DestinationAccount")
  public String DestinationAccount;

  @Schema(required = true, example = "KES", description = "Currency")
  @JsonbProperty("currency")
  public String currency;

  @Schema(required = true, example = "sendtobank", description = "Transaction type")
  @JsonbProperty("transactiontype")
  public String transactiontype;

  @Schema(required = true, example = "FTC220727SNLY", description = "Unique id")
  @JsonbProperty("uniqueId")
  public String uniqueId;

  public String getSenderName() {
    return senderName;
  }

  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }

  public String getBankId() {
    return bankId;
  }

  public void setBankId(String bankId) {
    this.bankId = bankId;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getSourceaccount() {
    return sourceaccount;
  }

  public void setSourceaccount(String sourceaccount) {
    this.sourceaccount = sourceaccount;
  }

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public String getBeneficiaryName() {
    return beneficiaryName;
  }

  public void setBeneficiaryName(String beneficiaryName) {
    this.beneficiaryName = beneficiaryName;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getSenderMobile() {
    return senderMobile;
  }

  public void setSenderMobile(String senderMobile) {
    this.senderMobile = senderMobile;
  }

  public String getDestinationAccount() {
    return DestinationAccount;
  }

  public void setDestinationAccount(String destinationAccount) {
    DestinationAccount = destinationAccount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getTransactiontype() {
    return transactiontype;
  }

  public void setTransactiontype(String transactiontype) {
    this.transactiontype = transactiontype;
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public PesaLinkCoreRequest(
      String senderName,
      String bankId,
      String amount,
      String sourceaccount,
      String purpose,
      String beneficiaryName,
      String customerId,
      String senderMobile,
      String destinationAccount,
      String currency,
      String transactiontype,
      String uniqueId) {
    super();
    this.senderName = senderName;
    this.bankId = bankId;
    this.amount = amount;
    this.sourceaccount = sourceaccount;
    this.purpose = purpose;
    this.beneficiaryName = beneficiaryName;
    this.customerId = customerId;
    this.senderMobile = senderMobile;
    DestinationAccount = destinationAccount;
    this.currency = currency;
    this.transactiontype = transactiontype;
    this.uniqueId = uniqueId;
  }
}
