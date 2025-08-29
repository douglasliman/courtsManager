package com.quadraOuro.adapters.persistence;

import com.quadraOuro.domain.models.CourtStatus;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "courts")
public class CourtJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Enumerated(EnumType.STRING)
    private CourtStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public CourtJpaEntity() {
    }

    public CourtJpaEntity(Long id, String name, String type, CourtStatus status, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CourtStatus getStatus() {
        return status;
    }

    public void setStatus(CourtStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
