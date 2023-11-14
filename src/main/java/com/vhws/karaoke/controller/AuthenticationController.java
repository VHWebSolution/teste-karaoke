package com.vhws.karaoke.controller;

import com.vhws.karaoke.entity.dto.AuthenticationDTO;
import com.vhws.karaoke.entity.dto.LoginResponseDTO;
import com.vhws.karaoke.entity.dto.RegisterDTO;
import com.vhws.karaoke.entity.ecxeption.ResourceBadRequestException;
import com.vhws.karaoke.entity.model.User;
import com.vhws.karaoke.infra.TokenService;
import com.vhws.karaoke.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        if (this.userRepository.findByLogin(data.login()) != null)
            throw new ResourceBadRequestException("User already exists!");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
