package com.shop.mapper;

import com.shop.dto.user.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 유저 찾기 + SNS (소셜) 유저 찾기
    UserDTO find_user(
            @Param("userDTO") UserDTO userDTO,
            @Param("isSNS") boolean isSNS
    );

    @Select("SELECT id FROM `user` WHERE `tel` = #{phoneNumber}")
    String find_user_id(String phoneNumber);

    // 유저 회원가입 ( 모든 정보 (SNS정보 빼고) )
    void join_user(UserDTO userDTO);
    // 기존 회원가입 유저에 SNS 정보만 등록하기
    void insert_sns_info(UserDTO userDTO);
}
