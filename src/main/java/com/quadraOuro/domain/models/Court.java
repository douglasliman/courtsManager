package com.quadraOuro.domain.models;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.Instant;


public class Court {
    private Long id;
    private String name;
    private String type;      // futsal, tênis, etc.
    private CourtStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public Court() {
    }
    public Court(Long id, String name, String type, CourtStatus status, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public CourtStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}