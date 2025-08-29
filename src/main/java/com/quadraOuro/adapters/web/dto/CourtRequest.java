package com.quadraOuro.adapters.web.dto;


import com.quadraOuro.domain.models.CourtStatus;

public record CourtRequest(
        String name,
        String type,
        CourtStatus status
) {}