package com.shop.service;

import com.shop.dto.ImageFileDTO;
import com.shop.dto.SnsInfoDTO;
import com.shop.dto.UserDTO;
import com.shop.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Log4j2
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.warn(" =========== CustomOAuth2UserService ============ ");
        log.warn(userRequest);
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();
        log.warn("[" + clientName + "]" + "(으)로 로그인 중입니다...");
        // 정보를 담고 있는 유저 맵을 생성한다.
        Map<String, Object> userPropertiesMap = create_user_properties_map();

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();

        switch (clientName.toUpperCase()){
            case "KAKAO" -> get_kakao_properties(paramMap, userPropertiesMap);
            case "GOOGLE" -> get_google_properties(paramMap, userPropertiesMap);
            case "NAVER" -> get_naver_properties(paramMap, userPropertiesMap);
        }

        String id = (String) userPropertiesMap.get("id");
        UserDTO findedUserDTO = userMapper.find_user(id, true);
        // 그 소셜 회원가입(등록) 기록이 있는가?
        // 기록이 없었다. => 소셜 회원가입(등록) 해야 한다.
        if((Objects.isNull(findedUserDTO.getSnsInfo().getId()))){
            // 근데 유저 자체도 없네 ?? => 유저 회원가입(첫 등록)도 해야 한다.
            if(Objects.isNull(findedUserDTO.getId())){
                // 회원가입을 처음부터 끝까지 진행시킨다 ...
            }
            // 새로운 소셜 로그인만 등록하면 된다.
            // 유저 정보에 새로운 소셜 정보를 추가한다...

            // 받아온 정보로 유저 DTO를 생성한다..
            UserDTO userDTO = create_userDTO(userPropertiesMap, userRequest.getAccessToken().getTokenValue());
        }

        // 유저가 있었다 => 그냥 로그인 시키면 됨
        return findedUserDTO;
    }

    public Map<String, Object> create_user_properties_map(){
        Map<String, Object> userPropertiesMap = new HashMap<>();
        userPropertiesMap.put("id", null);
        userPropertiesMap.put("name", null);
        userPropertiesMap.put("email", null);
        userPropertiesMap.put("mobile", null);
        userPropertiesMap.put("profile_image", null);
        return userPropertiesMap;
    }

    public void get_kakao_properties(Map<String, Object> paramMap, Map<String, Object> userPropertiesMap){
        Map<String, String> propertyMap = (Map<String, String>) paramMap.get("properties");
        String id = (String) paramMap.get("id").toString();
        String name = propertyMap.get("nickname"); // 닉네임 (이름)
        String profileImage = propertyMap.get("profile_image"); // 프로필 사진

        userPropertiesMap.put("id", id);
        userPropertiesMap.put("name", name);
        userPropertiesMap.put("profileImage", profileImage);
    }

    public void get_google_properties(Map<String, Object> paramMap, Map<String, Object> userPropertiesMap){
        String id = (String) paramMap.get("sub"); // id
        String name = (String) paramMap.get("name"); // 이름
        String email = (String) paramMap.get("email"); // 이메일
        String profileImage = (String) paramMap.get("picture"); // 프로필 사진

        userPropertiesMap.put("id", id);
        userPropertiesMap.put("name", name);
        userPropertiesMap.put("email", email);
        userPropertiesMap.put("profileImage", profileImage);
    }

    public void get_naver_properties(Map<String, Object> paramMap, Map<String, Object> userPropertiesMap){
        Map<String, String> propertyMap = (Map<String, String>) paramMap.get("response");
        String id = propertyMap.get("id");
        String email = propertyMap.get("email");
        String mobile = propertyMap.get("mobile");
        String profileImage = propertyMap.get("profile_image");

        userPropertiesMap.put("id", id);
        userPropertiesMap.put("email", email);
        userPropertiesMap.put("mobile", mobile);
        userPropertiesMap.put("profileImage", profileImage);
    }

    public UserDTO create_userDTO(Map<String, Object> userPropertiesMap, String token){
        String snsInfoId = (String) userPropertiesMap.get("id");
        String snsInfoClientName = (String) userPropertiesMap.get("clientName");
        String snsInfoProfileImage= (String) userPropertiesMap.get("profile_image");
        String snsInfoMobile = (String) userPropertiesMap.get("mobile");
        String snsInfoEmail = (String) userPropertiesMap.get("email");

        SnsInfoDTO snsInfoDTO = SnsInfoDTO.builder()
                .id(snsInfoId)
                .clientName(snsInfoClientName)
                .connectDate(LocalDateTime.now())
                .attributes(userPropertiesMap)
                .build();

        ImageFileDTO imageFileDTO = ImageFileDTO.builder()
                .originalFileName(snsInfoProfileImage)
                .savedFileName(snsInfoProfileImage)
                .build();

        return UserDTO.builder()
                .id("test-user")
                .token(token)
                .tel(snsInfoMobile)
                .imageFile(imageFileDTO)
                .email(snsInfoEmail)
                .snsInfo(snsInfoDTO)
                .build();
    }
}

