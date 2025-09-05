package com.quadraOuro.adapters.web;


import com.quadraOuro.adapters.web.dto.request.UserRequest;
import com.quadraOuro.adapters.web.dto.response.UserResponse;
import com.quadraOuro.domain.models.User;
import com.quadraOuro.domain.models.UserRole;
import com.quadraOuro.ports.in.UserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping("/admins")
    public ResponseEntity<List<UserResponse>> listAdmins() {
        var users = userUseCase.findByRole(UserRole.ADMIN)
                .stream().map(u -> new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole()))
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<UserResponse>> listFuncionarios() {
        var users = userUseCase.findByRole(UserRole.FUNCIONARIO)
                .stream().map(u -> new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole()))
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UserResponse>> listUsuarios() {
        var users = userUseCase.findByRole(UserRole.USUARIO)
                .stream().map(u -> new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole()))
                .toList();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
        var domain = new User(
                null,
                request.name(),
                request.email(),
                request.role(),
                null,
                null
        );

        var saved = userUseCase.save(domain);
        var response = new UserResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getRole());

        return ResponseEntity
                .created(URI.create("/api/v1/users/" + saved.getId()))
                .body(response);
    }
}
