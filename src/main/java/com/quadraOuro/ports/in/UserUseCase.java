package com.quadraOuro.ports.in;

import com.quadraOuro.domain.models.User;
import com.quadraOuro.domain.models.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    List<User> findByRole(UserRole role);
    Optional<User> findById(Long id);
    User save(User user);
    List<User> findAll();
    boolean existsByEmail(String email);
}
