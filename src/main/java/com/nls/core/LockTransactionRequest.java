package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "LockTransactionRequest", description = "Request object with KeyField and KeyValue")
@JsonbPropertyOrder({"keyField", "keyValue", "lockValidityPeriod", "uniqueReference"})
public class LockTransactionRequest {

  @Schema(required = true, example = "ACCOUNT", description = "Key Field")
  @JsonbProperty("keyField")
  private String keyField;

  @Schema(required = true, example = "123456789", description = "Key Value")
  @JsonbProperty("keyValue")
  private String keyValue;

  @Schema(required = true, example = "60", description = "Validity Period")
  @JsonbProperty("lockValidityPeriod")
  private String lockValidityPeriod;

  @Schema(required = true, example = "20220609AAC", description = "Unique Reference")
  @JsonbProperty("uniqueReference")
  private String uniqueReference;

  public LockTransactionRequest(
      String keyField, String keyValue, String lockValidityPeriod, String uniqueReference) {
    super();
    this.keyField = keyField;
    this.keyValue = keyValue;
    this.lockValidityPeriod = lockValidityPeriod;
    this.uniqueReference = uniqueReference;
  }

  public String getKeyField() {
    return keyField;
  }

  public void setKeyField(String keyField) {
    this.keyField = keyField;
  }

  public String getKeyValue() {
    return keyValue;
  }

  public void setKeyValue(String keyValue) {
    this.keyValue = keyValue;
  }

  public String getLockValidityPeriod() {
    return lockValidityPeriod;
  }

  public void setLockValidityPeriod(String lockValidityPeriod) {
    this.lockValidityPeriod = lockValidityPeriod;
  }

  public String getUniqueReference() {
    return uniqueReference;
  }

  public void setUniqueReference(String uniqueReference) {
    this.uniqueReference = uniqueReference;
  }
}
