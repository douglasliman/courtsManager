package com.quadraOuro.adapters.web.mapper;

import com.quadraOuro.adapters.web.dto.CourtRequest;
import com.quadraOuro.adapters.web.dto.CourtResponse;
import com.quadraOuro.domain.models.Court;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CourtMapper {

    public CourtResponse toResponse(Court court) {
        return new CourtResponse(
                court.getId(),
                court.getName(),
                court.getType(),
                court.getStatus()

        );
    }
    public Court toUpdatedDomain(Court existing, CourtRequest request) {
        return new Court(
                existing.getId(),
                request.name(),
                request.type(),
                request.status(),
                existing.getCreatedAt(),
                Instant.now()
        );
    }
}