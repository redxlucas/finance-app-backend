package com.ifrs.financeapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ifrs.financeapp.repository.UserRepository;

public class AuthorizationServiceTest {

    private AuthorizationService authorizationService;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        authorizationService = new AuthorizationService();
        authorizationService.userRepository = userRepository;
    }

    @Test
    void testLoadUserByUsernameFound() {
        String username = "user@example.com";
        UserDetails mockUserDetails = mock(UserDetails.class);

        when(userRepository.findByLogin(username)).thenReturn(mockUserDetails);

        UserDetails userDetails = authorizationService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(mockUserDetails, userDetails);
        verify(userRepository, times(1)).findByLogin(username);
    }
}
