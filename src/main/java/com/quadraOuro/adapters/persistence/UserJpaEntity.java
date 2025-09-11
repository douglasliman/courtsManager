package com.quadraOuro.adapters.persistence;

import com.quadraOuro.domain.models.UserRole;
import com.quadraOuro.domain.models.Endereco;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "users")
public class UserJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id", nullable = false)
    private Endereco endereco;

    // getters/setters
    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
