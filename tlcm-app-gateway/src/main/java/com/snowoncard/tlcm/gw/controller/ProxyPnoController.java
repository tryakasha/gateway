package com.snowoncard.tlcm.gw.controller;

import com.snowoncard.tlcm.gw.service.PropertyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@CrossOrigin(origins = "*", maxAge = 3600)  //삭제예정
@RestController
@RequestMapping(value = "/proxy", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProxyPnoController {

  @Autowired
  private PropertyManager propertyManager;

  @RequestMapping(value ="/{pnoType}/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
  public ResponseEntity<byte[]> proxy(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) byte[] body, @PathVariable("pnoType") String pnoType) throws
      IOException, URISyntaxException {

    HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    httpRequestFactory.setConnectionRequestTimeout(360000); //6분
    httpRequestFactory.setConnectTimeout(360000);           //6분
    httpRequestFactory.setReadTimeout(360000);              //6분

    String pnoUrl;
    switch (pnoType) {
      case "V":
        pnoUrl = propertyManager.getParameter(PropertyManager.VISA_SERVER_URL);
        break;
      case "M":
        pnoUrl = propertyManager.getParameter(PropertyManager.MASTER_SERVER_URL);
        break;
      case "A":
        pnoUrl = propertyManager.getParameter(PropertyManager.AMEX_SERVER_URL);
        break;
      default:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(("Invalid PNO type: " + pnoType).getBytes(StandardCharsets.UTF_8));
    }

    RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
    String originReqURL = request.getRequestURI().replaceAll("^/proxy/" + pnoType, "");
    String originQueryString = request.getQueryString();
    String urlStr = pnoUrl + originReqURL + (StringUtils.isEmpty(originQueryString) ? "" : "?"+originQueryString);

    //FIXME JC AMEX인 경우 SSL설정?? (Two-way SSL)
    URI url = new URI(urlStr);

//    String originMethod = request.getHeader("x-origin-method");
    HttpMethod method = HttpMethod.valueOf(request.getMethod());

    Enumeration<String> headerNames = request.getHeaderNames();
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    //FIXME JC header 정보 확인필요... 403 forbidden
    while(headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      String headerValue = request.getHeader(headerName);

      headers.add(headerName, headerValue);
    }

    HttpEntity<byte[]> httpEntity = new HttpEntity<>(body, headers);

    // FIXME JC error일 경우 다른 처리 필요? (테스트 필요)
    return restTemplate.exchange(url, method, httpEntity, byte[].class);
  }

}
