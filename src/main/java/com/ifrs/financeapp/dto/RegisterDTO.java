package com.ifrs.financeapp.dto;

import com.ifrs.financeapp.model.user.LanguagePreference;
import com.ifrs.financeapp.model.user.ThemePreference;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
        @NotBlank(message = "O login é obrigatório.") String login,
        @NotBlank(message = "A senha é obrigatória.") String password,
        @NotBlank(message = "O nome completo é obrigatório.") String completeName,
        @NotNull(message = "A preferência de idioma é obrigatória.") LanguagePreference languagePreference,
        @NotNull(message = "A preferência de tema é obrigatória.") ThemePreference themePreference) {
}
