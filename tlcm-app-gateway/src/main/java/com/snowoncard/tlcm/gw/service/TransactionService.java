package com.snowoncard.tlcm.gw.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowoncard.tlcm.gw.api.apple.RegistrationRequest;
import com.snowoncard.tlcm.gw.api.apple.RegistrationResponse;
import com.snowoncard.tlcm.gw.api.apple.TransactionResponse;
import com.snowoncard.tlcm.gw.api.model.RegistrationData;
import com.snowoncard.tlcm.gw.api.model.type.RegistrationActionType;
import com.snowoncard.tlcm.gw.api.pno.PnoGetTransactionRequest;
import com.snowoncard.tlcm.gw.api.pno.PnoGetTransactionResponse;
import com.snowoncard.tlcm.gw.api.pno.PnoRegistrationRequest;
import com.snowoncard.tlcm.gw.api.pno.PnoRegistrationResponse;
import com.snowoncard.tlcm.gw.exception.TlcmGwErrorCode;
import com.snowoncard.tlcm.gw.exception.TlcmGwException;

@Service
public class TransactionService {
	
	private final Log LOGGER = LogFactory.getLog(getClass());
	
	private ObjectMapper objectMapper;
	
	@Autowired
	private PnoChannel pnoChannel;
	
	@PostConstruct
	public void init(){
		objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	
	public RegistrationResponse registration(String jsonBody, String authToken, String deviceId, String dpanId) throws TlcmGwException{
		
		RegistrationRequest registrationRequest = null;
		
		try {
			registrationRequest = objectMapper.readValue(jsonBody, RegistrationRequest.class);
		} catch (IOException e) {
			throw new TlcmGwException(TlcmGwErrorCode.JSON_PROCESSING_ERROR, "Failed to generate JSON Body", e);
		}
		
		String base64decodedRegistrationData = new String(Base64.getDecoder().decode(registrationRequest.getRegistrationData()));
		RegistrationData registrationData = null;
		try {
			registrationData = objectMapper.readValue(base64decodedRegistrationData, RegistrationData.class);
		} catch (IOException e) {
			throw new TlcmGwException(TlcmGwErrorCode.JSON_PROCESSING_ERROR, "Failed to generate JSON Body", e);
		}
		RegistrationActionType registrationActionType = checkRegistrationType(registrationRequest, authToken, registrationData.getDpanIdentifiers());
		
		PnoRegistrationRequest pnoRegistrationRequest = new PnoRegistrationRequest();
		pnoRegistrationRequest.setActionType(registrationActionType);
		pnoRegistrationRequest.setDeviceId(deviceId);
		pnoRegistrationRequest.setDpanId(dpanId);
		pnoRegistrationRequest.setPushToken(registrationData.getPushToken());
		
		if(registrationData.getDpanIdentifiers() != null){
			pnoRegistrationRequest.setDpanIdList(registrationData.getDpanIdentifiers());
		}
		
		LOGGER.debug("Registration Request: " + pnoRegistrationRequest);
		// channel call
		PnoRegistrationResponse pnoRegistrationResponse = pnoChannel.registration(pnoRegistrationRequest); // cahnnel
		LOGGER.debug("Registration Respnose: " + pnoRegistrationResponse);
		
		RegistrationResponse registrationResponse = new RegistrationResponse();
		registrationResponse.setAuthenticationToken(pnoRegistrationResponse.getAuthenticationToken());
		
		return registrationResponse;
	}
	
	public TransactionResponse getTransaction(String authToken, String deviceId, String dpanId, String tag) throws TlcmGwException {

		PnoGetTransactionRequest pnoGetTransactionRequest = new PnoGetTransactionRequest();
		pnoGetTransactionRequest.setAuthenticationToken(authToken);
		pnoGetTransactionRequest.setDeviceId(deviceId);
		pnoGetTransactionRequest.setDpanId(dpanId);
		pnoGetTransactionRequest.setTag(tag);
		
		// channel call
		LOGGER.debug("Registration Request: " + pnoGetTransactionRequest);
		LOGGER.debug("Transaction Request: " + pnoGetTransactionRequest);
		PnoGetTransactionResponse pnoGetTransactionResponse = pnoChannel.getTransaction(pnoGetTransactionRequest);
		LOGGER.debug("Transaction Response: " + pnoGetTransactionResponse);
		
		TransactionResponse transactionResponse = new TransactionResponse();
		transactionResponse.setLastUpdatedTag(pnoGetTransactionResponse.getTag());
		transactionResponse.setAppLaunchToken(pnoGetTransactionResponse.getAppLaunchToken());
		transactionResponse.setTransactionDetails(pnoGetTransactionResponse.getTransactionDetails());
		transactionResponse.setAuthenticationToken(pnoGetTransactionResponse.getAuthenticationToken());
		
		return transactionResponse;
	}
	
	private RegistrationActionType checkRegistrationType(RegistrationRequest registrationRequest, String authToken, List<String> dpanIdentifiers){
		if(registrationRequest == null){
			return RegistrationActionType.DE_REGISTER;
		}
		String registrationData = registrationRequest.getRegistrationData();
		if(registrationData == null){
			return RegistrationActionType.DE_REGISTER;
		}
		if(authToken != null && dpanIdentifiers != null){
			return RegistrationActionType.RE_REGISTER;
		}
		return RegistrationActionType.REGISTER;
	}
	
}
