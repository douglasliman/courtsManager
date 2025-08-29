package com.quadraOuro.adapters.persistence.jpa;

import com.quadraOuro.adapters.persistence.CourtJpaEntity;
import com.quadraOuro.domain.models.Court;

public class CourtMapper {

    private CourtMapper(){}

    public static Court toDomain(CourtJpaEntity e) {
        return new Court(
                e.getId(), e.getName(), e.getType(), e.getStatus(), e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
