
package com.quadraOuro.adapters.web;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quadraOuro.adapters.web.mapper.UserMapper;
import com.quadraOuro.adapters.web.dto.request.UserRequest;
import com.quadraOuro.adapters.web.dto.response.UserResponse;
import com.quadraOuro.adapters.web.exception.EmailJaEmUsoException;
import com.quadraOuro.domain.models.User;
import com.quadraOuro.domain.models.UserRole;
import com.quadraOuro.ports.in.UserUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listUsers(
            @RequestParam(value = "role", required = false) String role) {
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
    List<UserResponse> response = users.stream().map(UserMapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
        UserResponse response = userUseCase.createUserFromRequest(request);
        return ResponseEntity.created(URI.create("/api/v1/users/" + response.id())).body(response);
    }

    @ExceptionHandler(EmailJaEmUsoException.class)
    public ResponseEntity<String> handleEmailJaEmUsoException(EmailJaEmUsoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
