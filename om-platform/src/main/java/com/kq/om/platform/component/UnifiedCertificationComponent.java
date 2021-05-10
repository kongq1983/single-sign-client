package com.kq.om.platform.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kq
 * @date 2021-05-08 10:52
 * @since 2020-0630
 */
@Component
public class UnifiedCertificationComponent {

    protected static Logger logger = LoggerFactory.getLogger(UnifiedCertificationComponent.class);

    public static final String SUCCESS_CODE = "200";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${unified.certification.appId}")
    private String appId;

    @Value("${unified.certification.appSecret}")
    private String appSecret;

    /** 统一门户 */
    @Value("${unified.certification.portal.url}")
    private String unifiedPortalUrl;

    /** 统一认证中心 */
    @Value("${unified.certification.ca.url}")
    private String unifiedCaUrl;

    @Value("${login.url}")
    private String loginUrl;



    public String getCertificationPortalLoginUrl(){

        String url = "%s?oauthClientId=%s&redirectUrl=%s";
        String forwardUrl =  String.format(url,unifiedPortalUrl,appId,loginUrl);
        logger.debug("forwardUrl={}",forwardUrl);
        return forwardUrl;
    }

    /**
     * 获取token
     * @return
     */
    public String getTokenUrl(){
        String url =  unifiedCaUrl+"/uias/token";
        return url;
    }

    /**
     * 验证token
     * @return
     */
    public String getCheckTokenUrl(){
        String url =  unifiedCaUrl+"/uias/token/result";
        return url;
    }


    public String getToken(String uiasSession,String code) throws Exception{

        String url = "%s?code=%s&clientId=%s&clientSecret=%s";

        String requestUrl = String.format(url,this.getTokenUrl(),code,appId,appSecret);


//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUrl,String.class);

        List<String> cookies = getCookies(uiasSession);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(HttpHeaders.COOKIE, cookies);
        // 注意 如果params.size=0，不要传 直接传null
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, httpEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        String response = responseEntity.getBody();
        logger.debug("获取token返回值={}",response);

        JsonNode jsonNode = objectMapper.readTree(response);

        String resultCode = jsonNode.get("code").asText();

        if(resultCode.equals(SUCCESS_CODE)) { // 成功
            String token = jsonNode.get("data").get("value").asText();
            logger.debug("成功获取token={}",token);
            return token;
        } else {
            String msg = jsonNode.get("msg").asText();
            logger.error("获取token失败 response={}",response);

            throw new Exception(msg);
        }

    }

    private List<String> getCookies(String uiasSession) {
        List<String> cookies = new ArrayList<>();
        cookies.add("SESSION=" + uiasSession);
        cookies.add("Path=/");
        return cookies;
    }


    public boolean checkToken(String uiasSession,String token) throws Exception{

        String url = "%s?token=%s";

        String requestUrl = String.format(url,this.getCheckTokenUrl(),token);

//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUrl,String.class);

        List<String> cookies = getCookies(uiasSession);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(HttpHeaders.COOKIE, cookies);
        // 注意 如果params.size=0，不要传 直接传null
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, httpEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        String response = responseEntity.getBody();
        logger.debug("获取验证token返回值={}",response);

        JsonNode jsonNode = objectMapper.readTree(response);

        String resultCode = jsonNode.get("code").asText();

        if(resultCode.equals(SUCCESS_CODE)) { // 成功
            String result = jsonNode.get("data").asText();
            return Boolean.valueOf(result);
        } else {
//            String msg = jsonNode.get("msg").asText();
            logger.error("获取token失败 response={}",response);
        }

        return  false;

    }





}
