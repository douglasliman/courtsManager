package com.quadraOuro.adapters.persistence.jpa;

import com.quadraOuro.adapters.persistence.CourtJpaEntity;
import com.quadraOuro.domain.models.CourtStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataCourtRepository extends JpaRepository<CourtJpaEntity, Long> {
    List<CourtJpaEntity> findByTypeIgnoreCase(String type);
    List<CourtJpaEntity> findByStatus(CourtStatus status);
    List<CourtJpaEntity> findByTypeIgnoreCaseAndStatus(String type, CourtStatus status);
}
