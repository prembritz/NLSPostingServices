package com.nls.posting;

import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTypeAdapter;

@JsonbNillable(true)
@JsonbPropertyOrder({
  "transactionID",
  "referenceNo",
  "resultCode",
  "cbxReferenceNo",
  "userId",
  "statusCode",
  "statusDesc",
  "errCode",
  "errorDesc",
  "errorMsg",
  "requestId",
  "coreReferenceNo"
})
public class PSRTGSObject {

  @JsonbProperty("hdrTranId")
  public String transactionID;

  @JsonbProperty("hdrRefNo")
  public String referenceNo = "1";

  @JsonbProperty("resResultCode")
  @JsonbTypeAdapter(value = com.nls.posting.ERROR_CODE_SERIALIZER.class)
  public ERROR_CODE resultCode = ERROR_CODE.SUCCESSFUL;

  @JsonbProperty("resCbxReferenceNo")
  public String cbxReferenceNo;

  @JsonbProperty("resUserId")
  public String userId;

  @JsonbProperty("resStatusCode")
  @JsonbTypeAdapter(value = com.nls.posting.ERROR_CODE_SERIALIZER.class)
  public ERROR_CODE statusCode = ERROR_CODE.SUCCESSFUL;

  @JsonbProperty("resStatusDescription")
  public String statusDesc = "success";

  @JsonbProperty("resErrorCode")
  @JsonbTypeAdapter(value = com.nls.posting.ERROR_CODE_SERIALIZER.class)
  public ERROR_CODE errCode = ERROR_CODE.SUCCESSFUL;

  @JsonbProperty("resErrorDesc")
  public String errorDesc = "success";

  @JsonbProperty("resErrorMessage")
  public String errorMsg = "success";

  @JsonbProperty("resRequestID")
  public String requestId;

  @JsonbProperty("resCoreReferenceNo")
  public String coreReferenceNo;

  public String getTransactionID() {
    return transactionID;
  }

  public void setTransactionID(String transactionID) {
    this.transactionID = transactionID;
  }

  public String getReferenceNo() {
    return referenceNo;
  }

  public void setReferenceNo(String referenceNo) {
    this.referenceNo = referenceNo;
  }

  public ERROR_CODE getResultCode() {
    return resultCode;
  }

  public void setResultCode(ERROR_CODE resultCode) {
    this.resultCode = resultCode;
  }

  public String getCbxReferenceNo() {
    return cbxReferenceNo;
  }

  public void setCbxReferenceNo(String cbxReferenceNo) {
    this.cbxReferenceNo = cbxReferenceNo;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public ERROR_CODE getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(ERROR_CODE statusCode) {
    this.statusCode = statusCode;
  }

  public String getStatusDesc() {
    return statusDesc;
  }

  public void setStatusDesc(String statusDesc) {
    this.statusDesc = statusDesc;
  }

  public ERROR_CODE getErrCode() {
    return errCode;
  }

  public void setErrCode(ERROR_CODE errCode) {
    this.errCode = errCode;
  }

  public String getErrorDesc() {
    return errorDesc;
  }

  public void setErrorDesc(String errorDesc) {
    this.errorDesc = errorDesc;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public String getCoreReferenceNo() {
    return coreReferenceNo;
  }

  public void setCoreReferenceNo(String coreReferenceNo) {
    this.coreReferenceNo = coreReferenceNo;
  }
}
