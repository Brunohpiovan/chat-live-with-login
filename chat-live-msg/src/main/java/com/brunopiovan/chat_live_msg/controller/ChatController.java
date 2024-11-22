package com.brunopiovan.chat_live_msg.controller;


import com.brunopiovan.chat_live_msg.domain.ChatMessage;
import com.brunopiovan.chat_live_msg.domain.Message;
import com.brunopiovan.chat_live_msg.repository.MessageRepository;
import com.brunopiovan.chat_live_msg.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired 
    private MessageService messageService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/messages")
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

    @GetMapping("/api/messages")
    public List<Message> getMessages() {
        return messageService.getAllMessages();
    }




}
