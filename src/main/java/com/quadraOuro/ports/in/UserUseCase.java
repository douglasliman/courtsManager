package com.quadraOuro.ports.in;

import com.quadraOuro.domain.models.User;
import com.quadraOuro.domain.models.UserRole;

import java.util.List;
import java.util.Optional;

import com.quadraOuro.adapters.web.dto.request.UserRequest;
import com.quadraOuro.adapters.web.dto.response.UserResponse;

public interface UserUseCase {
    List<User> findByRole(UserRole role);
    Optional<User> findById(Long id);
    User save(User user);
    List<User> findAll();
    boolean existsByEmail(String email);

    UserResponse createUserFromRequest(UserRequest request);
}
