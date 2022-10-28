package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTypeAdapter;

@JsonbPropertyOrder({
  "hdrTranId",
  "hdrRefNo",
  "resResultCode",
  "resCbxReferenceNo",
  "resUserId",
  "resStatusCode",
  "resStatusDescription",
  "resErrorCode",
  "resErrorDesc",
  "resErrorMessage",
  "resRequestID",
  "resCoreReferenceNo"
})
public class InterestObject {
  @JsonbProperty("hdrTranId")
  public String hdrTranId;

  @JsonbProperty("hdrRefNo")
  public String hdrRefNo = "1";

  @JsonbProperty("resResultCode")
  @JsonbTypeAdapter(value = com.nls.posting.ERROR_CODE_SERIALIZER.class)
  public ERROR_CODE resResultCode = ERROR_CODE.SUCCESSFUL;

  @JsonbProperty("resCbxReferenceNo")
  public String resCbxReferenceNo;

  @JsonbProperty("resUserId")
  public String resUserId;

  @JsonbProperty("resStatusCode")
  @JsonbTypeAdapter(value = com.nls.posting.ERROR_CODE_SERIALIZER.class)
  public ERROR_CODE resStatusCode = ERROR_CODE.SUCCESSFUL;

  @JsonbProperty("resStatusDescription")
  public String resStatusDescription;

  @JsonbProperty("resErrorCode")
  @JsonbTypeAdapter(value = com.nls.posting.ERROR_CODE_SERIALIZER.class)
  public ERROR_CODE resErrorCode = ERROR_CODE.SUCCESSFUL;

  @JsonbProperty("resErrorDesc")
  public String resErrorDesc;

  @JsonbProperty("resErrorMessage")
  public String resErrorMessage = "success";

  ;

  @JsonbProperty("resRequestID")
  public String resRequestID;

  @JsonbProperty("resCoreReferenceNo")
  public String resCoreReferenceNo;

  public String getHdrTranId() {
    return hdrTranId;
  }

  public void setHdrTranId(String hdrTranId) {
    this.hdrTranId = hdrTranId;
  }

  public String getHdrRefNo() {
    return hdrRefNo;
  }

  public void setHdrRefNo(String hdrRefNo) {
    this.hdrRefNo = hdrRefNo;
  }

  public ERROR_CODE getResResultCode() {
    return resResultCode;
  }

  public void setResResultCode(ERROR_CODE resResultCode) {
    this.resResultCode = resResultCode;
  }

  public String getResCbxReferenceNo() {
    return resCbxReferenceNo;
  }

  public void setResCbxReferenceNo(String resCbxReferenceNo) {
    this.resCbxReferenceNo = resCbxReferenceNo;
  }

  public String getResUserId() {
    return resUserId;
  }

  public void setResUserId(String resUserId) {
    this.resUserId = resUserId;
  }

  public ERROR_CODE getResStatusCode() {
    return resStatusCode;
  }

  public void setResStatusCode(ERROR_CODE resStatusCode) {
    this.resStatusCode = resStatusCode;
  }

  public String getResStatusDescription() {
    return resStatusDescription;
  }

  public void setResStatusDescription(String resStatusDescription) {
    this.resStatusDescription = resStatusDescription;
  }

  public ERROR_CODE getResErrorCode() {
    return resErrorCode;
  }

  public void setResErrorCode(ERROR_CODE resErrorCode) {
    this.resErrorCode = resErrorCode;
  }

  public String getResErrorDesc() {
    return resErrorDesc;
  }

  public void setResErrorDesc(String resErrorDesc) {
    this.resErrorDesc = resErrorDesc;
  }

  public String getResErrorMessage() {
    return resErrorMessage;
  }

  public void setResErrorMessage(String resErrorMessage) {
    this.resErrorMessage = resErrorMessage;
  }

  public String getResRequestID() {
    return resRequestID;
  }

  public void setResRequestID(String resRequestID) {
    this.resRequestID = resRequestID;
  }

  public String getResCoreReferenceNo() {
    return resCoreReferenceNo;
  }

  public void setResCoreReferenceNo(String resCoreReferenceNo) {
    this.resCoreReferenceNo = resCoreReferenceNo;
  }
}
