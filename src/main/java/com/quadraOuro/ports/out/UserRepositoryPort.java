/**
 * Porta de saída para persistência de usuários.
 * Define o contrato que qualquer implementação de repositório de usuário deve seguir (ex: JPA, memória, etc).
 * Permite desacoplar o domínio da tecnologia de persistência.
 */

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
    boolean existsByEmail(String email);
}
