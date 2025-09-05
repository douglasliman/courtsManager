package com.quadraOuro.domain.models;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.List;


public class Court {
    private Long id;
    private String name;
    private String type;
    private CourtStatus status;
    private List<String> photoUrls;
    private Instant createdAt;
    private Instant updatedAt;

    public Court() {
    }
    public Court(Long id, String name, String type, CourtStatus status, Instant createdAt, Instant updatedAt, List<String> photoUrls) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.photoUrls = photoUrls;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public CourtStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public List<String> getPhotoUrls() { return photoUrls; }
    public void setPhotoUrls(List<String> photoUrls) { this.photoUrls = photoUrls; }

}