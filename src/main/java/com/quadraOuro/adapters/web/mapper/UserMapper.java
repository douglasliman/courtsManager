package com.quadraOuro.adapters.web.mapper;

import com.quadraOuro.domain.models.User;
import com.quadraOuro.domain.models.Endereco;
import com.quadraOuro.adapters.web.dto.response.UserResponse;
import com.quadraOuro.adapters.web.dto.EnderecoResponse;

public class UserMapper {
    private UserMapper() {}

    public static EnderecoResponse toEnderecoResponse(Endereco e) {
        if (e == null) return null;
        return new EnderecoResponse(
                e.getCep(),
                e.getLogradouro(),
                e.getBairro(),
                e.getLocalidade(),
                e.getUf(),
                e.getEstado(),
                e.getRegiao()
        );
    }

    public static UserResponse toResponse(User u) {
        return new UserResponse(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getRole(),
                toEnderecoResponse(u.getEndereco())
        );
    }
}
