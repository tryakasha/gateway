package com.snowoncard.tlcm.gw.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.eatthepath.pushy.apns.ApnsClient;
import com.eatthepath.pushy.apns.ApnsClientBuilder;
import com.eatthepath.pushy.apns.PushNotificationResponse;
import com.eatthepath.pushy.apns.auth.ApnsSigningKey;
import com.eatthepath.pushy.apns.util.SimpleApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification;
import com.eatthepath.pushy.apns.util.TokenUtil;
import com.eatthepath.pushy.apns.util.concurrent.PushNotificationFuture;
import com.snowoncard.tlcm.gw.exception.TlcmGwErrorCode;
import com.snowoncard.tlcm.gw.exception.TlcmGwException;

@Service
public class APNsPushService {
	private final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private PropertyManager propertyManager;

	private String	teamId;
	private String	keyId;
	private String	signingKeyPath;
	private String	pushTopic;

	private long				lastTokenGeneratedTime;
	private long				tokenExpiryEpochTime;
	private ApnsClient	apnsClient;
	@PostConstruct
	public void init() {
		lastTokenGeneratedTime = 0L;
		tokenExpiryEpochTime = propertyManager.getLongParameter(PropertyManager.TRANSACTION_AUTH_TOKEN_EXPIRY_EPOCH_TIME);
		teamId = propertyManager.getParameter(PropertyManager.TRANSACTION_TEAM_ID);
		keyId = propertyManager.getParameter(PropertyManager.TRANSACTION_SIGNING_KEY_ID);
		signingKeyPath = propertyManager.getParameter(PropertyManager.TRANSACTION_SIGNING_KEY_PATH);
		pushTopic = propertyManager.getParameter(PropertyManager.TRANSACTION_PUSH_TOPIC);
		try {
			apnsClient = generateAuthenticationToken();
		} catch (TlcmGwException e) {
			LOGGER.error("Failed to Builed ApnsClient", e);
		}
	}
	
	@PreDestroy
  public void cleanup() {
      if (apnsClient != null) {
      	apnsClient.close();
      }
  }

	public void sendPushNotification(String token, boolean retry) throws TlcmGwException {
		String pushToken = TokenUtil.sanitizeTokenString(token);
		String payload = new SimpleApnsPayloadBuilder().build();
		SimpleApnsPushNotification notification = new SimpleApnsPushNotification(pushToken, pushTopic, payload);

		// check token expiry
		if (System.currentTimeMillis() - lastTokenGeneratedTime > tokenExpiryEpochTime) {
			lastTokenGeneratedTime = System.currentTimeMillis();
			cleanup();
			apnsClient = generateAuthenticationToken();
		}
		// send push
		PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>> sendNotificationFuture = apnsClient.sendNotification(notification);

		try {
			PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse = sendNotificationFuture.get(10000, TimeUnit.MILLISECONDS);
			
			if (pushNotificationResponse.isAccepted()) {
				LOGGER.info("Notification accepted [pushToken: " + pushToken + "]");
			} else {
				LOGGER.error("Failed to Send Notification [pushToken: " + pushToken + "]");
				LOGGER.error("Notification rejected by the APNs gateway: " + pushNotificationResponse.getRejectionReason());
				if (pushNotificationResponse.getTokenInvalidationTimestamp().isPresent()) {

				  // 재발송 token 새로 생성 필요
					cleanup();
					apnsClient = generateAuthenticationToken();
					if (!retry) {
						sendPushNotification(token, true);
					} else {
						throw new TlcmGwException(TlcmGwErrorCode.PUSH_NOTIFICATION_ERROR, "Invalid Authentication Token");
					}
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			throw new TlcmGwException(TlcmGwErrorCode.PUSH_NOTIFICATION_ERROR, "Failed to Send Notification for Unknown Reason", e);
		} catch (TimeoutException e) {
		  // 메모리 누수 처리(10초가 지나면 응답을 기다리지 않고 취소 처리)
		  sendNotificationFuture.cancel(true);
    }
	}

	private ApnsClient generateAuthenticationToken() throws TlcmGwException {
		ApnsClient apnsClient = null;
		try {
			apnsClient = new ApnsClientBuilder()
					.setApnsServer(ApnsClientBuilder.PRODUCTION_APNS_HOST)
					.setSigningKey(ApnsSigningKey.loadFromPkcs8File(new ClassPathResource(signingKeyPath).getFile(), teamId, keyId))
					.build();
		} catch (InvalidKeyException | NoSuchAlgorithmException | IOException e) {
			throw new TlcmGwException(TlcmGwErrorCode.PUSH_NOTIFICATION_ERROR, "Failed to Builed ApnsClient", e);
		}
		LOGGER.debug("ApnsClient Generated for Push Notification");
		LOGGER.debug("Team ID         : " + teamId);
		LOGGER.debug("Key ID          : " + keyId);
		return apnsClient;
	}
}
