package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTypeAdapter;

@JsonbPropertyOrder({
  "errorCode",
  "errorMessage",
  "unitId",
  "serviceType",
  "chargeCurrency",
  "debitAccount",
  "chargeAmount"
})
public class ChargeEnquiryObject {

  @JsonbProperty("UnitId")
  public String unitId;

  @JsonbProperty("ServiceType")
  public String serviceType;

  @JsonbProperty("ErrorCode")
  @JsonbTypeAdapter(value = com.nls.posting.ERROR_CODE_SERIALIZER.class)
  public ERROR_CODE errorCode = ERROR_CODE.SUCCESSFUL;

  @JsonbProperty("ErrorMessage")
  public String errorMessage = "success";

  ;

  @JsonbProperty("ChargeCurrency")
  public String chargeCurrency;

  @JsonbProperty("DebitAccount")
  public String debitAccount;

  @JsonbProperty("ChargeAmount")
  public String chargeAmount;

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getServiceType() {
    return serviceType;
  }

  public void setServiceType(String serviceType) {
    this.serviceType = serviceType;
  }

  public ERROR_CODE getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(ERROR_CODE errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getChargeCurrency() {
    return chargeCurrency;
  }

  public void setChargeCurrency(String chargeCurrency) {
    this.chargeCurrency = chargeCurrency;
  }

  public String getDebitAccount() {
    return debitAccount;
  }

  public void setDebitAccount(String debitAccount) {
    this.debitAccount = debitAccount;
  }

  public String getChargeAmount() {
    return chargeAmount;
  }

  public void setChargeAmount(String chargeAmount) {
    this.chargeAmount = chargeAmount;
  }
}
