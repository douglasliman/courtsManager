
package com.quadraOuro.application;

import com.quadraOuro.domain.models.User;
import com.quadraOuro.domain.models.UserRole;
import com.quadraOuro.ports.in.UserUseCase;
import com.quadraOuro.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserUseCase {

    private final UserRepositoryPort repository;

    public UserService(UserRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return repository.findByRole(role);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}