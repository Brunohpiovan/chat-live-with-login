package com.brunopiovan.chat_live_msg.service;

import com.brunopiovan.chat_live_msg.DTOS.RegisterDTO;
import com.brunopiovan.chat_live_msg.domain.User;
import com.brunopiovan.chat_live_msg.repository.UserRepository;
import com.brunopiovan.chat_live_msg.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User registrar(RegisterDTO user, MultipartFile profilePicture){
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
            return userRepository.save(novoUser);

        }catch (Exception e) {
            return null;
        }
    }
}
