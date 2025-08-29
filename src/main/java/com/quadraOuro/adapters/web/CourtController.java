package com.quadraOuro.adapters.web;

import com.quadraOuro.adapters.web.dto.CourtRequest;
import com.quadraOuro.adapters.web.dto.CourtResponse;
import com.quadraOuro.adapters.web.dto.error.NotFoundException;
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

    public CourtController(CourtUseCase courtUseCase) {
        this.courtUseCase = courtUseCase;
    }

    // GET /api/v1/courts?type=tenis&status=ATIVA
    @GetMapping
    public ResponseEntity<List<CourtResponse>> list(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) CourtStatus status
    ) {
        var filter = new CourtFilter(Optional.ofNullable(type), Optional.ofNullable(status));
        var result = courtUseCase.findAll(filter).stream()
                .map(c -> new CourtResponse(c.getId(), c.getName(), c.getType(), c.getStatus()))
                .toList();
        return ResponseEntity.ok(result);
    }

    // GET /api/v1/courts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CourtResponse> getById(@PathVariable Long id) {
        var court = courtUseCase.findById(id)
                .orElseThrow(() -> new NotFoundException("Court %d não encontrada".formatted(id)));
        var response = new CourtResponse(court.getId(), court.getName(), court.getType(), court.getStatus());
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<CourtResponse> create(@Valid @RequestBody CourtRequest request) {
        var domain = new Court(
                null,
                request.name(),
                request.type(),
                request.status(),
                null,
                null
        );

        var saved = courtUseCase.save(domain);
        var response = new CourtResponse(saved.getId(), saved.getName(), saved.getType(), saved.getStatus());

        return ResponseEntity
                .created(URI.create("/api/v1/courts/" + saved.getId()))
                .body(response);
    }
        @PutMapping("/{id}")
    public ResponseEntity<CourtResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CourtRequest request
    ) {
        var existing = courtUseCase.findById(id)
                .orElseThrow(() -> new NotFoundException("Court %d não encontrada".formatted(id)));

        var updated = new Court(
                id,
                request.name(),
                request.type(),
                request.status(),
                existing.getCreatedAt(),
                Instant.now()
        );

        var saved = courtUseCase.save(updated);
        var response = new CourtResponse(saved.getId(), saved.getName(), saved.getType(), saved.getStatus());

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete ( @PathVariable Long id ) {
        var existing = courtUseCase.findById(id)
                .orElseThrow(() -> new NotFoundException("Court %d não encontrada".formatted(id)));

        courtUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
