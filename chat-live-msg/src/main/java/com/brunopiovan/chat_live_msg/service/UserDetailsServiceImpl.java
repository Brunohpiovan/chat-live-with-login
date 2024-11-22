package com.brunopiovan.chat_live_msg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brunopiovan.chat_live_msg.domain.User;
import com.brunopiovan.chat_live_msg.repository.UserRepository;
import com.brunopiovan.chat_live_msg.security.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return UserDetailsImpl.build(user.get());
        }else{
            throw new UsernameNotFoundException(username);
        }
    }
}
