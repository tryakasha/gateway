package com.snowoncard.tlcm.gw.api.apple;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.snowoncard.tlcm.gw.api.model.TransactionDetail;

public class TransactionResponse {
	
	/**
	 * A tag to be included in the next transaction notifications request. This tag can be used for filtering of transactions to only the subset that has not been delivered to
	 * the device. It is at the provider’s discretion as to the value of the tag. If a date value is desired for a lastUpdatedTag, such as the date a record is inserted or updated in a
	 * database, it is recommended that the value should be formatted as a a string representation of UNIX time, or the number of sections since epoch, e.g.
	 * “1409097080”. This will ensure correct formatting of the value when added as a component of a request URL.
	 */
	@JsonInclude(Include.NON_NULL)
	private String lastUpdatedTag;
	
	/**
	 * An optional inline ‘roll’ of the authentication token may be supplied, in lieu of a full re-registration. 
	 */
	@JsonInclude(Include.NON_NULL)
	private String authenticationToken;
	
	/**
	 * An optional token that will be included in the “deep link” URL passed to the issuer application from iOS when a transaction item is selected.* Suggested length: 32-64 characters.
	 */
	@JsonInclude(Include.NON_NULL)
	private String appLaunchToken;
	
	/**
	 * Array of TransactionDetails for the DPAN Identifier the using a supplied lastUpdatedTag for filtering.
	 */
	@JsonInclude(Include.NON_NULL)
	private List<TransactionDetail> transactionDetails;

	/** @return the lastUpdatedTag */
	public String getLastUpdatedTag() {
		return lastUpdatedTag;
	}

	/** @param lastUpdatedTag the lastUpdatedTag to set */
	public void setLastUpdatedTag(String lastUpdatedTag) {
		this.lastUpdatedTag = lastUpdatedTag;
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
		builder.append("TransactionResponse [lastUpdatedTag=");
		builder.append(lastUpdatedTag);
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
