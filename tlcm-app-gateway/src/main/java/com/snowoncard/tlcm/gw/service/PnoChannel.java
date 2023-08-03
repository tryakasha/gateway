package com.snowoncard.tlcm.gw.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.snowoncard.tlcm.gw.api.pno.PnoGetTransactionRequest;
import com.snowoncard.tlcm.gw.api.pno.PnoGetTransactionResponse;
import com.snowoncard.tlcm.gw.api.pno.PnoRegistrationRequest;
import com.snowoncard.tlcm.gw.api.pno.PnoRegistrationResponse;
import com.snowoncard.tlcm.gw.exception.TlcmGwErrorCode;
import com.snowoncard.tlcm.gw.exception.TlcmGwException;

@Service
public class PnoChannel extends AbstractDirectChannel {

	final static Logger LOGGER = LoggerFactory.getLogger(PnoChannel.class);
	final static String PNO_RESOURCEPATH_REGISTRATION = "/devices/registration";
	final static String PNO_RESOURCEPATH_GET_TRANSACTION = "/devices/getTransaction";
	@Autowired
	private PropertyManager propertyManager;

	private RestTemplate restTemplate;
	
	private String pnoUrl;

	@PostConstruct
	public void init() {
		pnoUrl = propertyManager.getParameter(PropertyManager.PNO_SERVER_URL);
		try {
			this.restTemplate = getCustomRestTemplateForPno();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			throw new RuntimeException("Fail to load RestTemplate for Visa", e);
		}
	}

	public PnoRegistrationResponse registration(PnoRegistrationRequest request) throws TlcmGwException {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(pnoUrl).path(PNO_RESOURCEPATH_REGISTRATION);
		UriComponents uriComponents = uriBuilder.build();

		ResponseEntity<PnoRegistrationResponse> response = restTemplate.postForEntity(uriComponents.toString(), request, PnoRegistrationResponse.class);
		HttpStatus status = response.getStatusCode();
		if (!status.is2xxSuccessful()) {
			LOGGER.info(String.format("[PNO_CHANNEL] REGISTRATION REQUEST REQ: %-28s  HTTP RES: %3d ", pnoUrl, status.value()));
			throw new TlcmGwException(TlcmGwErrorCode.UNKNOWN_INTERNAL_ERROR, "Failed to Registration Device Info");
		}

		return response.getBody();
	}
	public PnoGetTransactionResponse getTransaction(PnoGetTransactionRequest request) throws TlcmGwException {
		
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(pnoUrl).path(PNO_RESOURCEPATH_GET_TRANSACTION);
		UriComponents uriComponents = uriBuilder.build();
		
		ResponseEntity<PnoGetTransactionResponse> response = restTemplate.postForEntity(uriComponents.toString(), request, PnoGetTransactionResponse.class);
		HttpStatus status = response.getStatusCode();
		if (status.is4xxClientError()) {
			LOGGER.info(String.format("[PNO_CHANNEL] GET TRANSACTION REQUEST REQ: %-28s  HTTP RES: %3d ", pnoUrl, status.value()));
			throw new TlcmGwException(TlcmGwErrorCode.AUTHENTICATION_ERROR, "Failed to Verify Authentication Token");
		}
		if (!status.is2xxSuccessful()) {
			LOGGER.info(String.format("[PNO_CHANNEL] GET TRANSACTION REQUEST REQ: %-28s  HTTP RES: %3d ", pnoUrl, status.value()));
			throw new TlcmGwException(TlcmGwErrorCode.INVALID_PARAMETER, "Failed to Get Transaction Data By PNO Server");
		}
		return response.getBody();
	}
}
