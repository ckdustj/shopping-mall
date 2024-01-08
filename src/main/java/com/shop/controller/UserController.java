package com.shop.controller;

import com.shop.dto.user.UserDTO;
import com.shop.service.UserCertificationService;
import com.shop.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired private UserService userService;
    @Autowired private UserCertificationService userCertificationService;

    @GetMapping("/login")
    public void user_get_login(){    }

    @GetMapping("/join")
    public void user_get_join(){    }

    @PostMapping("/join")
    public String user_post_join(
            @Validated UserDTO userDTO,
            BindingResult result
    ){
        if(result.hasErrors()){
            log.error("userDTO 객체에 에러가 발생함!");
            return "redirect:/user/join";
        }
        userService.join_user(userDTO);
        log.info("User INSERTED");
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/cert/token")
    public String get_user_certification_token(){
        try{
            // 성공시에는 Token값 반환
            return userCertificationService.get_portone_access_token();
        }catch (Exception e){
            // 실패시에는 빈문자열 반환
            log.error(e.getMessage());
            return "";
        }
    }

    @ResponseBody
    @PostMapping("/cert/{impUID}")
    public String get_user_certification_unique_key(
            @PathVariable String impUID,
            @RequestBody String token
    ){
        try{
            // 성공시에는 Token값 반환
            return userCertificationService.get_user_certification(impUID, token);
        }catch (Exception e){
            // 실패시에는 빈문자열 반환
            log.error(e.getMessage());
            return "";
        }
    }

    //***********************************************************
    @ResponseBody
    @GetMapping("/find/{phoneNumber}")
    public String find_user_id(@PathVariable String phoneNumber){
       return userService.find_user_id(phoneNumber);
    }
}