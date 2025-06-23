package com.ifrs.financeapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.ifrs.financeapp.dto.RegisterDTO;
import com.ifrs.financeapp.model.user.User;
import com.ifrs.financeapp.model.user.LanguagePreference;
import com.ifrs.financeapp.model.user.ThemePreference;
import com.ifrs.financeapp.repository.UserRepository;
import com.ifrs.financeapp.service.AuthenticationService;

public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        RegisterDTO registerDTO = new RegisterDTO(
                "newuser@example.com",
                "password123",
                "New User",
                LanguagePreference.PTBR,
                ThemePreference.DARK);

        when(userRepository.findByLogin("newuser@example.com")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        assertDoesNotThrow(() -> authenticationService.register(registerDTO));

        verify(userRepository, times(1)).findByLogin("newuser@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterEmailAlreadyExists() {
        RegisterDTO registerDTO = new RegisterDTO(
                "existing@example.com",
                "password123",
                "Existing User",
                LanguagePreference.ENG,
                ThemePreference.LIGHT);

        when(userRepository.findByLogin("existing@example.com")).thenReturn(new User());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register(registerDTO);
        });

        assertEquals("auth.error.emailAlreadyExists", ex.getMessage());

        verify(userRepository, times(1)).findByLogin("existing@example.com");
        verify(userRepository, never()).save(any());
    }
}
