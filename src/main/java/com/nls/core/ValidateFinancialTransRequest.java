package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
    name = "ValidateFinancialTransRequest",
    description = "Request object with Unique Reference and Service Name")
@JsonbPropertyOrder({
  "orgnUnit",
  "uniqueReference",
  "serviceName",
  "debitAccountNo",
  "creditAccountno",
  "transAmount",
  "transCurrency"
})
public class ValidateFinancialTransRequest {

  @Schema(required = true, example = "KE0010001", description = "Organization Unit")
  @JsonbProperty("OrganisationUnit")
  private String orgnUnit;

  @Schema(required = true, example = "20220609AAC", description = "Unique Reference")
  @JsonbProperty("UniqueReference")
  private String uniqueReference;

  @Schema(required = true, example = "InternalTransfer", description = "Service Name")
  @JsonbProperty("ServiceName")
  private String serviceName;

  @Schema(required = true, example = "123456789", description = "Debit Acc Number")
  @JsonbProperty("DebitAcctNo")
  private String debitAccountNo;

  @Schema(required = true, example = "6989420028", description = "Credit Acc Number")
  @JsonbProperty("CreditAcctNo")
  private String creditAccountno;

  @Schema(required = true, example = "1200", description = "Transaction Amount")
  @JsonbProperty("TransactionAmount")
  private String transAmount;

  @Schema(required = true, example = "KES", description = "Transaction Currency")
  @JsonbProperty("TransactionCurrency")
  private String transCurrency;

  @Schema(required = true, example = "VIP", description = "Transaction Flag")
  @JsonbProperty("TransactionFlag")
  private String transFlag;

  @Schema(required = true, example = "false", description = "Waive Charge")
  @JsonbProperty("WaiveCharge")
  private String waiveCharge;

  public ValidateFinancialTransRequest(
      String orgnUnit,
      String uniqueReference,
      String serviceName,
      String debitAccountNo,
      String creditAccountno,
      String transAmount,
      String transCurrency,
      String transFlag,
      String waiveCharge) {
    super();
    this.orgnUnit = orgnUnit;
    this.uniqueReference = uniqueReference;
    this.serviceName = serviceName;
    this.debitAccountNo = debitAccountNo;
    this.creditAccountno = creditAccountno;
    this.transAmount = transAmount;
    this.transCurrency = transCurrency;
    this.transFlag = transFlag;
    this.waiveCharge = waiveCharge;
  }

  public String getOrgnUnit() {
    return orgnUnit;
  }

  public void setOrgnUnit(String orgnUnit) {
    this.orgnUnit = orgnUnit;
  }

  public String getUniqueReference() {
    return uniqueReference;
  }

  public void setUniqueReference(String uniqueReference) {
    this.uniqueReference = uniqueReference;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getDebitAccountNo() {
    return debitAccountNo;
  }

  public void setDebitAccountNo(String debitAccountNo) {
    this.debitAccountNo = debitAccountNo;
  }

  public String getCreditAccountno() {
    return creditAccountno;
  }

  public void setCreditAccountno(String creditAccountno) {
    this.creditAccountno = creditAccountno;
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

  public String getTransFlag() {
    return transFlag;
  }

  public void setTransFlag(String transFlag) {
    this.transFlag = transFlag;
  }

  public String getWaiveCharge() {
    return waiveCharge;
  }

  public void setWaiveCharge(String waiveCharge) {
    this.waiveCharge = waiveCharge;
  }
}
