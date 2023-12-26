package com.shop.controller;

import com.shop.dto.UserDTO;
import com.shop.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

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
            log.error("UserDTO 객체에 에러가 발생함");
            return "redirect:/join";
        }
        userService.join_user(userDTO);
        return "redirect:/";
    }

}
