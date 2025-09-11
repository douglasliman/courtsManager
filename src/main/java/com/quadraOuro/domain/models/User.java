package com.quadraOuro.domain.models;

import java.time.Instant;

public class User {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private Instant createdAt;
    private Instant updatedAt;
    private Endereco endereco;

    public User(Long id, String name, String email, UserRole role, Instant createdAt, Instant updatedAt, Endereco endereco) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.endereco = endereco;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public UserRole getRole() { return role; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
}
