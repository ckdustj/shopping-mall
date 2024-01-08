package com.shop.service;

import com.shop.dto.user.UserDTO;
import com.shop.mapper.UserMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Service
public class UserService {
    private final String NAVER_SMS_URL = "https://sens.apigw.ntruss.com/sms/v2/services/{serviceId}/messages";
    private final String NAVER_SMS_SERVICE_KEY = "";
    private final String NAVER_SMS_ACCESS_KEY = "";
    private final String NAVER_SMS_SECRET_KEY = "";

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RestOperations restOperations;

    // 회원가입
    public void join_user(UserDTO userDTO){
        // 패스워드 인코딩 작업
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // DB 삽입
        userMapper.join_user(userDTO);
    }

    // 유저 아이디 찾기
    public String find_user_id(String phoneNumber){
        String userID = userMapper.find_user_id(phoneNumber);
        if (Objects.isNull(userID)){
            return "유저 존재 X";
        }
        try {
            send_sms_messsage(phoneNumber);
        }catch (Exception e){
            return "일시적 문제...";
        }
        return "문자 발송 !";
    }

    private void send_sms_messsage(String phoneNumberTo) throws Exception{
        String timestamp = String.valueOf(Timestamp.valueOf(LocalDateTime.now()).getTime());			// current timestamp (epoch)
        String signature = makeSignature("POST", "/sms/v2/services/" + NAVER_SMS_SERVICE_KEY + "/messages", timestamp);
        // 받을 사람 등록
        JSONObject sendMessageTemplate = new JSONObject();
        sendMessageTemplate.put("to", phoneNumberTo);
        // 메세지 정보
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(sendMessageTemplate);
        // 보내는 메세지
        JSONObject messageObject = new JSONObject();
        messageObject.put("type", "SMS"); //필수
        messageObject.put("contentType", "COMM"); //필수 아님
        messageObject.put("from", "자기 번호");
        messageObject.put("content", "[KOREAIT]\n 조회할 아이디");
        messageObject.put("messages", Arrays.asList(sendMessageTemplate));
        // 메세지 전송 요청
        RequestEntity<String> requestEntity = RequestEntity
                .post(NAVER_SMS_URL, NAVER_SMS_SERVICE_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-ncp-apigw-timestamp", timestamp)
                .header("x-ncp-iam-access-key", NAVER_SMS_ACCESS_KEY)
                .header("x-ncp-apigw-signature-v2", signature)
                .body(messageObject.toString());

        ResponseEntity<String> response = restOperations.exchange(requestEntity, String.class);
    }

    public String makeSignature(String method, String url, String timestamp) throws Exception{
        String space = " ";					// one space
        String newLine = "\n";					// new line

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(NAVER_SMS_ACCESS_KEY)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(NAVER_SMS_SECRET_KEY.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }
}
