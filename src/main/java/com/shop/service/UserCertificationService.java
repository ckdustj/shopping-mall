package com.shop.service;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Service
public class UserCertificationService {
    private final String PORTONE_ACCESS_TOKEN_URI = "https://api.iamport.kr/users/getToken";
    private final String PORTONE_CERTIFICATION_CI_URI = "https://api.iamport.kr/certifications/{impUID}";
    private final String IMP_KEY = "8126181711377774";
    private final String IMP_SECRET = "9S3JPX3pxQhqlj7acuNTppgsv5K8gDfpiGozFMu5sTcyDjWI3UEUV0iq1HTaekVh1VnNqwISQgsjkO97";
    @Autowired
    private RestOperations restOperations;

    public String get_user_certification(String impUID, String token) throws Exception{
        RequestEntity requestEntity = RequestEntity
                .get(PORTONE_CERTIFICATION_CI_URI, impUID)
                .header("Authorization", token)
                .build();

        ResponseEntity<JSONObject> responseEntity = restOperations.exchange(requestEntity, JSONObject.class);
        System.out.println(responseEntity.getBody());
        return ((Map) responseEntity.getBody().get("response")).get("unique_key").toString();
    }

    public String get_portone_access_token() throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = new JSONObject();
        body.put("imp_key", IMP_KEY);
        body.put("imp_secret", IMP_SECRET);

        HttpEntity<String> entity = new HttpEntity<>(body.toString(),headers);
        ResponseEntity<JSONObject> responseEntity = restOperations.postForEntity(PORTONE_ACCESS_TOKEN_URI, entity, JSONObject.class);
        return ((Map) responseEntity.getBody().get("response")).get("access_token").toString();
    }
}
