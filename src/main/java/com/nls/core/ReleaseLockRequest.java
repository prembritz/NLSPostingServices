package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "ReleaseLockRequest", description = "Request object with Unique Reference")
@JsonbPropertyOrder({"uniqReference"})
public class ReleaseLockRequest {

  @Schema(required = true, example = "20220609AAC", description = "Unique Reference")
  @JsonbProperty("uniqueReference")
  private String uniqReference;

  public ReleaseLockRequest(String uniqReference) {
    super();
    this.uniqReference = uniqReference;
  }

  public String getUniqReference() {
    return uniqReference;
  }

  public void setUniqReference(String uniqReference) {
    this.uniqReference = uniqReference;
  }
}
