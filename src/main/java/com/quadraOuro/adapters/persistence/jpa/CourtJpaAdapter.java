package com.quadraOuro.adapters.persistence.jpa;

import com.quadraOuro.adapters.persistence.CourtJpaEntity;
import com.quadraOuro.domain.models.Court;
import com.quadraOuro.domain.models.CourtFilter;
import com.quadraOuro.ports.out.CourtRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public class CourtJpaAdapter implements CourtRepositoryPort {

    private final SpringDataCourtRepository repo;

    public CourtJpaAdapter(SpringDataCourtRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Court> findAll(CourtFilter filter) {
        var typeOpt = filter.getType().map(String::trim).filter(s -> !s.isEmpty());
        var statusOpt = filter.getStatus();

        List<CourtJpaEntity> entities;
        if (typeOpt.isPresent() && statusOpt.isPresent()) {
            entities = repo.findByTypeIgnoreCaseAndStatus(typeOpt.get(), statusOpt.get());
        } else if (typeOpt.isPresent()) {
            entities = repo.findByTypeIgnoreCase(typeOpt.get());
        } else if (statusOpt.isPresent()) {
            entities = repo.findByStatus(statusOpt.get());
        } else {
            entities = repo.findAll();
        }
        return entities.stream().map(CourtMapper::toDomain).toList();
    }

    @Override
    public Optional<Court> findById(Long id) { // Corrigido de findByid para findById
        return repo.findById(id).map(CourtMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Court save(Court court) {
        CourtJpaEntity entity = new CourtJpaEntity();
        entity.setId(court.getId()); // null se for novo
        entity.setName(court.getName());
        entity.setType(court.getType());
        entity.setStatus(court.getStatus());
        entity.setCreatedAt(court.getCreatedAt() != null ? court.getCreatedAt() : Instant.now());
        entity.setUpdatedAt(Instant.now());

        if (court.getCreatedAt() == null) {
            entity.setCreatedAt(Instant.now());   // POST
        } else {
            entity.setCreatedAt(court.getCreatedAt()); // PUT preserva
        }
        entity.setUpdatedAt(Instant.now());

        var saved = repo.save(entity); // Spring Data decide insert/update
        return CourtMapper.toDomain(saved);
    }
    @Override
    @Transactional(readOnly = true)
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}