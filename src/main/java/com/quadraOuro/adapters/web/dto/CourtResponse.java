package com.quadraOuro.adapters.web.dto;

import com.quadraOuro.domain.models.CourtStatus;

public record CourtResponse(
        Long id,
        String name,
        String type,
        CourtStatus status
) {}