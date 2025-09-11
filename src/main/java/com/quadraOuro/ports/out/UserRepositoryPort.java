package com.quadraOuro.ports.out;

import com.quadraOuro.domain.models.User;
import com.quadraOuro.domain.models.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    List<User> findByRole(UserRole role);
    Optional<User> findById(Long id);
    User save(User user);
    List<User> findAll();
}
