package com.quadraOuro.application;

import com.quadraOuro.domain.models.Court;
import com.quadraOuro.domain.models.CourtFilter;
import com.quadraOuro.ports.in.CourtUseCase;
import com.quadraOuro.ports.out.CourtRepositoryPort;

import java.util.List;
// ...existing code...
import com.quadraOuro.domain.exception.CourtNotFoundException;

public class CourtQueryService implements CourtUseCase {

    private final CourtRepositoryPort repository;

    public CourtQueryService(CourtRepositoryPort courtRepositoryPort) {
        this.repository = courtRepositoryPort;
    }

    @Override
    public List<Court> findAll(CourtFilter filter) {
        return repository.findAll(filter);
    }

    @Override
    public Court findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CourtNotFoundException(id));
    }

    @Override
    public Court save(Court court) {
        return repository.save(court);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
