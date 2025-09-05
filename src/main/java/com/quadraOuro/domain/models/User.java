package com.quadraOuro.domain.models;

import java.time.Instant;

public class User {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private Instant createdAt;
    private Instant updatedAt;

    public User(Long id, String name, String email, UserRole role, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public UserRole getRole() { return role; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
