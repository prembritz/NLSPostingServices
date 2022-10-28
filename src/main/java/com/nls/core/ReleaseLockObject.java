package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"releaseLockCount", "uniqueReference", "status"})
public class ReleaseLockObject {

  @JsonbProperty("releasedLockCount")
  private String releaseLockCount;

  @JsonbProperty("uniqueReference")
  private String uniqueReference;

  @JsonbProperty("status")
  private String status;

  public ReleaseLockObject() {

    this.setReleaseLockCount(releaseLockCount);
    this.setUniqueReference(uniqueReference);
    this.setStatus(status);
  }

  public String getReleaseLockCount() {
    return releaseLockCount;
  }

  public void setReleaseLockCount(String releaseLockCount) {
    this.releaseLockCount = releaseLockCount;
  }

  public String getUniqueReference() {
    return uniqueReference;
  }

  public void setUniqueReference(String uniqueReference) {
    this.uniqueReference = uniqueReference;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
