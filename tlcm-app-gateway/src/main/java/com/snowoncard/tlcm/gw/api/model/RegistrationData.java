package com.snowoncard.tlcm.gw.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationData {
	
	/**
	 * APNs push token, supplied by the iOS device.
	 */
	private String pushToken;
	
	/**
	 * Used to identify a user by their Apple account. As this value is also provided during
	 * ‘Link and Provision’, is can be used during registration for validation purposes.
	 */
	private String accountHash;
	
	/**
	 * Multiple DPAN re-registration only. An array of all DPAN Identifiers associated with
	 * the request. If this value is not provided, it should be assumed that all previously
	 * registered DPAN Identifiers are being re-registered
	 */
	private List<String> dpanIdentifiers;

	/** @return the pushToken */
	public String getPushToken() {
		return pushToken;
	}

	/** @param pushToken the pushToken to set */
	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	/** @return the accountHash */
	public String getAccountHash() {
		return accountHash;
	}

	/** @param accountHash the accountHash to set */
	public void setAccountHash(String accountHash) {
		this.accountHash = accountHash;
	}

	/** @return the dpanIdentifiers */
	public List<String> getDpanIdentifiers() {
		return dpanIdentifiers;
	}

	/** @param dpanIdentifiers the dpanIdentifiers to set */
	public void setDpanIdentifiers(List<String> dpanIdentifiers) {
		this.dpanIdentifiers = dpanIdentifiers;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegistrationData [pushToken=");
		builder.append(pushToken);
		builder.append(", accountHash=");
		builder.append(accountHash);
		builder.append(", dpanIdentifiers=");
		builder.append(dpanIdentifiers);
		builder.append("]");
		return builder.toString();
	}
}
