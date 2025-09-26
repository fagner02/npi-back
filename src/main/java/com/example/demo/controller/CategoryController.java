package com.example.demo.controller;

import com.example.demo.dto.CreateCategoryDTO;
import com.example.demo.dto.DataResponse;
import com.example.demo.exceptions.ValidationError;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/categorias")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping(path = "")
    public ResponseEntity<DataResponse> getAll(Pageable pageable, @RequestParam(required = false) String name) {
        return ResponseEntity.ok(new DataResponse(categoryService.findAll(pageable, name)));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DataResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(new DataResponse(categoryService.findById(id)));
    }

    @PostMapping("")
    public ResponseEntity<DataResponse> save(@Valid @RequestBody CreateCategoryDTO category, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationError(result);
        }
        return ResponseEntity.ok(new DataResponse(categoryService.post(category)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody CreateCategoryDTO category, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationError(result);
        }
        categoryService.update(id, category);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new DataResponse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new DataResponse(null));
    }
}
