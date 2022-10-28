package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
    name = "DuplicateCheckRequest",
    description = "Request object with Unique Reference and Service Name")
@JsonbPropertyOrder({"uniqueReference", "serviceName", "transactionStatus"})
public class DuplicateCheckRequest {

  @Schema(required = true, example = "20220609AAC", description = "Unique Reference")
  @JsonbProperty("TransactionUniqueReference")
  private String uniqueReference;

  @Schema(required = true, example = "InternalTransfer", description = "Service Name")
  @JsonbProperty("ServiceName")
  private String serviceName;

  @Schema(required = true, example = "successful", description = "Transaction Status")
  @JsonbProperty("TransactionStatus")
  private String transactionStatus;

  public DuplicateCheckRequest(
      String uniqueReference, String serviceName, String transactionStatus) {
    super();
    this.uniqueReference = uniqueReference;
    this.serviceName = serviceName;
    this.transactionStatus = transactionStatus;
  }

  public String getUniqueReference() {
    return uniqueReference;
  }

  public void setUniqueReference(String uniqueReference) {
    this.uniqueReference = uniqueReference;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getTransactionStatus() {
    return transactionStatus;
  }

  public void setTransactionStatus(String transactionStatus) {
    this.transactionStatus = transactionStatus;
  }
}
