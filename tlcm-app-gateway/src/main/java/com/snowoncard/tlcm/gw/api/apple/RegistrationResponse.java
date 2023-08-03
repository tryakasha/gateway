package com.snowoncard.tlcm.gw.api.apple;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RegistrationResponse {
	
	/**
	 * Token used in authorization header to get transaction details for this device.
	 * Suggested length: 32-64 characters.
	 */
	@JsonInclude(Include.NON_NULL)
	private String authenticationToken;
	
	/**
	 * Transaction notification services only. The transaction service supports settlement of
	 * transactions from ‘Pending’ to a settled state, such as ‘Approved’.
	 */
	@JsonInclude(Include.NON_NULL)
	private String supportsSettlement;

	/** @return the authenticationToken */
	public String getAuthenticationToken() {
		return authenticationToken;
	}

	/** @param authenticationToken the authenticationToken to set */
	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	/** @return the supportsSettlement */
	public String getSupportsSettlement() {
		return supportsSettlement;
	}

	/** @param supportsSettlement the supportsSettlement to set */
	public void setSupportsSettlement(String supportsSettlement) {
		this.supportsSettlement = supportsSettlement;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegisterResponse [authenticationToken=");
		builder.append(authenticationToken);
		builder.append(", supportsSettlement=");
		builder.append(supportsSettlement);
		builder.append("]");
		return builder.toString();
	}
}
