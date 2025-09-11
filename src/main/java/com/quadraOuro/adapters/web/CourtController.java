/**
 * Adapter web (REST controller) para entrada/saída de quadras.
 * Recebe requisições HTTP, delega para os casos de uso e converte modelos de domínio para DTOs.
 * Não contém lógica de negócio.
 */
package com.quadraOuro.adapters.web;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quadraOuro.adapters.web.dto.CourtRequest;
import com.quadraOuro.adapters.web.dto.CourtResponse;
import com.quadraOuro.adapters.web.dto.error.NotFoundException;
import com.quadraOuro.domain.exception.CourtNotFoundException;
import com.quadraOuro.adapters.web.mapper.CourtMapper;
import com.quadraOuro.domain.models.CourtFilter;
import com.quadraOuro.domain.models.CourtStatus;
import com.quadraOuro.ports.in.CourtUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/courts")
public class CourtController {

        private final CourtUseCase courtUseCase;
        private final CourtMapper courtMapper;

        public CourtController(CourtUseCase courtUseCase, CourtMapper courtMapper) {
                this.courtUseCase = courtUseCase;
                this.courtMapper = courtMapper;
        }

        // GET /api/v1/courts?type=tenis&status=ATIVA
        @GetMapping
        public ResponseEntity<List<CourtResponse>> list(
                        @RequestParam(required = false) String type,
                        @RequestParam(required = false) CourtStatus status) {
                var filter = new CourtFilter(Optional.ofNullable(type), Optional.ofNullable(status));
                var result = courtUseCase.findAll(filter).stream()
                                .map(courtMapper::toResponse)
                                .toList();
                return ResponseEntity.ok(result);
        }

        // ...existing code...
        @GetMapping("/{id}")
                public ResponseEntity<CourtResponse> getById(@PathVariable Long id) {
                                try {
                                        var court = courtUseCase.findById(id);
                                        return ResponseEntity.ok(courtMapper.toResponse(court));
                                } catch (CourtNotFoundException ex) {
                                        throw new NotFoundException(ex.getMessage());
                                }
                }

        @PostMapping
        public ResponseEntity<CourtResponse> create(@Valid @RequestBody CourtRequest request) {
                var domain = courtMapper.toDomain(request);
                var saved = courtUseCase.save(domain);
                return ResponseEntity
                        .created(URI.create("/api/v1/courts/" + saved.getId()))
                        .body(courtMapper.toResponse(saved));
        }

        @PutMapping("/{id}")
                public ResponseEntity<CourtResponse> update(
                                                @PathVariable Long id,
                                                @Valid @RequestBody CourtRequest request) {
                                try {
                                        var existing = courtUseCase.findById(id);
                                        var updated = courtMapper.toUpdatedDomain(existing, request);
                                        var saved = courtUseCase.save(updated);
                                        return ResponseEntity.ok(courtMapper.toResponse(saved));
                                } catch (CourtNotFoundException ex) {
                                        throw new NotFoundException(ex.getMessage());
                                }
                }

        @DeleteMapping("/{id}")
                public ResponseEntity<Void> delete(@PathVariable Long id) {
                                try {
                                        courtUseCase.findById(id);
                                        courtUseCase.deleteById(id);
                                        return ResponseEntity.noContent().build();
                                } catch (CourtNotFoundException ex) {
                                        throw new NotFoundException(ex.getMessage());
                                }
                }
}
