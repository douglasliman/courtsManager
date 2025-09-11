package com.quadraOuro.adapters.persistence.jpa.user;

import com.quadraOuro.adapters.persistence.UserJpaEntity;
import com.quadraOuro.domain.models.User;

public class UserMapper {
	public static User toDomain(UserJpaEntity e) {
		return new User(
				e.getId(),
				e.getName(),
				e.getEmail(),
				e.getRole(),
				e.getCreatedAt(),
				e.getUpdatedAt(),
				e.getEndereco()
		);
	}
}
