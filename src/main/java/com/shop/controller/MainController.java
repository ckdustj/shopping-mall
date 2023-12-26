package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    /* 메인 화면 이동 */
    @GetMapping("/")
    public String get_main(){
        return "main/main";
    }
}
