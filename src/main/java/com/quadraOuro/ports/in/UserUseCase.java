/**
 * Porta de entrada (Application Service) para casos de uso relacionados a usuários.
 * Define as operações disponíveis para a aplicação manipular usuários, sem expor detalhes de infraestrutura.
 */
package com.quadraOuro.ports.in;

import com.quadraOuro.domain.models.User;
import com.quadraOuro.domain.models.UserRole;

import java.util.List;
import java.util.Optional;

import com.quadraOuro.adapters.web.dto.request.UserRequest;
import com.quadraOuro.adapters.web.dto.response.UserResponse;

public interface UserUseCase {
    List<User> findByRole(UserRole role);
    User findById(Long id);
    User save(User user);
    List<User> findAll();
    boolean existsByEmail(String email);
    UserResponse createUserFromRequest(UserRequest request);
}
