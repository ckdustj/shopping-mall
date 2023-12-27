package com.shop.mapper;

import com.shop.dto.SnsInfoDTO;
import com.shop.dto.UserDTO;
import jakarta.validation.constraints.Max;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 유저 찾기 + SNS (소셜) 유저 찾기
    UserDTO find_user(String id, boolean isSNS);

    void join_user(UserDTO userDTO);


}
