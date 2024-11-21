package com.brunopiovan.chat_live_msg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistroController {

    @GetMapping("/register")
    public String Registro() {
        return "login/registro";
    }
}
