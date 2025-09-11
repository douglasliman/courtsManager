package com.quadraOuro.application;
import org.springframework.stereotype.Service;

import com.quadraOuro.domain.models.User;
import com.quadraOuro.domain.models.UserRole;

import com.quadraOuro.ports.in.UserUseCase;
import com.quadraOuro.ports.out.UserRepositoryPort;
import com.quadraOuro.adapters.web.dto.request.UserRequest;
import com.quadraOuro.adapters.web.dto.response.UserResponse;
import com.quadraOuro.adapters.web.dto.EnderecoResponse;
import com.quadraOuro.adapters.web.mapper.UserMapper;
import com.quadraOuro.adapters.external.ViaCepClient;
import com.quadraOuro.adapters.persistence.EnderecoRepository;
import com.quadraOuro.domain.models.Endereco;
import com.quadraOuro.adapters.web.exception.CepInvalidoException;
import com.quadraOuro.domain.exception.EnderecoNotFoundException;
import com.quadraOuro.adapters.web.exception.EmailJaEmUsoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import com.quadraOuro.domain.exception.UserNotFoundException;

@Service
public class UserService implements UserUseCase {

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    private final UserRepositoryPort repository;
    private final ViaCepClient viaCepClient;
    private final EnderecoRepository enderecoRepository;

    @Autowired
    public UserService(UserRepositoryPort repository, ViaCepClient viaCepClient, EnderecoRepository enderecoRepository) {
        this.repository = repository;
        this.viaCepClient = viaCepClient;
        this.enderecoRepository = enderecoRepository;
    }
    @Override
    @Transactional
    public UserResponse createUserFromRequest(UserRequest request) {
        // Verifica se o email já existe
        if (existsByEmail(request.email())) {
            throw new EmailJaEmUsoException("E-mail já está em uso");
        }

        // Buscar endereço pelo CEP
        Endereco endereco;
        endereco = enderecoRepository.findByCep(request.cep()).orElseGet(() -> {
            EnderecoResponse enderecoViaCep = viaCepClient.getEndereco(request.cep());
            EnderecoResponse enderecoResponse = enderecoViaCep;
            if (enderecoResponse == null || enderecoResponse.cep() == null) {
                throw new EnderecoNotFoundException(request.cep());
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

        User domain = new User(
                null,
                request.name(),
                request.email(),
                request.role(),
                null,
                null,
                endereco);

        User saved = save(domain);
        Endereco enderecoSalvo = saved.getEndereco();
    EnderecoResponse enderecoResponse = UserMapper.toEnderecoResponse(enderecoSalvo);
    return new UserResponse(
        saved.getId(),
        saved.getName(),
        saved.getEmail(),
        saved.getRole(),
        enderecoResponse);
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return repository.findByRole(role);
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}