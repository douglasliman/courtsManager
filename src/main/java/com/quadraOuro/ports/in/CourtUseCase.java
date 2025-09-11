package com.quadraOuro.ports.in;

import com.quadraOuro.domain.models.Court;
import com.quadraOuro.domain.models.CourtFilter;

import java.util.List;
// ...existing code...

public interface CourtUseCase {
    List<Court> findAll(CourtFilter filter);
    Court findById(Long id);
    Court save(Court court);
    void deleteById(Long id);
}