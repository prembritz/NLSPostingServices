package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "BatchCallBackRequest", description = "Request object with batchReferenceNo")
@JsonbPropertyOrder({"batchReferenceNo"})
public class CallBackRequest {

  @Schema(required = true, example = "TES1039223113", description = "Batch Reference No")
  @JsonbProperty("BatchReferenceNo")
  private String batchReferenceNo;

  @Schema(required = true, example = "RTGS", description = "Service Name")
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
}
