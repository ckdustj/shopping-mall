package com.shop.mapper;

import com.shop.dto.UserDTO;
import jakarta.validation.constraints.Max;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDTO find_user(String username);
    void join_user(UserDTO userDTO);


}
