package com.brunopiovan.chat_live_msg.controller;


import com.brunopiovan.chat_live_msg.domain.ChatMessage;
import com.brunopiovan.chat_live_msg.domain.Message;
import com.brunopiovan.chat_live_msg.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topics/messages")
    public ChatMessage sendMessage(ChatMessage message, Principal principal) {
        message.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Message savedMessage = new Message();
        savedMessage.setSender(message.getSender());
        savedMessage.setContent(message.getContent());
        savedMessage.setTimestamp(message.getTimestamp());
        savedMessage.setPhotoUrl(message.getProfilePicture());
        messageRepository.save(savedMessage);
        return message;
    }
}
