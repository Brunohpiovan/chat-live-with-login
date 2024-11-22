package com.brunopiovan.chat_live_msg.controller;


import com.brunopiovan.chat_live_msg.DTOS.AuthenticationDTO;
import com.brunopiovan.chat_live_msg.DTOS.RegisterDTO;
import com.brunopiovan.chat_live_msg.domain.User;
import com.brunopiovan.chat_live_msg.repository.UserRepository;
import com.brunopiovan.chat_live_msg.security.TokenService;
import com.brunopiovan.chat_live_msg.service.RegisterService;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RegisterService registerService;


    @PostMapping(value="/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO body){
        try{
        var userPass = new UsernamePasswordAuthenticationToken(body.email(),body.senha());
        var auth = this.authenticationManager.authenticate(userPass);
        var user = (UserDetails) auth.getPrincipal();
        var token = tokenService.generateToken(user);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Erro ao fazer login"));
        }
    }

    @PostMapping(value= "/register" ,consumes = "multipart/form-data")
    public ResponseEntity register(@RequestPart("registerRequest") RegisterDTO user, @RequestPart("fotoUrl") MultipartFile profilePicture){
        if (this.userRepository.findByEmail(user.getEmail())!=null){
            return ResponseEntity.badRequest().body("Ja existe um usuario com esse email.");
        }
        User novoUser = this.registerService.registrar(user,profilePicture);
        if(novoUser!=null){
            var token = tokenService.generateToken((UserDetails)novoUser);
            return ResponseEntity.ok(token);
        }else
            return ResponseEntity.status(500).body("Erro ao salvar a usuario.");

    }
}
