package com.brunopiovan.chat_live_msg.controller;


import com.brunopiovan.chat_live_msg.DTOS.AuthenticationDTO;
import com.brunopiovan.chat_live_msg.DTOS.RegisterDTO;
import com.brunopiovan.chat_live_msg.domain.User;
import com.brunopiovan.chat_live_msg.repository.UserRepository;
import com.brunopiovan.chat_live_msg.security.TokenService;
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
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        User novoUser = new User(null, user.getEmail(), user.getSenha(), null);
        // Definir o caminho do diretório de uploads
        Path uploadDir = Paths.get("uploads");
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }if (profilePicture != null && !profilePicture.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + profilePicture.getOriginalFilename();
                Path targetLocation = uploadDir.resolve(fileName);
                Files.copy(profilePicture.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/uploads/")
                        .path(fileName)
                        .toUriString();
                novoUser.setFotoUrl(fileDownloadUri); }
                this.userRepository.save(novoUser);
                var token = tokenService.generateToken(novoUser);
                return ResponseEntity.ok(token);
    }catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao salvar a imagem.");
        }
    }
}
