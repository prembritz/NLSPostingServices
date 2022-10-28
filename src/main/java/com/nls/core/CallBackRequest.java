package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "BatchCallBackRequest", description = "Request object with batchReferenceNo")
@JsonbPropertyOrder({"batchReferenceNo"})
public class CallBackRequest {

  @Schema(required = true, example = "TES1039223113", description = "Key Field")
  @JsonbProperty("BatchReferenceNo")
  private String batchReferenceNo;

  @Schema(required = true, example = "CBFTBatch", description = "ServiceName")
  @JsonbProperty("ServiceName")
  private String serviceName;

  public String getBatchReferenceNo() {
    return batchReferenceNo;
  }

  public void setBatchReferenceNo(String batchReferenceNo) {
    this.batchReferenceNo = batchReferenceNo;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public CallBackRequest() {}

  public CallBackRequest(String batchReferenceNo, String serviceName) {
    super();
    this.batchReferenceNo = batchReferenceNo;
    this.serviceName = serviceName;
  }
}
