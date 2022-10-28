package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"status", "ofsMsg"})
public class OFSFormatObject {

  @JsonbProperty("Status")
  private String status;

  @JsonbProperty("OFSMessage")
  private String ofsMsg;

  public OFSFormatObject() {
    this.setStatus(status);
    this.setOfsMsg(ofsMsg);
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getOfsMsg() {
    return ofsMsg;
  }

  public void setOfsMsg(String ofsMsg) {
    this.ofsMsg = ofsMsg;
  }
}
