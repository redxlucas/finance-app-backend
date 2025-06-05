package com.ifrs.financeapp.dto;

import java.time.LocalDate;

import com.ifrs.financeapp.model.user.LanguagePreference;
import com.ifrs.financeapp.model.user.ThemePreference;

public record RegisterDTO(String login, String password, String completeName, LocalDate birthDate,
        LanguagePreference languagePreference, ThemePreference themePreference) {

}
