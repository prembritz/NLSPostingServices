package com.nls.posting;

public enum ERROR_CODE {
  SUCCESSFUL("000"),
  NOT_FOUND("111"),
  TIMED_OUT("222");

  private String value;

  ERROR_CODE(String value) {
    this.setValue(value);
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
