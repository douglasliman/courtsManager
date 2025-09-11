package com.quadraOuro.adapters.web.mapper;

import com.quadraOuro.domain.models.User;
import com.quadraOuro.domain.models.Endereco;
import com.quadraOuro.adapters.web.dto.response.UserResponse;
import com.quadraOuro.adapters.web.dto.EnderecoResponse;

public class UserMapper {
    public static UserResponse toResponse(User u) {
        Endereco e = u.getEndereco();
        EnderecoResponse enderecoResponse = e == null ? null
                : new EnderecoResponse(
                        e.getCep(),
                        e.getLogradouro(),
                        e.getBairro(),
                        e.getLocalidade(),
                        e.getUf(),
                        e.getEstado(),
                        e.getRegiao());
        return new UserResponse(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getRole(),
                enderecoResponse);
    }
}
