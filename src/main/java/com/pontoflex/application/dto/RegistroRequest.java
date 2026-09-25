package com.pontoflex.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record RegistroRequest(
        @NotBlank @Size(max = 150) String nome,
        @NotBlank @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 digitos numericos") String cpf,
        @NotBlank @Size(max = 30) String matricula,
        @NotNull @PastOrPresent LocalDate dataAdmissao,
        @NotBlank @Email @Size(max = 150) String email,
        @NotBlank @Size(min = 8, max = 100) String senha) {}
