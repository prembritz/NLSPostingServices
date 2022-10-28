package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"lockvalidTill", "lockTime", "lockExists", "lockSuccessful"})
public class LockTrasactionObject {

  @JsonbProperty("lockValidTill")
  private String lockvalidTill;

  @JsonbProperty("lockTime")
  private String lockTime;

  @JsonbProperty("lockExists")
  private String lockExists;

  @JsonbProperty("lockSuccessful")
  private String lockSuccessful;

  public LockTrasactionObject() {

    this.setLockvalidTill(lockvalidTill);
    this.setLockTime(lockTime);
    this.setLockExists(lockExists);
    this.setLockSuccessful(lockSuccessful);
  }

  public String getLockvalidTill() {
    return lockvalidTill;
  }

  public void setLockvalidTill(String lockvalidTill) {
    this.lockvalidTill = lockvalidTill;
  }

  public String getLockTime() {
    return lockTime;
  }

  public void setLockTime(String lockTime) {
    this.lockTime = lockTime;
  }

  public String getLockExists() {
    return lockExists;
  }

  public void setLockExists(String lockExists) {
    this.lockExists = lockExists;
  }

  public String getLockSuccessful() {
    return lockSuccessful;
  }

  public void setLockSuccessful(String lockSuccessful) {
    this.lockSuccessful = lockSuccessful;
  }
}
