package com.snowoncard.tlcm.gw.api.pno;

public class PnoRegistrationResponse {
	
	private String authenticationToken;

	/** @return the authenticationToken */
	public String getAuthenticationToken() {
		return authenticationToken;
	}

	/** @param authenticationToken the authenticationToken to set */
	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PnoRegistrationResponse [authenticationToken=");
		builder.append(authenticationToken);
		builder.append("]");
		return builder.toString();
	}
	
}
