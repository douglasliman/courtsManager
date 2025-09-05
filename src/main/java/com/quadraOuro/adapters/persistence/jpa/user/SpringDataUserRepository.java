package com.quadraOuro.adapters.persistence.jpa.user;

import com.quadraOuro.adapters.persistence.UserJpaEntity;
import com.quadraOuro.domain.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, Long> {
    List<UserJpaEntity> findByRole(UserRole role);
}
