package com.example.demo.service;

import com.example.demo.dto.CreateCategoryDTO;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Category;
import com.example.demo.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.example.demo.specifications.CategorySpecification.hasNameLike;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    public Page<Category> findAll(Pageable pageable, String name) {
        Specification<Category> specification = Specification.allOf(hasNameLike(name));
        return categoryRepo.findAll(specification, pageable);
    }

    public Category findById(Long id) {
        return categoryRepo.findById(id).orElseThrow(() -> new NotFoundException("Categoria"));
    }

    public Category post(CreateCategoryDTO category) {
        if (categoryRepo.existsByName(category.name)) {
            throw new IllegalArgumentException("O nome já existe");
        }
        Category newCategory = new Category();
        newCategory.name = category.name;
        newCategory.description = category.description;
        return categoryRepo.save(newCategory);
    }

    public void update(Long id, CreateCategoryDTO category) {
        Category old = categoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria"));
        if (!Objects.equals(old.name, category.name) && categoryRepo.existsByName(category.name)) {
            throw new IllegalArgumentException("O nome já existe");
        }
        Category newCategory = new Category();
        newCategory.id = id;
        newCategory.name = category.name;
        newCategory.description = category.description;
        categoryRepo.save(newCategory);
    }

    public void delete(Long id) {
        if (!categoryRepo.existsById(id)) {
            throw new NotFoundException("Categoria");
        }
        categoryRepo.deleteById(id);
    }
}
