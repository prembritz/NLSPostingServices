package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
    name = "PersistUniqueDataRequest",
    description = "Request object with Unique Reference & Service Name")
@JsonbPropertyOrder({
  "uniqReference",
  "servUniqReference",
  "serviceName",
  "debitAccountNo",
  "creditAccountNo",
  "transAmount",
  "transCurrency",
  "channelId",
  "unitId",
  "transStatus",
  "requestData",
  "responseData"
})
public class PersistUniqueDataRequest {

  @Schema(required = true, example = "20220609AAC", description = "TransUnique Reference")
  @JsonbProperty("TransactionUniqueReference")
  private String uniqReference;

  @Schema(required = true, example = "20220609ABC", description = "Service Unique Reference")
  @JsonbProperty("ServiceUniqueReference")
  private String servUniqReference;

  @Schema(required = true, example = "InternalTransfer", description = "Service Name")
  @JsonbProperty("ServiceName")
  private String serviceName;

  @Schema(required = true, example = "1711460011", description = "Debit Acc Number")
  @JsonbProperty("DebitAccountNo")
  private String debitAccountNo;

  @Schema(required = true, example = "7519350046", description = "Credit Acc Number")
  @JsonbProperty("CreditAccountNo")
  private String creditAccountNo;

  @Schema(required = true, example = "1200", description = "Transaction Amount")
  @JsonbProperty("TransactionAmount")
  private String transAmount;

  @Schema(required = true, example = "KES", description = "Transaction Currency")
  @JsonbProperty("TransactionCurrency")
  private String transCurrency;

  @Schema(required = true, example = "CMS", description = "Channel ID")
  @JsonbProperty("ChannelID")
  private String channelId;

  @Schema(required = true, example = "KE0010001", description = "Unit ID")
  @JsonbProperty("UnitID")
  private String unitId;

  @Schema(required = true, example = "successful", description = "Transaction Status")
  @JsonbProperty("TransactionStatus")
  private String transStatus;

  @Schema(required = true, example = "", description = "Request Data")
  @JsonbProperty("RequestData")
  private String requestData;

  @Schema(required = true, example = "", description = "Response Data")
  @JsonbProperty("ResponseData")
  private String responseData;

  public PersistUniqueDataRequest(
      String uniqReference,
      String servUniqReference,
      String serviceName,
      String debitAccountNo,
      String creditAccountNo,
      String transAmount,
      String transCurrency,
      String channelId,
      String unitId,
      String transStatus,
      String requestData,
      String responseData) {
    super();
    this.uniqReference = uniqReference;
    this.servUniqReference = servUniqReference;
    this.serviceName = serviceName;
    this.debitAccountNo = debitAccountNo;
    this.creditAccountNo = creditAccountNo;
    this.transAmount = transAmount;
    this.transCurrency = transCurrency;
    this.channelId = channelId;
    this.unitId = unitId;
    this.transStatus = transStatus;
    this.requestData = requestData;
    this.responseData = responseData;
  }

  public String getUniqReference() {
    return uniqReference;
  }

  public void setUniqReference(String uniqReference) {
    this.uniqReference = uniqReference;
  }

  public String getServUniqReference() {
    return servUniqReference;
  }

  public void setServUniqReference(String servUniqReference) {
    this.servUniqReference = servUniqReference;
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

  public String getCreditAccountNo() {
    return creditAccountNo;
  }

  public void setCreditAccountNo(String creditAccountNo) {
    this.creditAccountNo = creditAccountNo;
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

  public String getTransStatus() {
    return transStatus;
  }

  public void setTransStatus(String transStatus) {
    this.transStatus = transStatus;
  }

  public String getRequestData() {
    return requestData;
  }

  public void setRequestData(String requestData) {
    this.requestData = requestData;
  }

  public String getResponseData() {
    return responseData;
  }

  public void setResponseData(String responseData) {
    this.responseData = responseData;
  }
}
