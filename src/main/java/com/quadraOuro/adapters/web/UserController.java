
package com.quadraOuro.adapters.web;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quadraOuro.adapters.persistence.EnderecoRepository;
import com.quadraOuro.adapters.web.client.ViaCepClient;
import com.quadraOuro.adapters.web.dto.EnderecoResponse;
import com.quadraOuro.adapters.web.dto.request.UserRequest;
import com.quadraOuro.adapters.web.dto.response.UserResponse;
import com.quadraOuro.adapters.web.exception.CepInvalidoException;
import com.quadraOuro.adapters.web.exception.EmailJaEmUsoException;
import com.quadraOuro.domain.models.Endereco;
import com.quadraOuro.domain.models.User;
import com.quadraOuro.domain.models.UserRole;
import com.quadraOuro.ports.in.UserUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserUseCase userUseCase;
    private final ViaCepClient viaCepClient;
    private final EnderecoRepository enderecoRepository;

    public UserController(UserUseCase userUseCase, ViaCepClient viaCepClient, EnderecoRepository enderecoRepository) {
        this.userUseCase = userUseCase;
        this.viaCepClient = viaCepClient;
        this.enderecoRepository = enderecoRepository;
    }

    @GetMapping

    public ResponseEntity<List<UserResponse>> listUsers(
            @org.springframework.web.bind.annotation.RequestParam(value = "role", required = false) String role) {
        List<User> users;
        if (role != null) {
            try {
                UserRole userRole = UserRole.valueOf(role.toUpperCase());
                users = userUseCase.findByRole(userRole);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            users = userUseCase.findAll();
        }
        List<UserResponse> response = users.stream().map(this::mapToUserResponse).toList();
        return ResponseEntity.ok(response);
    }

    private UserResponse mapToUserResponse(User u) {
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

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
        // Verifica se o email já existe
        if (userUseCase.existsByEmail(request.email())) {
            throw new EmailJaEmUsoException("E-mail já está em uso");
        }

        // Buscar endereço pelo CEP
        Endereco endereco;
        try {
            endereco = enderecoRepository.findByCep(request.cep()).orElseGet(() -> {
                EnderecoResponse enderecoViaCep = viaCepClient.getEndereco(request.cep());
                EnderecoResponse enderecoResponse = new EnderecoResponse(
                        enderecoViaCep.cep(),
                        enderecoViaCep.logradouro(),
                        enderecoViaCep.bairro(),
                        enderecoViaCep.localidade(),
                        enderecoViaCep.uf(),
                        enderecoViaCep.estado(),
                        enderecoViaCep.regiao());
                if (enderecoResponse == null || enderecoResponse.cep() == null) {
                    throw new CepInvalidoException("CEP inválido");
                }
                Endereco novoEndereco = new Endereco();
                novoEndereco.setCep(enderecoResponse.cep());
                novoEndereco.setLogradouro(enderecoResponse.logradouro());
                novoEndereco.setBairro(enderecoResponse.bairro());
                novoEndereco.setLocalidade(enderecoResponse.localidade());
                novoEndereco.setUf(enderecoResponse.uf());
                novoEndereco.setEstado(enderecoResponse.estado());
                novoEndereco.setRegiao(enderecoResponse.regiao());
                return enderecoRepository.save(novoEndereco);
            });
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Aqui pode-se criar um DTO de erro customizado
        }

        User domain = new User(
                null,
                request.name(),
                request.email(),
                request.role(),
                null,
                null,
                endereco);

        User saved = userUseCase.save(domain);
        Endereco enderecoSalvo = saved.getEndereco();
        EnderecoResponse enderecoResponse = enderecoSalvo == null ? null
                : new EnderecoResponse(
                        enderecoSalvo.getCep(),
                        enderecoSalvo.getLogradouro(),
                        enderecoSalvo.getBairro(),
                        enderecoSalvo.getLocalidade(),
                        enderecoSalvo.getUf(),
                        enderecoSalvo.getEstado(),
                        enderecoSalvo.getRegiao());
        UserResponse response = new UserResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getRole(),
                enderecoResponse);

        return ResponseEntity
                .created(URI.create("/api/v1/users/" + saved.getId()))
                .body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EmailJaEmUsoException.class)
    public ResponseEntity<String> handleEmailJaEmUsoException(EmailJaEmUsoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
