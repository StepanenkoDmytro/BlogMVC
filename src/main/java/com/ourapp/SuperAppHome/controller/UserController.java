package com.ourapp.SuperAppHome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class UserController {

    @GetMapping("/user")
    public String getUserPage(){
        return "user-main";
    }
}
