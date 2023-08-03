package com.snowoncard.tlcm.gw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@PropertySource(value="classpath:application.properties")
public class PropertyManager {
  private static final Logger LOGGER = LoggerFactory.getLogger(PropertyManager.class);

  //Transaction
  public static final String TRANSACTION_AUTH_TOKEN_EXPIRY_EPOCH_TIME = "transaction.auth.token.expiry";
  public static final String TRANSACTION_SIGNING_KEY_ID = "transaction.sign.key.id";
  public static final String TRANSACTION_SIGNING_KEY_PATH = "transaction.sign.key.path";
  public static final String TRANSACTION_TEAM_ID = "transaction.team.id";
  public static final String TRANSACTION_PUSH_TOPIC = "transaction.push.topic";
  
  //Pno
  public static final String PNO_SERVER_URL = "pno.server.url";
  public static final String VISA_SERVER_URL = "visa.server.url";
  public static final String MASTER_SERVER_URL = "master.server.url";
  public static final String AMEX_SERVER_URL = "amex.server.url";

  @Autowired
  private Environment env;

  public String getParameter(String paramName) {
    //String value = gtsProperties.getProperty(paramName);
    String value = env.getProperty(paramName);
    if (value != null) {
      value = value.trim();
    }
    return value;
  }

  public boolean getBooleanParameter(String paramName) {
    String value = getParameter(paramName);

    if (StringUtils.isEmpty(value)) {
      return false;
    }

    if ("TRUE".equalsIgnoreCase(value)) {
      return true;
    } else {
      return false;
    }
  }

  public long getLongParameter(String paramName) {
    String value =getParameter(paramName);

    if (StringUtils.isEmpty(value)) {
      LOGGER.warn("Parameter is empty. Key={}", new Object[] {paramName});
      return -1;
    }

    long result = -1;
    try {
      result = Long.parseLong(value);
    } catch (NumberFormatException e) {
      LOGGER.warn("Parameter Type is not Long. Key={}, Value={}, ErrorMsg={}", new Object[] {paramName, value, e.getMessage()});
    } catch (RuntimeException e) {
      LOGGER.error("Parameter Type is not Long. Key={}, Value={}, ErrorMsg={}", new Object[] {paramName, value, e.getMessage()});
    }

    return result;
  }

  public int getIntParameter(String paramName) {
    String value = getParameter(paramName);

    if (StringUtils.isEmpty(value)) {
      LOGGER.warn("Parameter is empty. Key={}", new Object[] {paramName});
      return -1;
    }

    int result = -1;
    try {
      result = Integer.parseInt(value);
    } catch (NumberFormatException e) {
      LOGGER.warn("Parameter Type is not int. Key={}, Value={}, ErrorMsg={}", new Object[] {paramName, value, e.getMessage()});
    } catch (RuntimeException e) {
      LOGGER.error("Parameter Type is not int. Key={}, Value={}, ErrorMsg={}", new Object[] {paramName, value, e.getMessage()});
    }

    return result;
  }
  
}
