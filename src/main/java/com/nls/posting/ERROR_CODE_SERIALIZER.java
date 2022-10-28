package com.nls.posting;

import javax.json.bind.adapter.JsonbAdapter;

public class ERROR_CODE_SERIALIZER implements JsonbAdapter<ERROR_CODE, String> {

  @Override
  public ERROR_CODE adaptFromJson(String errorCode) throws Exception {
    return ERROR_CODE.valueOf(errorCode);
  }

  @Override
  public String adaptToJson(ERROR_CODE errorCode) throws Exception {
    return errorCode.getValue();
  }
}
