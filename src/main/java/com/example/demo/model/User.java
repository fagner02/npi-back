package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull(message = "O nome de usuário deve ser fornecido")
    @NotBlank(message = "O nome de usuário não deve estar vazio")
    public String username;

    @NotNull(message = "O email deve ser fornecido")
    @NotBlank(message = "O email não deve estar vazio")
    public String email;

    @NotNull(message = "O senha deve ser fornecido")
    @NotBlank(message = "O senha não deve estar vazio")
    public String password;
}
