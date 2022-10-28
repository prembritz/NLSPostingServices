package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "ReferenceRequest", description = "Request object with Correlation Id")
@JsonbPropertyOrder({"correlationID"})
public class ReferenceRequest {

  @Schema(required = true, example = "20220609AAC", description = "Correlation Id")
  @JsonbProperty("correlationID")
  private String correlationID;

  public ReferenceRequest(String correlationID) {
    super();
    this.correlationID = correlationID;
  }

  public String getCorrelationID() {
    return correlationID;
  }

  public void setCorrelationID(String correlationID) {
    this.correlationID = correlationID;
  }
}
