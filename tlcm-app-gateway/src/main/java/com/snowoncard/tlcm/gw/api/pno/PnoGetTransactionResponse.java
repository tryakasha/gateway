package com.snowoncard.tlcm.gw.api.pno;

import java.util.List;

import com.snowoncard.tlcm.gw.api.model.TransactionDetail;

public class PnoGetTransactionResponse {
	
	private String tag;
	private String authenticationToken;
	private String appLaunchToken;
	private List<TransactionDetail> transactionDetails;
	
	/** @return the tag */
	public String getTag() {
		return tag;
	}
	/** @param tag the tag to set */
	public void setTag(String tag) {
		this.tag = tag;
	}
	/** @return the authenticationToken */
	public String getAuthenticationToken() {
		return authenticationToken;
	}
	/** @param authenticationToken the authenticationToken to set */
	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}
	/** @return the appLaunchToken */
	public String getAppLaunchToken() {
		return appLaunchToken;
	}
	/** @param appLaunchToken the appLaunchToken to set */
	public void setAppLaunchToken(String appLaunchToken) {
		this.appLaunchToken = appLaunchToken;
	}
	/** @return the transactionDetails */
	public List<TransactionDetail> getTransactionDetails() {
		return transactionDetails;
	}
	/** @param transactionDetails the transactionDetails to set */
	public void setTransactionDetails(List<TransactionDetail> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PnoGetTransactionResponse [tag=");
		builder.append(tag);
		builder.append(", authenticationToken=");
		builder.append(authenticationToken);
		builder.append(", appLaunchToken=");
		builder.append(appLaunchToken);
		builder.append(", transactionDetails=");
		builder.append(transactionDetails);
		builder.append("]");
		return builder.toString();
	}
}
