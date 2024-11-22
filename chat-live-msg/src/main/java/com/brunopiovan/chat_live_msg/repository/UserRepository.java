package com.brunopiovan.chat_live_msg.repository;


import com.brunopiovan.chat_live_msg.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    UserDetails findByEmail(String email);

    Optional<User> findByUsername(String email);
}
