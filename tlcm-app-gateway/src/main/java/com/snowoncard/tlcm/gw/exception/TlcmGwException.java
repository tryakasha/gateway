package com.snowoncard.tlcm.gw.exception;

public class TlcmGwException extends Exception {

	private static final long serialVersionUID = 8052043831301754898L;

	private TlcmGwErrorType errorType;

	private TlcmGwErrorCode errorCode;

	private String errorMessage;

	private Throwable cause;

	public TlcmGwException(TlcmGwErrorCode errorCode, String errorMessage) {
		super(errorMessage);
		this.errorType = TlcmGwErrorType.INTERNAL;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public TlcmGwException(TlcmGwErrorCode errorCode, String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.errorType = TlcmGwErrorType.INTERNAL;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.cause = cause;

	}

	public TlcmGwException(TlcmGwErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorType = TlcmGwErrorType.INTERNAL;
		this.errorCode = errorCode;
		this.cause = cause;
	}


	/**
	 * @return the errorType
	 */
	public TlcmGwErrorType getErrorType() {
		return errorType;
	}

	/**
	 * @return the errorCode
	 */
	public TlcmGwErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @return the cause
	 */
	public Throwable getCause() {
		return cause;
	}


	@Override
	public String getMessage() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorType=");
		builder.append(errorType);
		builder.append(", ErrorCode=");
		builder.append(errorCode);
		if (errorType.equals(TlcmGwErrorType.INTERNAL)) {
			if (errorMessage != null) {
				builder.append(", errorMessage=");
				builder.append(errorMessage);
			}
			if (cause != null) {
				builder.append(", cause=");
				builder.append(cause.getMessage());
			}
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		String newLine = "\r\n";
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorType=");
		builder.append(errorType);
		builder.append(", ErrorCode=");
		builder.append(errorCode).append(newLine);
		if (errorType.equals(TlcmGwErrorType.INTERNAL)) {
			if (errorMessage != null) {
				builder.append(", errorMessage=");
				builder.append(errorMessage);
			}
			if (cause != null) {
				builder.append(", cause=");
				builder.append(cause.getMessage());
			}
		} 
		return builder.toString();
	}

}
