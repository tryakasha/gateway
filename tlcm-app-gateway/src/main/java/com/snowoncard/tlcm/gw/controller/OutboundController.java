package com.snowoncard.tlcm.gw.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.snowoncard.tlcm.gw.api.apple.PushNotificationResponse;
import com.snowoncard.tlcm.gw.api.model.type.PushNotificationResult;
import com.snowoncard.tlcm.gw.exception.TlcmGwException;
import com.snowoncard.tlcm.gw.service.APNsPushService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@ResponseBody
@RequestMapping(value = "/push", consumes = {"application/json; charset=UTF-8"}, produces = {"application/json; charset=UTF-8"})
public class OutboundController {

	private final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private APNsPushService apnsPushservice;

	@RequestMapping(value = "/sendNotification/{pushToken}", method = RequestMethod.POST)
	public ResponseEntity<?> pushTest(HttpServletRequest httpServletRequest, @RequestHeader HttpHeaders headers, HttpEntity<String> httpEntity,
			@PathVariable(name = "pushToken") String pushToken) {

		LOGGER.debug("Enter the Push Trigger API");
		LOGGER.debug("pushToken: " + pushToken);

		try {
			apnsPushservice.sendPushNotification(pushToken, false);
		} catch (TlcmGwException e) {
			LOGGER.error(e);
			return ResponseEntity.ok(setErrorResult(PushNotificationResult.FAILED, e.getErrorMessage()));
		} catch (Exception e){
			LOGGER.error(e);
			return ResponseEntity.ok(setErrorResult(PushNotificationResult.FAILED, "Unknown Error"));
		} catch (Throwable e){
			LOGGER.error(e);
			return ResponseEntity.ok(setErrorResult(PushNotificationResult.FAILED, "Unknown Error"));
		}
		return ResponseEntity.ok(new PushNotificationResponse());
	}
	
	private PushNotificationResponse setErrorResult(PushNotificationResult errorCode, String message){
		PushNotificationResponse response = new PushNotificationResponse();
		response.setResult(errorCode);
		response.setMessage(message);
		
		return response;
	}
}
