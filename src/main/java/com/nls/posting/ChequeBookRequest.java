package com.nls.posting;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({
  "hdrTranId",
  "reqChnlId",
  "reqUnitID",
  "reqTransactionReferenceNo",
  "reqAccountNo",
  "debitAccountCurrency",
  "chequeDeliveryCategory",
  "noOfLeaves",
  "chequeBooks",
  "branchNo",
  "userPreferredAddress",
  "authorizedPersonIDtype",
  "authorizedPersonID",
  "authorizedPersonName",
  "authorizedPersonEmail",
  "authorizedPersonMobile",
  "vipMarker",
  "waiveCharge"
})
@Schema(name = "ChequeBookRequest", description = "request object with reqChnlId, reqUnitID ...etc")
public class ChequeBookRequest {

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

  @Schema(required = true, example = "Chequebookreq", description = "Hdr Trans Id")
  @JsonbProperty("hdrTranId")
  public String hdrTranId;

  @Schema(required = true, example = "CBX", description = "req Channel Id")
  @JsonbProperty("reqChnlId")
  public String reqChnlId;

  @Schema(
      required = true,
      example = "KE0010001",
      description = "req UnitID",
      enumeration = {"KE0010001", "TZ0010001", "UG0010001"})
  @JsonbProperty("reqUnitID")
  public String reqUnitID;

  @Schema(required = true, example = "TFA8090900198", description = "Req Transaction ReferenceNo")
  @JsonbProperty("reqTransactionReferenceNo")
  public String reqTransactionReferenceNo;

  @Schema(required = true, example = "1711460011", description = "req AccountNo")
  @JsonbProperty("DebitAccountNumber")
  public String reqAccountNo;

  @Schema(required = true, example = "KES", description = "req Debit Acc Currency")
  @JsonbProperty("DebitAccountCurrency")
  public String debitAccountCurrency;

  @Schema(required = true, example = "branch", description = "Cheque Delivery Category")
  @JsonbProperty("ChequeDeliveryCategory")
  public String chequeDeliveryCategory;

  @Schema(required = true, example = "50", description = "No of Leaves")
  @JsonbProperty("NOOfLeaves")
  public String noOfLeaves;

  @Schema(required = true, example = "2", description = "No of Cheque Books")
  @JsonbProperty("NoofChequeBooks")
  public String chequeBooks;

  @Schema(required = true, example = "123456", description = "Branch Number")
  @JsonbProperty("BranchNo")
  public String branchNo;

  @Schema(required = true, example = "", description = "User Preferred Address")
  @JsonbProperty("UserPreferredAddress")
  public String userPreferredAddress;

  @Schema(required = true, example = "", description = "Authorised Person Id Type")
  @JsonbProperty("AuthorizedPersonIDType")
  public String authorizedPersonIDtype;

  @Schema(required = true, example = "", description = "Authorised Person Id")
  @JsonbProperty("AuthorizedPersonID")
  public String authorizedPersonID;

  @Schema(required = true, example = "", description = "Authorised Person Name")
  @JsonbProperty("AuthorizedPersonName")
  public String authorizedPersonName;

  @Schema(required = true, example = "", description = "Authorised Person Email")
  @JsonbProperty("AuthorizedPersonEmail")
  public String authorizedPersonEmail;

  @Schema(required = true, example = "", description = "Authorised Person Mobile")
  @JsonbProperty("AuthorizedPersonMobile")
  public String authorizedPersonMobile;
}
