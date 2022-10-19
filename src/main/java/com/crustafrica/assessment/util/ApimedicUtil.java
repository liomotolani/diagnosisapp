package com.crustafrica.assessment.util;


import com.crustafrica.assessment.dto.PrialdLoginDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApimedicUtil {

    @Value("priald.api_key")
    private String apiKey;

    @Value("priald.secret_key")
    private String secretKey;


    @Value("priald.token")
    private String token;

//    private final Map<String, String> token = new HashMap<>();


    @NonNull
    private final RestTemplate restTemplate;

    @SneakyThrows
    public Object getAccessToken(){
        try {
            String url = "https://sandbox-authservice.priaid.ch/login";
            String hashedKey = getMD5("f9DJb2s8K7Tdz6WGw"+url);
            HttpEntity entity = new HttpEntity<>(getHttpHeadersUsingSecretKey(hashedKey));
            ResponseEntity<Object> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    Object.class,
                    1
            );
            log.info("Response body {}", responseEntity.getStatusCode());
            return responseEntity.getBody();
        }catch (Exception clientErrorException){
            log.debug("Priald login endpoint returned {}", clientErrorException.getMessage());
        }
        return null;
    }

    @SneakyThrows
    public Object getDiagnosisResult(List<Integer> symptoms, String gender, int yearOfBirth){
        try {
            JSONArray jsonSymptoms = new JSONArray(symptoms);
            String token = getToken();
            HttpEntity entity = new HttpEntity<>(getHttpHeadersUsingToken(token));
            String url = "https://sandbox-healthservice.priaid.ch/diagnosis?" +
                    "token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImxpZ2FsaW9tb3RvbGFuaUBnbWF" +
                    "pbC5jb20iLCJyb2xlIjoiVXNlciIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUv" +
                    "MDUvaWRlbnRpdHkvY2xhaW1zL3NpZCI6IjExMjk1IiwiaHR0cDovL3NjaGVtYXMubWljc" +
                    "9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy92ZXJzaW9uIjoiMjAwI" +
                    "iwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9saW1pdCI6Ijk5OTk5OTk5OSIsIm" +
                    "h0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbWVtYmVyc2hpcCI6IlByZW1pdW0i" +
                    "LCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL2xhbmd1YWdlIjoiZW4tZ2IiLCJodHRwOi8vc2N" +
                    "oZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL2V4cGl" +
                    "yYXRpb24iOiIyMDk5LTEyLTMxIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWl" +
                    "tcy9tZW1iZXJzaGlwc3RhcnQiOiIyMDIyLTEwLTE3IiwiaXNzIjoiaHR0c" +
                    "HM6Ly9zYW5kYm94LWF1dGhzZXJ2aWNlLnByaWFpZC5jaCIsImF1ZCI6" +
                    "Imh0dHBzOi8vaGVhbHRoc2VydmljZS5wcmlhaWQuY2giLCJleHAiOjE" +
                    "2NjYxOTA3MDgsIm5iZiI6MTY2NjE4MzUwOH0.bX61yih8CX4SBbNpfX" +
                    "3Aphkfb-6-d2Arzea9bjM6v6g&language=en-gb&symptoms=" + jsonSymptoms +"&gender="+ gender +
                    "&year_of_birth="+ yearOfBirth;
            log.info("Url {}", url);
            ResponseEntity<Object> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Object.class,
                    1
            );
            log.info("Response body {}", responseEntity.getBody());
            System.out.print(responseEntity.getBody());
            return responseEntity.getBody();
        }catch (Exception clientErrorException){
            log.debug("Priald fetch Symptoms endpoint returned {}", clientErrorException.getMessage());
        }
        return null;
    }

    @SneakyThrows
    public Object fetchSymptoms(){
        try {
            String tokenValue = getToken();
            HttpEntity entity = new HttpEntity<>(getHttpHeadersUsingToken(tokenValue));
            String url = "https://sandbox-healthservice.priaid.ch/symptoms?token="+tokenValue+"&language=en-gb";
            log.info("Url {}", url);
            ResponseEntity<Object> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Object.class,
                    1
            );
            log.info("Response body {}", responseEntity.getBody());
            System.out.print(responseEntity.getBody());
            return responseEntity.getBody();
        }catch (Exception clientErrorException){
            log.debug("Priald fetch Symptoms endpoint returned {}", clientErrorException.getMessage());
        }
        return null;
    }

    private String getToken(){
        PrialdLoginDetails prialdLoginDetails = new ObjectMapper().convertValue(getAccessToken(), PrialdLoginDetails.class);
        return prialdLoginDetails.getToken();
    }


    private HttpHeaders getHttpHeadersUsingSecretKey(String hashedCred) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer ligaliomotolani@gmail.com :" + hashedCred);
        return headers;
    }

    private HttpHeaders getHttpHeadersUsingToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    public static String getMD5(String data) throws NoSuchAlgorithmException
    {
        MessageDigest messageDigest=MessageDigest.getInstance("MD5");

        messageDigest.update(data.getBytes());
        byte[] digest=messageDigest.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(Integer.toHexString((int) (b & 0xff)));
        }
        return sb.toString();
    }
}
