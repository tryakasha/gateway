package com.snowoncard.tlcm.gw.api.model;

public class TransactionDetail {
	/**
	 * Unique identifier for a transaction from the provider. Suggested length: 32-64 characters
	 * (GUID).
	 * <b>Required</b>
	 */
	private String identifier;
	
	/**
	 * The transaction identifier for a transaction,generated using the algorithms supplied in
	 * this specification.
	 * <b>Required</b>
	 */
	private String transactionIdentifier;
	
	/**
	 * This defines the type of the transaction. Current values are ‘Purchase’ and ‘Refund’
	 * <b>Required</b>
	 */
	private String transactionType;
	
	/**
	 * Device PAN Identifier
	 * <b>Conditional</b>
	 */
	private String dpanIdentifier;
	
	/**
	 * Funding PAN Identifier
	 * <b>Conditional</b>
	 */
	private String fpanIdentifier;
	
	/**
	 * The date of the transaction event. See Appendix C: Acceptable Date & Time Format.
	 * <b>Required</b>
	 */
	private String transactionDate;
	
	/**
	 * The currency of the transaction will be defined using ISO 4217 currency codes. If a
	 * code is not supplied, the device’s current locale currency code will be used.
	 * <b>Optional</b>
	 */
	private String currencyCode;
	
	/**
	 * This field should contain the original L1 value of the merchant, as seen on a customer’s bank
	 * statement.
	 * <b>Required</b>
	 */
	private String rawMerchantName;
	
	/**
	 * Name of the merchant with any server-side cleanup or a doing-business-as name. If no
	 * processing is applied, this value should match the rawMerchantName.
	 * <b>Required</b>
	 */
	private String merchantName;
	
	/**
	 * Transaction amount. Sign of transaction should match transaction type (i.e. + for purchase, - for refund)
	 * <b>Optional</b>
	 */
	private double amount;
	
	/**
	 * This will be used to track the authorization status of a transaction. Currently, the valid statuses are: ‘Pending’, ‘Approved’, ‘Refunded’,
	 * and ‘Declined’. Note: A transaction status of ‘Refunded’ should be used to indicate a voided transaction.
	 * <b>Required</b>
	 */
	private String transactionStatus;
	
	/**
	 * This will be used to describe what type of category the merchant falls into.
	 * <b>Optional</b>
	 */
	private String industryCategory;
	
	/**
	 * The industry category should be described using the ISO 18245 code, if possible.
	 * <b>Optional</b>
	 */
	private String idustryCode;
	
	/**
	 * If a transaction is no longer valid (i.e. it is canceled or revoked), supplying this property will remove it from the device’s history.
	 * <b>Optional</b>
	 */
	private boolean invalidated;
	
	/**
	 * New transactions (with a different identifier) that should completely replace an existing pending transaction can supply this property
	 * to indicate the device should use “softmatching” to perform a best-effort replacement.
	 * <b>Optional</b>
	 */
	private boolean completion;

	/** @return the identifier */
	public String getIdentifier() {
		return identifier;
	}

	/** @param identifier the identifier to set */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/** @return the transactionIdentifier */
	public String getTransactionIdentifier() {
		return transactionIdentifier;
	}

	/** @param transactionIdentifier the transactionIdentifier to set */
	public void setTransactionIdentifier(String transactionIdentifier) {
		this.transactionIdentifier = transactionIdentifier;
	}

	/** @return the transactionType */
	public String getTransactionType() {
		return transactionType;
	}

	/** @param transactionType the transactionType to set */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/** @return the dpanIdentifier */
	public String getDpanIdentifier() {
		return dpanIdentifier;
	}

	/** @param dpanIdentifier the dpanIdentifier to set */
	public void setDpanIdentifier(String dpanIdentifier) {
		this.dpanIdentifier = dpanIdentifier;
	}

	/** @return the fpanIdentifier */
	public String getFpanIdentifier() {
		return fpanIdentifier;
	}

	/** @param fpanIdentifier the fpanIdentifier to set */
	public void setFpanIdentifier(String fpanIdentifier) {
		this.fpanIdentifier = fpanIdentifier;
	}

	/** @return the transactionDate */
	public String getTransactionDate() {
		return transactionDate;
	}

	/** @param transactionDate the transactionDate to set */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	/** @return the currencyCode */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/** @param currencyCode the currencyCode to set */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/** @return the rawMerchantName */
	public String getRawMerchantName() {
		return rawMerchantName;
	}

	/** @param rawMerchantName the rawMerchantName to set */
	public void setRawMerchantName(String rawMerchantName) {
		this.rawMerchantName = rawMerchantName;
	}

	/** @return the merchantName */
	public String getMerchantName() {
		return merchantName;
	}

	/** @param merchantName the merchantName to set */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/** @return the amount */
	public double getAmount() {
		return amount;
	}

	/** @param amount the amount to set */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/** @return the transactionStatus */
	public String getTransactionStatus() {
		return transactionStatus;
	}

	/** @param transactionStatus the transactionStatus to set */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	/** @return the industryCategory */
	public String getIndustryCategory() {
		return industryCategory;
	}

	/** @param industryCategory the industryCategory to set */
	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	/** @return the idustryCode */
	public String getIdustryCode() {
		return idustryCode;
	}

	/** @param idustryCode the idustryCode to set */
	public void setIdustryCode(String idustryCode) {
		this.idustryCode = idustryCode;
	}

	/** @return the invalidated */
	public boolean isInvalidated() {
		return invalidated;
	}

	/** @param invalidated the invalidated to set */
	public void setInvalidated(boolean invalidated) {
		this.invalidated = invalidated;
	}

	/** @return the completion */
	public boolean isCompletion() {
		return completion;
	}

	/** @param completion the completion to set */
	public void setCompletion(boolean completion) {
		this.completion = completion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransactionDetail [identifier=");
		builder.append(identifier);
		builder.append(", transactionIdentifier=");
		builder.append(transactionIdentifier);
		builder.append(", transactionType=");
		builder.append(transactionType);
		builder.append(", dpanIdentifier=");
		builder.append(dpanIdentifier);
		builder.append(", fpanIdentifier=");
		builder.append(fpanIdentifier);
		builder.append(", transactionDate=");
		builder.append(transactionDate);
		builder.append(", currencyCode=");
		builder.append(currencyCode);
		builder.append(", rawMerchantName=");
		builder.append(rawMerchantName);
		builder.append(", merchantName=");
		builder.append(merchantName);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", transactionStatus=");
		builder.append(transactionStatus);
		builder.append(", industryCategory=");
		builder.append(industryCategory);
		builder.append(", idustryCode=");
		builder.append(idustryCode);
		builder.append(", invalidated=");
		builder.append(invalidated);
		builder.append(", completion=");
		builder.append(completion);
		builder.append("]");
		return builder.toString();
	}
}
