package com.quadraOuro.adapters.persistence.jpa.user;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.quadraOuro.adapters.persistence.UserJpaEntity;
import com.quadraOuro.domain.models.User;
import com.quadraOuro.domain.models.UserRole;
import com.quadraOuro.ports.out.UserRepositoryPort;

@Component
public class UserJpaAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository repo;

    public UserJpaAdapter(SpringDataUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return repo.findByRole(role).stream().map(UserMapper::toDomain).toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return repo.findById(id).map(UserMapper::toDomain);
    }

    @Override
    @Transactional
    public User save(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setRole(user.getRole());

        if (user.getCreatedAt() == null) {
            entity.setCreatedAt(Instant.now());
        } else {
            entity.setCreatedAt(user.getCreatedAt());
        }
        entity.setUpdatedAt(Instant.now());

        // Mapear endereço
        entity.setEndereco(user.getEndereco());

        return UserMapper.toDomain(repo.save(entity));
    }

    @Override
    public List<User> findAll() {
        return repo.findAll().stream().map(UserMapper::toDomain).toList();
    }
}
