package com.nls.core;

import com.nls.posting.ERROR_CODE;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTypeAdapter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
    name = "PesaLinkCoreRequest",
    description = "Request object with resErrorCode & resErrorMessage")
@JsonbPropertyOrder({"responseCode", "responseDesc"})
public class PesaLinkCoreObject {

  @JsonbProperty("responseCode")
  @JsonbTypeAdapter(value = com.nls.posting.ERROR_CODE_SERIALIZER.class)
  public ERROR_CODE responseCode = ERROR_CODE.SUCCESSFUL;

  @JsonbProperty("responseDesc")
  public String responseDesc = "success";

  public ERROR_CODE getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(ERROR_CODE responseCode) {
    this.responseCode = responseCode;
  }

  public String getResponseDesc() {
    return responseDesc;
  }

  public void setResponseDesc(String responseDesc) {
    this.responseDesc = responseDesc;
  }
}
