package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateProductDTO {
    @NotBlank(message = "O nome não deve estar vazio")
    @NotNull(message = "O nome deve ser fornecido")
    public String name;

    public String description;

    @NotNull(message = "O id da categoria deve ser fornecido")
    public Long categoryId;

    @Positive(message = "O preço deve ser maior que 0")
    @NotNull(message = "O preço deve ser fornecido")
    public double price;

    @Min(value = 0, message = "A quantidade deve ser 0 ou mais")
    @NotNull(message = "A quantidade deve ser fornecida")
    public int quantity;
}
