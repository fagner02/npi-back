package com.example.demo.repo;

import com.example.demo.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    @NonNull
    Page<Category> findAll(@NonNull Pageable pageable);

    boolean existsByName(String name);
}
