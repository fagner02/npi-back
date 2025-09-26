package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(unique = true, nullable = false)
    @NotBlank(message = "O nome não deve ser vazio")
    @NotNull(message = "O nome deve ser fornecido")
    public String name;
    public String description;
    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties("category")
    public List<Product> products;

    public Category() {
    }
}
