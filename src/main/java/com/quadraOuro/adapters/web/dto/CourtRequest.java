package com.quadraOuro.adapters.web.dto;

import com.quadraOuro.domain.models.CourtStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CourtRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Type is required")
        String type,

        @NotNull(message = "Status is required")
        CourtStatus status,

        List<String> photoUrls // New field
) {}