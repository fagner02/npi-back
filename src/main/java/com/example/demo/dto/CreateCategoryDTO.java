package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCategoryDTO {
    @NotBlank(message = "O nome não deve estar vazio")
    @NotNull(message = "O nome deve ser fornecido")
    public String name;
    public String description;
}
