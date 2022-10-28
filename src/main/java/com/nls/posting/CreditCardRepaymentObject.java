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
  "resErrorMessage",
  "resDescription",
  "resCoreReferenceNo",
  "resStatusDescriptionKey",
  "resApplicationID",
  "resMessageType",
  "resCode"
})
public class CreditCardRepaymentObject {

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
  public String resStatusDescription = "success";

  @JsonbProperty("resDescription")
  public String resDescription;

  @JsonbProperty("resCoreReferenceNo")
  public String resCoreReferenceNo;

  @JsonbProperty("resStatusDescriptionKey")
  public String resStatusDescriptionKey;

  @JsonbProperty("resApplicationID")
  public String resApplicationID;

  @JsonbProperty("resMessageType")
  public String resMessageType;

  @JsonbProperty("resCode")
  public String resCode;

  @JsonbProperty("resErrorCode")
  @JsonbTypeAdapter(value = com.nls.posting.ERROR_CODE_SERIALIZER.class)
  public ERROR_CODE resErrorCode = ERROR_CODE.SUCCESSFUL;

  @JsonbProperty("resErrorMessage")
  public String resErrorMessage = "success";

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

  public String getResErrorMessage() {
    return resErrorMessage;
  }

  public void setResErrorMessage(String resErrorMessage) {
    this.resErrorMessage = resErrorMessage;
  }

  public String getResDescription() {
    return resDescription;
  }

  public void setResDescription(String resDescription) {
    this.resDescription = resDescription;
  }

  public String getResCoreReferenceNo() {
    return resCoreReferenceNo;
  }

  public void setResCoreReferenceNo(String resCoreReferenceNo) {
    this.resCoreReferenceNo = resCoreReferenceNo;
  }

  public String getResStatusDescriptionKey() {
    return resStatusDescriptionKey;
  }

  public void setResStatusDescriptionKey(String resStatusDescriptionKey) {
    this.resStatusDescriptionKey = resStatusDescriptionKey;
  }

  public String getResApplicationID() {
    return resApplicationID;
  }

  public void setResApplicationID(String resApplicationID) {
    this.resApplicationID = resApplicationID;
  }

  public String getResMessageType() {
    return resMessageType;
  }

  public void setResMessageType(String resMessageType) {
    this.resMessageType = resMessageType;
  }

  public String getResCode() {
    return resCode;
  }

  public void setResCode(String resCode) {
    this.resCode = resCode;
  }

  public ERROR_CODE getResResultCode() {
    return resResultCode;
  }

  public void setResResultCode(ERROR_CODE resResultCode) {
    this.resResultCode = resResultCode;
  }

  public ERROR_CODE getResStatusCode() {
    return resStatusCode;
  }

  public void setResStatusCode(ERROR_CODE resStatusCode) {
    this.resStatusCode = resStatusCode;
  }

  public CreditCardRepaymentObject() {
    this.setHdrTranId(hdrTranId);
    this.setHdrRefNo(hdrRefNo);
    this.setResResultCode(resResultCode);
    this.setResCbxReferenceNo(resCbxReferenceNo);
    this.setResUserId(resUserId);
    this.setResStatusCode(resStatusCode);
    this.setResStatusDescription(resStatusDescription);
    this.setResErrorCode(resErrorCode);
    this.setResErrorMessage(resErrorMessage);
    this.setResDescription(resDescription);
    this.setResCoreReferenceNo(resCoreReferenceNo);
    this.setResStatusDescriptionKey(resStatusDescriptionKey);
    this.setResApplicationID(resApplicationID);
    this.setResMessageType(resMessageType);
    this.setResCode(resCode);
  }
}
