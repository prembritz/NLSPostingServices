package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({
  "hdrTranId",
  "hdrStatus",
  "reqChnlId",
  "reqTxnDt",
  "reqInterfaceId",
  "reqRefNo",
  "reqEchoField",
  "reqUnitId",
  "reqAccNo",
  "requtilpaycode",
  "reqAmt",
  "reqConvMode",
  "reqConvRate",
  "reqconsumerid",
  "reqValDt",
  "unit",
  "reqTxnCCY",
  "reqbillamt",
  "reqgcif",
  "reqServiceType",
  "reqtaxpayername",
  "reqeslipdate",
  "reqtaxpayerpin",
  "reqdocrefno",
  "reqtransparticulars",
  "custreference",
  "resBillerCode",
  "resTaxpayerPin",
  "hdrRefNo",
  "SystemCode",
  "resPayeeInvoiceCurrency",
  "resTotalAmount",
  "resPartnerName",
  "resPayeeTaxAmount",
  "resSlipPaymentCode",
  "resServiceTypeCode",
  "resPaymentAdviceDate",
  "resTaxPayerFullName",
  "resMessageDescription",
  "reseSlipNumber",
  "resPaymentTypeCode",
  "resPayeeTaxName",
  "resPayeeTaxPeriod",
  "resPayeeInvoiceCode",
  "resAccountNumber",
  "resInvoiceCurrency",
  "EXRATE1",
  "DRCRFLAG",
  "reqPostingFlg",
  "reqDebitccy",
  "reqDebitamt",
  "vipMarker",
  "waiveCharge"
})
@Schema(
    name = "TaxTransactionRequest",
    description =
        "request object with HdrTranId, hdrStatus, reqChnlId, reqUnitID, reqChannelName...etc")
public class StatutoryPaymentRequest {

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

  @Schema(
      required = true,
      example = "NHIF",
      description = "Hdr TranId",
      enumeration = {"NHIF", "NSSF"})
  @JsonbProperty("hdrTranId")
  public String hdrTranId;

  @Schema(required = false, example = "NULL", description = "Hdr Status")
  @JsonbProperty("hdrStatus")
  public String hdrStatus;

  @Schema(required = true, example = "CMS", description = "req ChnlId")
  @JsonbProperty("reqChnlId")
  public String reqChnlId;

  @Schema(required = false, example = "20190628103536", description = "req txn date")
  @JsonbProperty("reqTxnDt")
  public String reqTxnDt;

  @Schema(required = false, example = "CB001", description = "req interface id")
  @JsonbProperty("reqInterfaceId")
  public String reqInterfaceId;

  @Schema(required = true, example = "C734280619090532", description = "req ref no")
  @JsonbProperty("reqRefNo")
  public String reqRefNo;

  @Schema(required = false, example = "C734280619090532", description = "Req echo field")
  @JsonbProperty("reqEchoField")
  public String reqEchoField;

  @Schema(
      required = true,
      example = "KE0010001",
      description = "req UnitID",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("reqUnitId")
  public String reqUnitId;

  @Schema(required = true, example = "1711460011", description = "req account no")
  @JsonbProperty("reqAccNo")
  public String reqAccNo;

  @Schema(required = false, example = "KRA", description = "req util pay code")
  @JsonbProperty("requtilpaycode")
  public String requtilpaycode;

  @Schema(required = true, example = "10", description = "req amount")
  @JsonbProperty("reqAmt")
  public String reqAmt;

  @Schema(required = false, example = "", description = "req conv mode")
  @JsonbProperty("reqConvMode")
  public String reqConvMode;

  @Schema(required = false, example = "", description = "req conversation rate")
  @JsonbProperty("reqConvRate")
  public String reqConvRate;

  @Schema(required = false, example = "2020190003107689", description = "req consumer id")
  @JsonbProperty("reqconsumerid")
  public String reqconsumerid;

  @Schema(required = false, example = "20190628", description = "req value date")
  @JsonbProperty("reqValDt")
  public String reqValDt;

  @Schema(required = false, example = "KE001001", description = "unit")
  @JsonbProperty("unit")
  public String unit;

  @Schema(required = false, example = "KES", description = "req txn currency")
  @JsonbProperty("reqTxnCCY")
  public String reqTxnCCY;

  @Schema(required = false, example = "1000000", description = "req bill amount")
  @JsonbProperty("reqbillamt")
  public String reqbillamt;

  @Schema(required = false, example = "10000112", description = "req gcif")
  @JsonbProperty("reqgcif")
  public String reqgcif;

  @Schema(
      required = true,
      example = "KRA",
      description = "Service Type",
      enumeration = {"RTGS", "SaccoTransfer", "EFT", "PesaLink", "MobileAirtimeTransfer"})
  @JsonbProperty("reqServiceType")
  public String reqServiceType;

  @Schema(required = false, example = "", description = "req tax payer name")
  @JsonbProperty("reqtaxpayername")
  public String reqtaxpayername;

  @Schema(required = false, example = "", description = "req eslip date")
  @JsonbProperty("reqeslipdate")
  public String reqeslipdate;

  @Schema(required = false, example = "", description = "req tax payer pin")
  @JsonbProperty("reqtaxpayerpin")
  public String reqtaxpayerpin;

  @Schema(required = false, example = "", description = "req doc ref no")
  @JsonbProperty("reqdocrefno")
  public String reqdocrefno;

  @Schema(required = false, example = "", description = "req trans particulars")
  @JsonbProperty("reqtransparticulars")
  public String reqtransparticulars;

  @Schema(required = false, example = "KRA null 2020190003107689", description = "cust reference")
  @JsonbProperty("custreference")
  public String custreference;

  @Schema(required = false, example = "0009", description = "res biller code")
  @JsonbProperty("resBillerCode")
  public String resBillerCode;

  @Schema(required = false, example = "A004378173E", description = "res tax payer pin")
  @JsonbProperty("resTaxpayerPin")
  public String resTaxpayerPin;

  @Schema(required = true, example = "1", description = "hdr ref no")
  @JsonbProperty("hdrRefNo")
  public String hdrRefNo;

  @Schema(required = false, example = "PG", description = "system code")
  @JsonbProperty("SystemCode")
  public String SystemCode;

  @Schema(required = false, example = "KES", description = "res payee invoice currency")
  @JsonbProperty("resPayeeInvoiceCurrency")
  public String resPayeeInvoiceCurrency;

  @Schema(required = false, example = "1000000", description = "res total amount")
  @JsonbProperty("resTotalAmount")
  public String resTotalAmount;

  @Schema(required = false, example = "KRA - DOMESTIC", description = "res partner name")
  @JsonbProperty("resPartnerName")
  public String resPartnerName;

  @Schema(required = false, example = "1000000", description = "res payee tax amount")
  @JsonbProperty("resPayeeTaxAmount")
  public String resPayeeTaxAmount;

  @Schema(required = false, example = "", description = "res slip payment code")
  @JsonbProperty("resSlipPaymentCode")
  public String resSlipPaymentCode;

  @Schema(required = false, example = "BUS017", description = "res service type code")
  @JsonbProperty("resServiceTypeCode")
  public String resServiceTypeCode;

  @Schema(required = false, example = "20190614", description = "res payment advice date")
  @JsonbProperty("resPaymentAdviceDate")
  public String resPaymentAdviceDate;

  @Schema(required = false, example = "Gilbert", description = "res tax payer name")
  @JsonbProperty("resTaxPayerFullName")
  public String resTaxPayerFullName;

  @Schema(
      required = false,
      example = "REQUESTED/CONSULTED E SLIP NUMBER (PRN) EXISTS IN THE SYSTEM",
      description = "res message description")
  @JsonbProperty("resMessageDescription")
  public String resMessageDescription;

  @Schema(required = false, example = "2020190003107689", description = "res eslip number")
  @JsonbProperty("reseSlipNumber")
  public String reseSlipNumber;

  @Schema(required = false, example = "1100119", description = "res payment type code")
  @JsonbProperty("resPaymentTypeCode")
  public String resPaymentTypeCode;

  @Schema(
      required = false,
      example = "Income Tax - Resident Individual",
      description = "res payee tax name")
  @JsonbProperty("resPayeeTaxName")
  public String resPayeeTaxName;

  @Schema(required = false, example = "2016-01-01", description = "res payee tax period")
  @JsonbProperty("resPayeeTaxPeriod")
  public String resPayeeTaxPeriod;

  @Schema(required = false, example = "3100", description = "res payee invoice code")
  @JsonbProperty("resPayeeInvoiceCode")
  public String resPayeeInvoiceCode;

  @Schema(required = false, example = "01136006150700", description = "res Acc number")
  @JsonbProperty("resAccountNumber")
  public String resAccountNumber;

  @Schema(required = false, example = "KES", description = "req Invoice Currency")
  @JsonbProperty("resInvoiceCurrency")
  public String resInvoiceCurrency;

  @Schema(required = false, example = "", description = "Exchange Rate")
  @JsonbProperty("EXRATE1")
  public String EXRATE1;

  @Schema(required = false, example = "", description = "req Credit Flag")
  @JsonbProperty("DRCRFLAG")
  public String DRCRFLAG;

  @Schema(
      required = false,
      example = "NHIF",
      description = "req Posting flag",
      enumeration = {"NHIF", "NSSF"})
  @JsonbProperty("reqPostingFlg")
  public String reqPostingFlg;

  @Schema(required = true, example = "KES", description = "req Debit Currency")
  @JsonbProperty("reqDebitccy")
  public String reqDebitccy;

  @Schema(required = true, example = "1000000", description = "req Debit Amt")
  @JsonbProperty("reqDebitamt")
  public String reqDebitamt;
}
