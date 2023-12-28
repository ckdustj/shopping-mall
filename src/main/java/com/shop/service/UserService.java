package com.shop.service;

import com.shop.dto.user.UserDTO;
import com.shop.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 회원가입
    public void join_user(UserDTO userDTO){
        // 패스워드 인코딩 작업
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // DB 삽입
        userMapper.join_user(userDTO);
    }


}
