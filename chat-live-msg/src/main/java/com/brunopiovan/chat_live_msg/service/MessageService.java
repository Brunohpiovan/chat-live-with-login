package com.brunopiovan.chat_live_msg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.brunopiovan.chat_live_msg.domain.Message;
import com.brunopiovan.chat_live_msg.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    
}
