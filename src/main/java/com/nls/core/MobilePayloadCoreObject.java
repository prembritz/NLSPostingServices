package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"code", "status", "description", "mNORef", "transactionID"})
public class MobilePayloadCoreObject {

  @JsonbProperty("Code")
  public String code;

  @JsonbProperty("Status")
  public String status = "F";

  @JsonbProperty("Description")
  public String description;

  @JsonbProperty("MNORef")
  public String mNORef;

  @JsonbProperty("TransactionID")
  public String transactionID;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getmNORef() {
    return mNORef;
  }

  public void setmNORef(String mNORef) {
    this.mNORef = mNORef;
  }

  public String getTransactionID() {
    return transactionID;
  }

  public void setTransactionID(String transactionID) {
    this.transactionID = transactionID;
  }
}
