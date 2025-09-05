package com.quadraOuro.adapters.web.dto.response;

import com.quadraOuro.domain.models.UserRole;

public record UserResponse(
        Long id,
        String name,
        String email,
        UserRole role
) {}
