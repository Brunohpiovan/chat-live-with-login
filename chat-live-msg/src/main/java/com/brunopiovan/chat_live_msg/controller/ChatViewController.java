package com.brunopiovan.chat_live_msg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatViewController {

    @GetMapping("/api/chat")
    public String viewChat(){
        return "chat/chat";
    }

}
