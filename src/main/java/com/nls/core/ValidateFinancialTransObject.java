package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({
  "status",
  "debitCustomerId",
  "creditCustomerId",
  "debitAccTitle",
  "debitAccMinBalance",
  "debitAccCurrency",
  "chargeAmt",
  "errorDetail",
  "balBeforeCreditAccount",
  "balAfterDebitAccount",
  "debitAccLockedAmt",
  "creditAccCurrency",
  "debitAccMobileNumber",
  "debitAccountDAO",
  "debitAccountLimit",
  "balBeforeDebitAccount"
})
public class ValidateFinancialTransObject {

  @JsonbProperty("Status")
  public String status;

  @JsonbProperty("DebitAcctCustomerId")
  public String debitCustomerId;

  @JsonbProperty("CreditAcctCustomerId")
  public String creditCustomerId;

  @JsonbProperty("DebitAcctTitle")
  public String debitAccTitle;

  @JsonbProperty("DebitAcctMinimumBalance")
  public String debitAccMinBalance;

  @JsonbProperty("DebitAcctCurrency")
  public String debitAccCurrency;

  @JsonbProperty("ChargeAmount")
  public String chargeAmt;

  @JsonbProperty("ErrorDetail")
  public String errorDetail;

  @JsonbProperty("BalanceBeforeCrAcct")
  public String balBeforeCreditAccount;

  @JsonbProperty("BalanceAfterDrAcct")
  public String balAfterDebitAccount;

  @JsonbProperty("DebitAcctLockedAmount")
  public String debitAccLockedAmt;

  @JsonbProperty("CreditAcctCurrency")
  public String creditAccCurrency;

  @JsonbProperty("DebitAcctMobileNo")
  public String debitAccMobileNumber;

  @JsonbProperty("DebitAcctDao")
  public String debitAccountDAO;

  @JsonbProperty("DebitAcctLimit")
  public String debitAccountLimit;

  @JsonbProperty("BalanceBeforeDrAcct")
  public String balBeforeDebitAccount;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDebitCustomerId() {
    return debitCustomerId;
  }

  public void setDebitCustomerId(String debitCustomerId) {
    this.debitCustomerId = debitCustomerId;
  }

  public String getCreditCustomerId() {
    return creditCustomerId;
  }

  public void setCreditCustomerId(String creditCustomerId) {
    this.creditCustomerId = creditCustomerId;
  }

  public String getDebitAccTitle() {
    return debitAccTitle;
  }

  public void setDebitAccTitle(String debitAccTitle) {
    this.debitAccTitle = debitAccTitle;
  }

  public String getDebitAccMinBalance() {
    return debitAccMinBalance;
  }

  public void setDebitAccMinBalance(String debitAccMinBalance) {
    this.debitAccMinBalance = debitAccMinBalance;
  }

  public String getDebitAccCurrency() {
    return debitAccCurrency;
  }

  public void setDebitAccCurrency(String debitAccCurrency) {
    this.debitAccCurrency = debitAccCurrency;
  }

  public String getChargeAmt() {
    return chargeAmt;
  }

  public void setChargeAmt(String chargeAmt) {
    this.chargeAmt = chargeAmt;
  }

  public String getErrorDetail() {
    return errorDetail;
  }

  public void setErrorDetail(String errorDetail) {
    this.errorDetail = errorDetail;
  }

  public String getBalBeforeCreditAccount() {
    return balBeforeCreditAccount;
  }

  public void setBalBeforeCreditAccount(String balBeforeCreditAccount) {
    this.balBeforeCreditAccount = balBeforeCreditAccount;
  }

  public String getBalAfterDebitAccount() {
    return balAfterDebitAccount;
  }

  public void setBalAfterDebitAccount(String balAfterDebitAccount) {
    this.balAfterDebitAccount = balAfterDebitAccount;
  }

  public String getDebitAccLockedAmt() {
    return debitAccLockedAmt;
  }

  public void setDebitAccLockedAmt(String debitAccLockedAmt) {
    this.debitAccLockedAmt = debitAccLockedAmt;
  }

  public String getCreditAccCurrency() {
    return creditAccCurrency;
  }

  public void setCreditAccCurrency(String creditAccCurrency) {
    this.creditAccCurrency = creditAccCurrency;
  }

  public String getDebitAccMobileNumber() {
    return debitAccMobileNumber;
  }

  public void setDebitAccMobileNumber(String debitAccMobileNumber) {
    this.debitAccMobileNumber = debitAccMobileNumber;
  }

  public String getDebitAccountDAO() {
    return debitAccountDAO;
  }

  public void setDebitAccountDAO(String debitAccountDAO) {
    this.debitAccountDAO = debitAccountDAO;
  }

  public String getDebitAccountLimit() {
    return debitAccountLimit;
  }

  public void setDebitAccountLimit(String debitAccountLimit) {
    this.debitAccountLimit = debitAccountLimit;
  }

  public String getBalBeforeDebitAccount() {
    return balBeforeDebitAccount;
  }

  public void setBalBeforeDebitAccount(String balBeforeDebitAccount) {
    this.balBeforeDebitAccount = balBeforeDebitAccount;
  }
}
