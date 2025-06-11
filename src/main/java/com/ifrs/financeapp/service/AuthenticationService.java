package com.ifrs.financeapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ifrs.financeapp.config.TokenService;
import com.ifrs.financeapp.dto.AuthenticationDTO;
import com.ifrs.financeapp.dto.RegisterDTO;
import com.ifrs.financeapp.model.user.User;
import com.ifrs.financeapp.repository.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public String login(AuthenticationDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(),
                authenticationDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        return tokenService.generateToken((User) auth.getPrincipal());
    }

    public void register(RegisterDTO registerDTO) {
        if (userRepository.findByLogin(registerDTO.login()) != null) {
            throw new IllegalArgumentException("auth.error.emailAlreadyExists");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

        User newUser = new User(registerDTO.login(), encryptedPassword, registerDTO.completeName(),
                registerDTO.languagePreference(),
                registerDTO.themePreference());

        userRepository.save(newUser);
    }
}
