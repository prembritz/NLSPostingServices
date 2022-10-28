package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"status", "errorDetail"})
public class QueueFinancialTransObject {

  @JsonbProperty("Status")
  private String status;

  @JsonbProperty("ErrorDetail")
  private String errorDetail;

  public QueueFinancialTransObject() {

    this.setStatus(status);
    this.setErrorDetail(errorDetail);
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getErrorDetail() {
    return errorDetail;
  }

  public void setErrorDetail(String errorDetail) {
    this.errorDetail = errorDetail;
  }
}
