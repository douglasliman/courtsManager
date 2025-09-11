package com.quadraOuro.adapters.web.dto.response;

import com.quadraOuro.domain.models.UserRole;
import com.quadraOuro.adapters.web.dto.response.EnderecoResponse;

public record UserResponse(
        Long id,
        String name,
        String email,
        UserRole role,
        EnderecoResponse endereco
) {}
