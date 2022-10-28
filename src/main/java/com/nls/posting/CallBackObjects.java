package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTypeAdapter;

@JsonbPropertyOrder({"resErrorCode", "resErrorMessage"})
public class CallBackObjects {

  @JsonbProperty("resErrorCode")
  @JsonbTypeAdapter(value = com.nls.posting.ERROR_CODE_SERIALIZER.class)
  public ERROR_CODE resErrorCode = ERROR_CODE.SUCCESSFUL;

  @JsonbProperty("resErrorMessage")
  public String resErrorMessage = "success";

  public ERROR_CODE getResErrorCode() {
    return resErrorCode;
  }

  public void setResErrorCode(ERROR_CODE resErrorCode) {
    this.resErrorCode = resErrorCode;
  }

  public String getResErrorMessage() {
    return resErrorMessage;
  }

  public void setResErrorMessage(String resErrorMessage) {
    this.resErrorMessage = resErrorMessage;
  }
  ;
}
