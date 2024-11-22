package com.brunopiovan.chat_live_msg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brunopiovan.chat_live_msg.domain.User;
import com.brunopiovan.chat_live_msg.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }



    
}
