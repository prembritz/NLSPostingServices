package com.nls.core;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
    name = "MobilePayloadCoreRequest",
    description = "Request object with Account , clientBase ...etc")
@JsonbPropertyOrder({
  "account",
  "clientBase",
  "channelName",
  "fTRef",
  "amount",
  "msgType",
  "channelID",
  "senderMobile",
  "business",
  "name"
})
public class MobileAirtimeCoreRequest {
  @Schema(required = true, example = "3403310014", description = "Account")
  @JsonbProperty("Account")
  public String account;

  @Schema(required = true, example = "340331", description = "Client Base")
  @JsonbProperty("ClientBase")
  public String clientBase;

  @Schema(required = true, example = "CM", description = "Channel Name")
  @JsonbProperty("ChannelName")
  public String channelName;

  @Schema(required = true, example = "FTC220802GUFG", description = "fTRef")
  @JsonbProperty("FTRef")
  public String fTRef;

  @Schema(required = true, example = "95100", description = "Amount")
  @JsonbProperty("Amount")
  public String amount;

  @Schema(required = true, example = "MP", description = "Msg Type")
  @JsonbProperty("MsgType")
  public String msgType;

  @Schema(required = true, example = "3", description = "Channel ID")
  @JsonbProperty("channelID")
  public int channelID;

  @Schema(required = true, example = "254718221266", description = "Mobile")
  @JsonbProperty("Mobile")
  public String mobile;

  @Schema(required = true, example = "FRANKLIN KITHINJI LONGO", description = "Name")
  @JsonbProperty("Name")
  public String name;

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getClientBase() {
    return clientBase;
  }

  public void setClientBase(String clientBase) {
    this.clientBase = clientBase;
  }

  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }

  public String getfTRef() {
    return fTRef;
  }

  public void setfTRef(String fTRef) {
    this.fTRef = fTRef;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getMsgType() {
    return msgType;
  }

  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  public int getChannelID() {
    return channelID;
  }

  public void setChannelID(int channelID) {
    this.channelID = channelID;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MobileAirtimeCoreRequest(
      String account,
      String clientBase,
      String channelName,
      String fTRef,
      String amount,
      String msgType,
      int channelID,
      String mobile,
      String name) {
    this.account = account;
    this.clientBase = clientBase;
    this.channelName = channelName;
    this.fTRef = fTRef;
    this.amount = amount;
    this.msgType = msgType;
    this.channelID = channelID;
    this.mobile = mobile;
    this.name = name;
  }
}
