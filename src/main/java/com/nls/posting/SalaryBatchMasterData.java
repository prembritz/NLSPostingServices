package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
    name = "SalaryBatchMasterData",
    description = "request object with hdrTranId, hdrStatus, hdrStatus, cifNo...etc")
public class SalaryBatchMasterData {

  @Schema(required = true, example = "TBC1502211514001", description = "hdr Tran Id")
  @JsonbProperty("hdrTranId")
  public String hdrTranId;

  @Schema(required = false, example = "success", description = "hdr Status")
  @JsonbProperty("hdrStatus")
  public String hdrStatus;

  @Schema(
      required = true,
      example = "KE0010001",
      description = "req User Id",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("reqUserId")
  public String reqUserId;

  @Schema(required = false, example = "34565", description = "CIF No")
  @JsonbProperty("cifNo")
  public String cifNo;

  @Schema(required = false, example = "TBC1502211514001", description = "File Reference Number")
  @JsonbProperty("fileReferenceNumber")
  public String fileReferenceNumber;

  @Schema(
      required = false,
      example = "TBC1502211514001",
      description = "Batch File Reference Number")
  @JsonbProperty("batchFileReferenceNumber")
  public String batchFileReferenceNumber;

  @Schema(required = true, example = "1711460011", description = "Debit Account Number")
  @JsonbProperty("debitAccountNumber")
  public String debitAccountNumber;

  @Schema(required = false, example = "3", description = "Comp Code")
  @JsonbProperty("compCode")
  public String compCode;

  @Schema(required = true, example = "CIB", description = "Txn Type")
  @JsonbProperty("txnType")
  public String txnType;

  @Schema(required = true, example = "KES", description = "Total Amount")
  @JsonbProperty("txnCurrency")
  public String txnCurrency;

  @Schema(required = true, example = "1000", description = "Currency")
  @JsonbProperty("txnTotalAmt")
  public String txnTotalAmt;

  @Schema(required = false, example = "", description = "Txn Mode")
  @JsonbProperty("txnMode")
  public String txnMode;

  @Schema(required = false, example = "", description = "Org Debit Account")
  @JsonbProperty("orgDebitAccount")
  public String orgDebitAccount;

  @Schema(required = false, example = "10", description = "Total Count")
  @JsonbProperty("totalCount")
  public String totalCount;

  @Schema(
      required = true,
      example = "TBC1502211514001",
      description = "Req Transaction ReferenceNo")
  @JsonbProperty("reqTransactionReferenceNo")
  public String reqTransactionReferenceNo;

  @Schema(
      required = false,
      example = "N",
      description = "VIP Marker",
      enumeration = {"N", "Y"},
      title = "Markers are NON VIP/VIP")
  @JsonbProperty("VipMarker")
  public String vipMarker;

  @Schema(
      required = true,
      example = "N",
      description = "Waive Charge",
      enumeration = {"N", "Y"},
      title = "Charges are false/true")
  @JsonbProperty("WaiveCharge")
  public String waiveCharge;

  @Schema(required = false, example = "43", description = "Req Transaction Code")
  @JsonbProperty("reqTransactionCode")
  public String reqTransactionCode;
}
