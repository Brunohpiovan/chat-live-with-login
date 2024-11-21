package com.brunopiovan.chat_live_msg.controller;


import com.brunopiovan.chat_live_msg.DTOS.AuthenticationDTO;
import com.brunopiovan.chat_live_msg.DTOS.RegisterDTO;
import com.brunopiovan.chat_live_msg.domain.User;
import com.brunopiovan.chat_live_msg.repository.UserRepository;
import com.brunopiovan.chat_live_msg.security.TokenService;
import com.brunopiovan.chat_live_msg.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO body){
        var userPass = new UsernamePasswordAuthenticationToken(body.email(),body.senha());
        var auth = this.authenticationManager.authenticate(userPass);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(token);
    }

    @PostMapping(value= "/register" ,consumes = "multipart/form-data")
    public ResponseEntity register(@RequestPart("registerRequest") RegisterDTO user, @RequestPart("fotoUrl") MultipartFile profilePicture){
        if (this.userRepository.findByEmail(user.getEmail())!=null){
            return ResponseEntity.badRequest().body("Ja existe um usuario com esse email.");
        }
        User novoUser = this.registerService.registrar(user,profilePicture);
        if(novoUser!=null){
            var token = tokenService.generateToken(novoUser);
            return ResponseEntity.ok(token);
        }else
            return ResponseEntity.status(500).body("Erro ao salvar a usuario.");

    }
}
