package com.snowoncard.tlcm.gw.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;


public class AbstractDirectChannel {
  
  final static Logger LOGGER = LoggerFactory.getLogger(LoggingRequestInterceptor.class);
  
  @Value("${restTemplate.factory.readTimeout:60000}")
  protected int readTimeout;

  @Value("${restTemplate.factory.connectTimeout:5000}")
  protected int connectTimeout;

  @Value("${restTemplate.httpClient.maxConnTotal:10}")
  protected int maxConnTotal;

  @Value("${restTemplate.httpClient.maxConnPerRoute:5}")
  protected int maxConnPerRoute;
  
  public RestTemplate getCustomRestTemplateForPno() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(clientHttpRequestFactory(maxConnTotal, maxConnPerRoute, readTimeout, connectTimeout)));
    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
    interceptors.add(new LoggingRequestInterceptor());
    restTemplate.setInterceptors(interceptors);
    //restTemplate.getMessageConverters().add(0, createMappingJacksonHttpMessageConverter());
    
    restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
    
    return restTemplate;
  }
  
  private ClientHttpRequestFactory clientHttpRequestFactory(int maxConnTotal, int maxConnPerRoute, int readTimeout, int connectTimeout) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
  	HttpClient httpClient = HttpClients.custom()
        .setMaxConnTotal(maxConnTotal)
        .setMaxConnPerRoute(maxConnPerRoute)
        .build();
    
  	HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
  	requestFactory.setHttpClient(httpClient);
    requestFactory.setReadTimeout(readTimeout);
    requestFactory.setConnectTimeout(connectTimeout);
    
    return requestFactory;
  }
  
  class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
      HttpStatus status = httpResponse.getStatusCode();
      
      if (!status.is2xxSuccessful()) {
        LOGGER.info("[CHANNEL] Http Response Status Code: " +  status.value());
      }      
      
      return false;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
      LOGGER.error("Never called");
    }
  }
  
  class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {
    
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
      traceRequest(request, body);
      ClientHttpResponse response = execution.execute(request, body);
      traceResponse(response);
      return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
      LOGGER.debug("===========================Http Request ==================================================");
      LOGGER.debug("URI          : {}", request.getURI());
      LOGGER.debug("Method       : {}", request.getMethod());
      LOGGER.debug("Headers      : {}", request.getHeaders());
      LOGGER.debug("Request body : {}", new String(body, "UTF-8"));
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
      StringBuilder inputStringBuilder = new StringBuilder();
      BufferedReader bufferedReader = null;
      try {
        bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
        String line = bufferedReader.readLine();
        while (line != null) {
          inputStringBuilder.append(line);
          inputStringBuilder.append('\n');
          line = bufferedReader.readLine();
        }
      } finally {
        if(bufferedReader != null) {
          bufferedReader.close();
        }
      }
      
      LOGGER.debug("===========================Http Response =================================================");
      LOGGER.debug("Status code  : {}", response.getStatusCode());
      LOGGER.debug("Status text  : {}", response.getStatusText());
      LOGGER.debug("Headers      : {}", response.getHeaders());
      LOGGER.debug("Response body: {}", inputStringBuilder.toString());
    }
  }
  

}
