package com.brunopiovan.chat_live_msg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String redireciona(){
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String Login() {
        return "login/index";
    }



}
