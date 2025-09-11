package com.quadraOuro.ports.out;

import java.util.List;
import java.util.Optional;

import com.quadraOuro.domain.models.Court;
import com.quadraOuro.domain.models.CourtFilter;

public interface CourtRepositoryPort {
    List<Court> findAll(CourtFilter filter);

    Optional<Court> findById(Long id);

    Court save(Court court);

    void deleteById(Long id);

}
