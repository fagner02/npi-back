package com.example.demo.controller;

import com.example.demo.config.JwtUtil;
import com.example.demo.dto.CreateUserDTO;
import com.example.demo.dto.DataResponse;
import com.example.demo.dto.LoginDTO;
import com.example.demo.exceptions.ValidationError;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationError(result);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.email,
                loginDTO.password
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(loginDTO.email), "username", userService.findByEmail(loginDTO.email).username));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<DataResponse> register(@Valid @RequestBody CreateUserDTO createUserDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationError(result);
        }
        userService.post(createUserDTO);
        return ResponseEntity.ok(new DataResponse(""));
    }

    @GetMapping(path = "")
    public ResponseEntity<DataResponse> getAll(Pageable pageable, @RequestParam String name) {
        return ResponseEntity.ok(
                new DataResponse(userService.findAll(pageable, name)));
    }

    @PostMapping("")
    public ResponseEntity<DataResponse> save(@Valid @RequestBody CreateUserDTO user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationError(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new DataResponse(userService.post(user)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse> update(@PathVariable Long id, @Valid @RequestBody CreateUserDTO user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationError(result);
        }
        userService.update(id, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new DataResponse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse> deleteById(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new DataResponse(null));
    }
}
