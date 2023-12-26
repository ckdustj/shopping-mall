package com.shop.service;

import com.shop.dto.UserDTO;
import com.shop.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void login_user(UserDTO userDTO){
//        userMapper.find_user(userDTO);
    }
    // 회원가입
    public void join_user(UserDTO userDTO){
        userMapper.join_user(userDTO);
    }
}
