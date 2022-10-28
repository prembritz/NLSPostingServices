package com.nls.core;

import java.util.Map;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "OFSFormatRequest", description = "Request object with Service Name")
@JsonbPropertyOrder({"serviceName", "serviceData"})
public class OFSFormatRequest {

  @Schema(required = true, example = "InternalTransfer", description = "Service Name")
  @JsonbProperty("ServiceName")
  public String serviceName;

  @JsonbProperty("ServiceData")
  public Map<String, String> serviceData;

  public OFSFormatRequest(String serviceName, Map<String, String> serviceData) {
    super();
    this.serviceName = serviceName;
    this.serviceData = serviceData;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public Map<String, String> getServiceData() {
    return serviceData;
  }

  public void setServiceData(Map<String, String> serviceData) {
    this.serviceData = serviceData;
  }
}
