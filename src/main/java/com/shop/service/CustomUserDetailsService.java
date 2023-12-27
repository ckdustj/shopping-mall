package com.shop.service;

import com.shop.dto.UserDTO;
import com.shop.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserDTO userDTO = userMapper.find_user(username);
//        if(Objects.isNull(userDTO)){
//            throw new UsernameNotFoundException("해당 username이 존재하지 않음, [" + username + "]");
//        }
//        return userDTO;
        return null;
    }
}
