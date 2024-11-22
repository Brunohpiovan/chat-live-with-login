package com.brunopiovan.chat_live_msg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brunopiovan.chat_live_msg.domain.User;
import com.brunopiovan.chat_live_msg.service.UserService;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/api/online-users")
     public List<User> getOnlineUsers() { 
        return userService.findAll(); 
    }
}
