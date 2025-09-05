package com.quadraOuro.adapters.web;

import com.quadraOuro.adapters.web.dto.CourtRequest;
import com.quadraOuro.adapters.web.dto.CourtResponse;
import com.quadraOuro.adapters.web.dto.error.NotFoundException;
import com.quadraOuro.adapters.web.mapper.CourtMapper;
import com.quadraOuro.domain.models.Court;
import com.quadraOuro.domain.models.CourtFilter;
import com.quadraOuro.domain.models.CourtStatus;
import com.quadraOuro.ports.in.CourtUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

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
            @RequestParam(required = false) CourtStatus status
    ) {
        var filter = new CourtFilter(Optional.ofNullable(type), Optional.ofNullable(status));
        var result = courtUseCase.findAll(filter).stream()
                .map(courtMapper::toResponse)
                .toList();
        return ResponseEntity.ok(result);
    }

    // GET /api/v1/courts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CourtResponse> getById(@PathVariable Long id) {
        var court = courtUseCase.findById(id)
                .orElseThrow(() -> new NotFoundException("Court %d não encontrada".formatted(id)));
        return ResponseEntity.ok(courtMapper.toResponse(court));
    }


    @PostMapping
    public ResponseEntity<CourtResponse> create(@Valid @RequestBody CourtRequest request) {
        var domain = new Court(
                null,
                request.name(),
                request.type(),
                request.status(),
                null,
                null,
                null
        );

        var saved = courtUseCase.save(domain);


        return ResponseEntity
                .created(URI.create("/api/v1/courts/" + saved.getId()))
                .body(courtMapper.toResponse(saved));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CourtResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CourtRequest request
    ) {
        var existing = courtUseCase.findById(id)
                .orElseThrow(() -> new NotFoundException("Court %d não encontrada".formatted(id)));

        var updated = courtMapper.toUpdatedDomain(existing, request);
        var saved = courtUseCase.save(updated);

        return ResponseEntity.ok(courtMapper.toResponse(saved));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete ( @PathVariable Long id ) {
        var existing = courtUseCase.findById(id)
                .orElseThrow(() -> new NotFoundException("Court %d não encontrada".formatted(id)));

        courtUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
