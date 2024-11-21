package com.brunopiovan.chat_live_msg.repository;

import com.brunopiovan.chat_live_msg.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
