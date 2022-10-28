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
  "fTRef",
  "amount",
  "receiver",
  "msgType",
  "channelRef",
  "channelID",
  "bulk",
  "name",
  "channelName",
  "senderMobile",
  "business"
})
public class MobilePayloadCoreRequest {
  @Schema(required = true, example = "3403310014", description = "Account")
  @JsonbProperty("Account")
  public String account;

  @Schema(required = true, example = "340331", description = "Client Base")
  @JsonbProperty("ClientBase")
  public String clientBase;

  @Schema(required = true, example = "FTC220802GUFG", description = "fTRef")
  @JsonbProperty("FTRef")
  public String fTRef;

  @Schema(required = true, example = "95100", description = "Amount")
  @JsonbProperty("Amount")
  public String amount;

  @Schema(required = true, example = "OTHER", description = "Receiver")
  @JsonbProperty("Receiver")
  public String receiver;

  @Schema(required = true, example = "MP", description = "Msg Type")
  @JsonbProperty("MsgType")
  public String msgType;

  @Schema(required = true, example = "220802GUFG", description = "Channel Ref")
  @JsonbProperty("ChannelRef")
  public String channelRef;

  @Schema(required = true, example = "3", description = "Channel ID")
  @JsonbProperty("channelID")
  public int channelID;

  @Schema(required = true, example = "254718221266", description = "Mobile")
  @JsonbProperty("Mobile")
  public String mobile;

  @Schema(required = true, example = "false", description = "Bulk")
  @JsonbProperty("Bulk")
  public boolean bulk;

  @Schema(required = true, example = "FRANKLIN KITHINJI LONGO", description = "Name")
  @JsonbProperty("Name")
  public String name;

  @Schema(required = true, example = "CM", description = "Channel Name")
  @JsonbProperty("ChannelName")
  public String channelName;

  @Schema(required = true, example = "254726738472", description = "Sender Mobile")
  @JsonbProperty("SenderMobile")
  public String senderMobile;

  @Schema(required = true, example = "RETAIL", description = "Business")
  @JsonbProperty("Business")
  public String business;

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

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getMsgType() {
    return msgType;
  }

  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  public String getChannelRef() {
    return channelRef;
  }

  public void setChannelRef(String channelRef) {
    this.channelRef = channelRef;
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

  public boolean getBulk() {
    return bulk;
  }

  public void setBulk(boolean bulk) {
    this.bulk = bulk;
  }

  public String getName() {
    return name;
  }

  public void setName(String Name) {
    name = Name;
  }

  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }

  public String getSenderMobile() {
    return senderMobile;
  }

  public void setSenderMobile(String senderMobile) {
    this.senderMobile = senderMobile;
  }

  public String getBusiness() {
    return business;
  }

  public void setBusiness(String business) {
    this.business = business;
  }

  public MobilePayloadCoreRequest(
      String account,
      String clientBase,
      String fTRef,
      String amount,
      String receiver,
      String msgType,
      String channelRef,
      int channelID,
      String mobile,
      boolean bulk,
      String name,
      String channelName,
      String senderMobile,
      String business) {
    this.account = account;
    this.clientBase = clientBase;
    this.fTRef = fTRef;
    this.amount = amount;
    this.receiver = receiver;
    this.msgType = msgType;
    this.channelRef = channelRef;
    this.channelID = channelID;
    this.mobile = mobile;
    this.bulk = bulk;
    this.name = name;
    this.channelName = channelName;
    this.senderMobile = senderMobile;
    this.business = business;
  }
}
