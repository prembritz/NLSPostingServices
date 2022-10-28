package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({
  "resFileRefNo",
  "resBatchRefNo",
  "resNoOfTxns",
  "resErrorDesc",
  "unitId",
  "resBatchId",
  "resBatchstatus"
})
public class SalaryBatchHeaderObject {

  @JsonbProperty("resFileRefNo")
  public String resFileRefNo;

  @JsonbProperty("resBatchRefNo")
  public String resBatchRefNo;

  @JsonbProperty("resNoOfTxns")
  public String resNoOfTxns;

  @JsonbProperty("resErrorDesc")
  public String resErrorDesc = "SUCCESS";

  @JsonbProperty("unitId")
  public String unitId;

  @JsonbProperty("resBatchId")
  public String resBatchId;

  @JsonbProperty("resBatchstatus")
  public String resBatchstatus;

  public String getResFileRefNo() {
    return resFileRefNo;
  }

  public void setResFileRefNo(String resFileRefNo) {
    this.resFileRefNo = resFileRefNo;
  }

  public String getResBatchRefNo() {
    return resBatchRefNo;
  }

  public void setResBatchRefNo(String resBatchRefNo) {
    this.resBatchRefNo = resBatchRefNo;
  }

  public String getResNoOfTxns() {
    return resNoOfTxns;
  }

  public void setResNoOfTxns(String resNoOfTxns) {
    this.resNoOfTxns = resNoOfTxns;
  }

  public String getResErrorDesc() {
    return resErrorDesc;
  }

  public void setResErrorDesc(String resErrorDesc) {
    this.resErrorDesc = resErrorDesc;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getResBatchId() {
    return resBatchId;
  }

  public void setResBatchId(String resBatchId) {
    this.resBatchId = resBatchId;
  }

  public String getResBatchstatus() {
    return resBatchstatus;
  }

  public void setResBatchstatus(String resBatchstatus) {
    this.resBatchstatus = resBatchstatus;
  }
}
