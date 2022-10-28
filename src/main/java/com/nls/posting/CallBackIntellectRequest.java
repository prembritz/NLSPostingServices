package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "CallBackIntellectRequest", description = "Request object with batchReferenceNo")
@JsonbPropertyOrder({"batchReferenceNo", "transReferenceNo"})
public class CallBackIntellectRequest {

  @Schema(required = true, example = "TBC1502211512002", description = "Batch Reference No")
  @JsonbProperty("BatchReferenceNo")
  private String batchReferenceNo;

  @Schema(required = true, example = "5452019050801742", description = "Trans Reference No")
  @JsonbProperty("TransactionReferenceNo")
  private String transReferenceNo;

  public String getBatchReferenceNo() {
    return batchReferenceNo;
  }

  public void setBatchReferenceNo(String batchReferenceNo) {
    this.batchReferenceNo = batchReferenceNo;
  }

  public String getTransReferenceNo() {
    return transReferenceNo;
  }

  public void setTransReferenceNo(String transReferenceNo) {
    this.transReferenceNo = transReferenceNo;
  }
}
