package com.snowoncard.tlcm.gw.api.pno;

public class PnoGetTransactionRequest {
	private String deviceId;
	private String dpanId;
	private String authenticationToken;
	private String tag;
	/** @return the deviceId */
	public String getDeviceId() {
		return deviceId;
	}
	/** @param deviceId the deviceId to set */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/** @return the dpanId */
	public String getDpanId() {
		return dpanId;
	}
	/** @param dpanId the dpanId to set */
	public void setDpanId(String dpanId) {
		this.dpanId = dpanId;
	}
	/** @return the authenticationToken */
	public String getAuthenticationToken() {
		return authenticationToken;
	}
	/** @param authenticationToken the authenticationToken to set */
	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}
	/** @return the tag */
	public String getTag() {
		return tag;
	}
	/** @param tag the tag to set */
	public void setTag(String tag) {
		this.tag = tag;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PnoGetTransactionRequest [deviceId=");
		builder.append(deviceId);
		builder.append(", dpanId=");
		builder.append(dpanId);
		builder.append(", authenticationToken=");
		builder.append(authenticationToken);
		builder.append(", tag=");
		builder.append(tag);
		builder.append("]");
		return builder.toString();
	}
}
