package com.snowoncard.tlcm.gw.api.apple;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequest {

	/**
	 * Base64 encoded UTF-8 bytes of registration data JSON dictionary.
	 */
	private String registrationData;
	
	/**
	 * Base64 encoded bytes of INTERNAL AUTHENTICATE APDU response
	 */
	private String signature;
	
	/**
	 * Hex encoded CASD certificate
	 */
	private String casdCertificate;

	/** @return the registrationData */
	public String getRegistrationData() {
		return registrationData;
	}

	/** @param registrationData the registrationData to set */
	public void setRegistrationData(String registrationData) {
		this.registrationData = registrationData;
	}

	/** @return the signature */
	public String getSignature() {
		return signature;
	}

	/** @param signature the signature to set */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/** @return the casdCertificate */
	public String getCasdCertificate() {
		return casdCertificate;
	}

	/** @param casdCertificate the casdCertificate to set */
	public void setCasdCertificate(String casdCertificate) {
		this.casdCertificate = casdCertificate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegisterRequest [registrationData=");
		builder.append(registrationData);
		builder.append(", signature=");
		builder.append(signature);
		builder.append(", casdCertificate=");
		builder.append(casdCertificate);
		builder.append("]");
		return builder.toString();
	}
}
