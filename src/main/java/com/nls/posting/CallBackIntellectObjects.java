package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"status"})
public class CallBackIntellectObjects {

  /*
   * @JsonbProperty("res_ErrorCode")
   *
   * @JsonbTypeAdapter(value = com.nls.posting.ERROR_CODE_SERIALIZER.class) public ERROR_CODE
   * resErrorCode = ERROR_CODE.SUCCESSFUL;
   *
   * @JsonbProperty("res_ErrorMessage") public String resErrorMessage = "success";
   */

  @JsonbProperty("StatusDescription")
  public String status = "Acknowledged";

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
