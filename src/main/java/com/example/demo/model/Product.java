package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank(message = "O nome não deve ser vazio")
    @NotNull(message = "O nome deve ser fornecido")
    public String name;

    public String description;

    @Positive(message = "O preço deve ser maior que 0")
    @NotNull(message = "O preço deve ser fornecido")
    public double price;

    @Min(value = 0, message = "A quantidade deve ser 0 ou mais")
    @NotNull(message = "A quantidade deve ser fornecida")
    public int quantity;

    @ManyToOne()
    @JoinColumn(name = "categoryId")
    @JsonIgnoreProperties("products")
    public Category category;

    public byte[] image;

    public Product() {
    }

}
