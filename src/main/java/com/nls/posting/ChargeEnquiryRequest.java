package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({"unitId", "serviceType", "debitAccount", "chargeCurrency", "amount"})
@Schema(
    name = "ChargeEnquiryRequest",
    description = "request object with unitId, serviceType,debitAccount...etc")
public class ChargeEnquiryRequest {

  @Schema(
      required = true,
      example = "KE0010001",
      description = "Unit Id",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("UnitID")
  private String unitId;

  @Schema(
      required = true,
      example = "RTGS",
      description = "Service Type",
      enumeration = {"RTGS", "SaccoTransfer", "EFT", "PesaLink", "MobileAirtimeTransfer"})
  @JsonbProperty("ServiceType")
  private String serviceType;

  @Schema(required = true, example = "1711460011", description = "Debit Account")
  @JsonbProperty("DebitAccount")
  private String debitAccount;

  @Schema(required = true, example = "KES", description = "Charge Currency")
  @JsonbProperty("ChargeCurrency")
  private String chargeCurrency;

  @Schema(required = true, example = "20", description = "Trans Amount")
  @JsonbProperty("TransAmount")
  private String amount;

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

  public String getDebitAccount() {
    return debitAccount;
  }

  public void setDebitAccount(String debitAccount) {
    this.debitAccount = debitAccount;
  }

  public String getChargeCurrency() {
    return chargeCurrency;
  }

  public void setChargeCurrency(String chargeCurrency) {
    this.chargeCurrency = chargeCurrency;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }
}
