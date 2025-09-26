package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateUserDTO {
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
