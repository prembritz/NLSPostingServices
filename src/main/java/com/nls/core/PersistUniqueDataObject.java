package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"status"})
public class PersistUniqueDataObject {

  @JsonbProperty("Status")
  private String status;

  public PersistUniqueDataObject() {
    this.setStatus(status);
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
