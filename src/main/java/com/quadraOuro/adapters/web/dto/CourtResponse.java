package com.quadraOuro.adapters.web.dto;

import com.quadraOuro.domain.models.CourtStatus;

import java.util.List;

public record CourtResponse(
        Long id,
        String name,
        String type,
        CourtStatus status

) {}