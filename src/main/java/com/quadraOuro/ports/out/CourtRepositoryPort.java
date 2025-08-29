package com.quadraOuro.ports.out;

import com.quadraOuro.domain.models.Court;
import com.quadraOuro.domain.models.CourtFilter;

import java.util.List;
import java.util.Optional;

public interface CourtRepositoryPort {
    List<Court> findAll(CourtFilter filter);
    Optional<Court> findByid(Long id);
    Court save(Court court);
    void deleteById(Long id);

}
