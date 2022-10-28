package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"resErrorCode", "resErrorMessage"})
public class CallBackObjects {

  @JsonbProperty("resErrorCode")
  public String resErrorCode;

  @JsonbProperty("resErrorMessage")
  public String resErrorMessage;
}
