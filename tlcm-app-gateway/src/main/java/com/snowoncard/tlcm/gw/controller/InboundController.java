package com.snowoncard.tlcm.gw.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.snowoncard.tlcm.gw.api.apple.RegistrationResponse;
import com.snowoncard.tlcm.gw.api.apple.TransactionResponse;
import com.snowoncard.tlcm.gw.exception.TlcmGwErrorCode;
import com.snowoncard.tlcm.gw.exception.TlcmGwException;
import com.snowoncard.tlcm.gw.service.TransactionService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@ResponseBody
@RequestMapping(value = "/tds/devices", /*consumes = { "application/json; charset=UTF-8" },*/ produces = { "application/json; charset=UTF-8" })
public class InboundController {

	private final Log LOGGER = LogFactory.getLog(getClass());
	private final static String AUTHENTICATION_TOKEN = "authorization";
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping(value = "/{deviceIdentifier}/registrations/dpan/{dpanIdentifier}", method = RequestMethod.POST)
	public ResponseEntity<?> register(HttpServletRequest httpServletRequest, @RequestHeader HttpHeaders headers, HttpEntity<String> httpEntity,
			@PathVariable(name = "deviceIdentifier") String deviceId,
			@PathVariable(name = "dpanIdentifier") String dpanId){
		String authToken = getAuthenticationToken(headers);
		String jsonBody = httpEntity.getBody();
		
		LOGGER.debug("Enter the Register API");
		LOGGER.debug("jsonBody : " + jsonBody);
		LOGGER.debug("authToken: " + authToken);
		LOGGER.debug("deviceId : " + deviceId);
		LOGGER.debug("dpanId   : " + dpanId);
		
		RegistrationResponse registrationResponse = null;
		try{
			registrationResponse = transactionService.registration(jsonBody, authToken, deviceId, dpanId);
		} catch (TlcmGwException e) {
			LOGGER.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (Exception e){
			LOGGER.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (Throwable t){
			LOGGER.error("Catched error" + t);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		LOGGER.debug("RegistrationResponse:" + registrationResponse);
		return ResponseEntity.ok(registrationResponse);
	}
	
	@RequestMapping(value = "/{deviceIdentifier}/registrations", method = RequestMethod.POST)
	public ResponseEntity<?> register(HttpServletRequest httpServletRequest, @RequestHeader HttpHeaders headers, HttpEntity<String> httpEntity,
			@PathVariable(name = "deviceIdentifier") String deviceId){
		String authToken = getAuthenticationToken(headers);
		String jsonBody = httpEntity.getBody();
		
		LOGGER.debug("Enter the Register APIv2");
		LOGGER.debug("jsonBody : " + jsonBody);
		LOGGER.debug("authToken: " + authToken);
		LOGGER.debug("deviceId : " + deviceId);
		
		RegistrationResponse registrationResponse = null;
		try{
			registrationResponse = transactionService.registration(jsonBody, authToken, deviceId, null);
		} catch (TlcmGwException e) {
			LOGGER.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (Exception e){
			LOGGER.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (Throwable t){
			LOGGER.error("Catched error" + t);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		LOGGER.debug("RegistrationResponse:" + registrationResponse);
		return ResponseEntity.ok(registrationResponse);
	}
	
	@RequestMapping(value = "/{deviceIdentifier}/dpan/{dpanIdentifier}/transactions", method = RequestMethod.GET)
	public ResponseEntity<?> getTransaction(HttpServletRequest httpServletRequest, @RequestHeader HttpHeaders headers, HttpEntity<String> httpEntity,
			@PathVariable(name = "deviceIdentifier") String deviceId,
			@PathVariable(name = "dpanIdentifier") String dpanId,
			@RequestParam(value = "tag", required = false) String tag){
		String authToken = getAuthenticationToken(headers);
		String jsonBody = httpEntity.getBody();
		
		LOGGER.debug("Enter the getTransaction API");
		LOGGER.debug("jsonBody : " + jsonBody);
		LOGGER.debug("authToken: " + authToken);
		LOGGER.debug("deviceId : " + deviceId);
		LOGGER.debug("dpanId   : " + dpanId);
		LOGGER.debug("tag      : " + tag);
		
		TransactionResponse transactionResponse = null;
		try{
			transactionResponse = transactionService.getTransaction(authToken, deviceId, dpanId, tag);
		} catch (TlcmGwException e) {
			LOGGER.error(e);
			if(e.getErrorCode() == TlcmGwErrorCode.AUTHENTICATION_ERROR){
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (Exception e){
			LOGGER.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (Throwable t){
			LOGGER.error("Catched error" + t);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		LOGGER.debug("TransactionResponse:" + transactionResponse);
		return ResponseEntity.ok(transactionResponse);
	}
	
	@RequestMapping(value = "/{deviceIdentifier}/transactions", method = RequestMethod.GET)
	public ResponseEntity<?> getTransaction(HttpServletRequest httpServletRequest, @RequestHeader HttpHeaders headers, HttpEntity<String> httpEntity,
			@PathVariable(name = "deviceIdentifier") String deviceId,
			@RequestParam(value = "tag", required = false) String tag){
		String authToken = getAuthenticationToken(headers);
		String jsonBody = httpEntity.getBody();
		
		LOGGER.debug("Enter the getTransaction APIv2");
		LOGGER.debug("jsonBody : " + jsonBody);
		LOGGER.debug("authToken: " + authToken);
		LOGGER.debug("deviceId : " + deviceId);
		LOGGER.debug("tag      : " + tag);
		
		TransactionResponse transactionResponse = null;
		try{
			transactionResponse = transactionService.getTransaction(authToken, deviceId, null, tag);
		} catch (TlcmGwException e) {
			LOGGER.error(e);
			if(e.getErrorCode() == TlcmGwErrorCode.AUTHENTICATION_ERROR){
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (Exception e){
			LOGGER.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (Throwable t){
			LOGGER.error("Catched error" + t);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		LOGGER.debug("TransactionResponse:" + transactionResponse);
		return ResponseEntity.ok(transactionResponse);
	}
	
	@RequestMapping(value = "/{deviceIdentifier}/pan/{dpanIdentifier}/transactions", method = RequestMethod.GET)
	public ResponseEntity<?> getTransactionV2(HttpServletRequest httpServletRequest, @RequestHeader HttpHeaders headers, HttpEntity<String> httpEntity,
			@PathVariable(name = "deviceIdentifier") String deviceId,
			@PathVariable(name = "dpanIdentifier") String dpanId,
			@RequestParam(value = "tag", required = false) String tag){
		String authToken = getAuthenticationToken(headers);
		String jsonBody = httpEntity.getBody();
		
		LOGGER.debug("Enter the getTransaction API");
		LOGGER.debug("jsonBody : " + jsonBody);
		LOGGER.debug("authToken: " + authToken);
		LOGGER.debug("deviceId : " + deviceId);
		LOGGER.debug("dpanId   : " + dpanId);
		LOGGER.debug("tag      : " + tag);
		
		TransactionResponse transactionResponse = null;
		try{
			transactionResponse = transactionService.getTransaction(authToken, deviceId, dpanId, tag);
		} catch (TlcmGwException e) {
			LOGGER.error(e);
			if(e.getErrorCode() == TlcmGwErrorCode.AUTHENTICATION_ERROR){
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (Exception e){
			LOGGER.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (Throwable t){
			LOGGER.error("Catched error" + t);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		LOGGER.debug("TransactionResponse:" + transactionResponse);
		return ResponseEntity.ok(transactionResponse);
	}
	
	private String getAuthenticationToken(HttpHeaders headers){
		String authToken = headers.getFirst(AUTHENTICATION_TOKEN);
		if(authToken != null){
			authToken = authToken.split(" ")[1];
		}
		
		return authToken;
	}
}
