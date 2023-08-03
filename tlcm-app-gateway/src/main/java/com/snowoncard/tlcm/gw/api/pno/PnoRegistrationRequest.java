package com.snowoncard.tlcm.gw.api.pno;

import java.util.List;

import com.snowoncard.tlcm.gw.api.model.type.RegistrationActionType;

public class PnoRegistrationRequest {
	
	private String deviceId;
	private String dpanId;
	private List<String> dpanIdList;
	private String pushToken;
	private String accountHash;
	private RegistrationActionType actionType;
  /**
   * @return the deviceId
   */
  public String getDeviceId() {
    return deviceId;
  }
  /**
   * @param deviceId the deviceId to set
   */
  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }
  /**
   * @return the dpanId
   */
  public String getDpanId() {
    return dpanId;
  }
  /**
   * @param dpanId the dpanId to set
   */
  public void setDpanId(String dpanId) {
    this.dpanId = dpanId;
  }
  /**
   * @return the dpanIdList
   */
  public List<String> getDpanIdList() {
    return dpanIdList;
  }
  /**
   * @param dpanIdList the dpanIdList to set
   */
  public void setDpanIdList(List<String> dpanIdList) {
    this.dpanIdList = dpanIdList;
  }
  /**
   * @return the pushToken
   */
  public String getPushToken() {
    return pushToken;
  }
  /**
   * @param pushToken the pushToken to set
   */
  public void setPushToken(String pushToken) {
    this.pushToken = pushToken;
  }
  /**
   * @return the accountHash
   */
  public String getAccountHash() {
    return accountHash;
  }
  /**
   * @param accountHash the accountHash to set
   */
  public void setAccountHash(String accountHash) {
    this.accountHash = accountHash;
  }
  /**
   * @return the actionType
   */
  public RegistrationActionType getActionType() {
    return actionType;
  }
  /**
   * @param actionType the actionType to set
   */
  public void setActionType(RegistrationActionType actionType) {
    this.actionType = actionType;
  }
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PnoRegistrationRequest [deviceId=");
    builder.append(deviceId);
    builder.append(", dpanId=");
    builder.append(dpanId);
    builder.append(", dpanIdList=");
    builder.append(dpanIdList);
    builder.append(", pushToken=");
    builder.append(pushToken);
    builder.append(", accountHash=");
    builder.append(accountHash);
    builder.append(", actionType=");
    builder.append(actionType);
    builder.append("]");
    return builder.toString();
  }
	
}
