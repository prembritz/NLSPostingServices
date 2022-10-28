package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({
  "hdrTranId",
  "procode",
  "unitid",
  "reqTransactionCurrency",
  "transactionrefno",
  "cif",
  "settlementaccountno",
  "amount",
  "valuedate",
  "txndate",
  "narration",
  "drcrflag",
  "reqPostingIndicator",
  "VipMarker",
  "waiveCharge"
})
@Schema(
    name = "InterestingRequest",
    description = "request object with procode, unitid, transactionrefno, cif...etc")
public class AccuralRequest {

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

  @Schema(required = true, example = "AccrualPosting", description = "ProCode")
  @JsonbProperty("hdrTranId")
  public String hdrTranId;

  @Schema(required = false, example = "PRDT002", description = "ProCode")
  @JsonbProperty("ProcCode")
  public String procode;

  @Schema(
      required = true,
      example = "IGTBKE",
      description = "UnitID",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("reqUnitID")
  public String unitid;

  @Schema(required = true, example = "KES", description = "req Debit Acc Currency")
  @JsonbProperty("reqTransactionCurrency")
  public String reqTransactionCurrency;

  @Schema(required = true, example = "TBS2104090375861", description = "TransactionRefNo")
  @JsonbProperty("reqTransactionReferenceNo")
  public String transactionrefno;

  @Schema(required = false, example = "5000020021", description = "CIF")
  @JsonbProperty("reqCIF")
  public String cif;

  @Schema(required = true, example = "01-01-001-053-22970", description = "SettlementAccountNo")
  @JsonbProperty("reqSettlementAccountNo")
  public String settlementaccountno;

  @Schema(required = true, example = "100", description = "Amount")
  @JsonbProperty("reqAmount")
  public String amount;

  @Schema(required = false, example = "08042021", description = "ValueDate")
  @JsonbProperty("reqValueDate")
  public String valuedate;

  @Schema(required = false, example = "13042021", description = "TxnDate")
  @JsonbProperty("reqTransactionDate")
  public String txndate;

  @Schema(required = false, example = "DOMEWT -TBS2104090375861 - -", description = "reqNarration")
  @JsonbProperty("reqNarration")
  public String narration;

  @Schema(required = false, example = "C", description = "reqDRCRFlag")
  @JsonbProperty("reqDRCRFlag")
  public String drcrflag;

  @Schema(required = false, example = "N", description = "reqPostingIndicator")
  @JsonbProperty("reqPostingIndicator")
  public String reqPostingIndicator;
}
