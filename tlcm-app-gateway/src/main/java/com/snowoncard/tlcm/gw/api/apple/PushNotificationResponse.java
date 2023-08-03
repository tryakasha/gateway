package com.snowoncard.tlcm.gw.api.apple;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.snowoncard.tlcm.gw.api.model.type.PushNotificationResult;

public class PushNotificationResponse {
	private PushNotificationResult result;
	@JsonInclude(Include.NON_NULL)
	private String message;
	
	public PushNotificationResponse(){
		this.result = PushNotificationResult.SUCCESS; 
	}
	/** @return the result */
	public PushNotificationResult getResult() {
		return result;
	}
	/** @param result the result to set */
	public void setResult(PushNotificationResult result) {
		this.result = result;
	}
	/** @return the message */
	public String getMessage() {
		return message;
	}
	/** @param message the message to set */
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PushNotificationResult [result=");
		builder.append(result);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
}
