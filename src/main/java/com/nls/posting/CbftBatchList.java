package com.nls.posting;

import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;

public class CbftBatchList {

  /*
   * public static class BatchResponseHeaders{
   *
   * @JsonbProperty("res_File_RefNo") public String res_File_RefNo;
   *
   * @JsonbProperty("res_Batch_RefNo") public String res_Batch_RefNo;
   *
   * @JsonbProperty("res_No_Of_Txns") public String res_No_Of_Txns;
   *
   * @JsonbProperty("res_Error_Desc") public String res_Error_Desc="SUCCESS";
   *
   * @JsonbProperty("unitId") public String unitId;
   *
   * @JsonbProperty("res_Batch_Id") public String res_Batch_Id;
   *
   * @JsonbProperty("res_Batch_status") public String res_Batch_status;
   *
   * }
   */

  // @JsonbProperty("CBFT_BATCH_RESPONSE_HEADER")
  // private List<BatchResponseHeaders> batchResponseHeaders;

  @JsonbProperty("CBFT_BATCH_RESPONSE_DATA")
  private List<CbftBatchObject> batchResponseData;

  public void addAccount(CbftBatchObject object) {
    this.batchResponseData.add(object);
  }

  @JsonbProperty("CBFT_BATCH_RESPONSE_HEADER")
  private List<CbftBatchHeaderObject> batchResponseHeaders;

  public void addHeader(CbftBatchHeaderObject object) {
    this.batchResponseHeaders.add(object);
  }

  public CbftBatchList() {
    batchResponseData = new ArrayList<CbftBatchObject>();
    batchResponseHeaders = new ArrayList<CbftBatchHeaderObject>();
  }
}
