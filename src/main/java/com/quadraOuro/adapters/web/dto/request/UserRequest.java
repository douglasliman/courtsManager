package com.quadraOuro.adapters.web.dto.request;

import com.quadraOuro.domain.models.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotNull(message = "Role é obrigatória")
        UserRole role,

        @NotBlank(message = "CEP é obrigatório")
        String cep
) {}
