package com.example.demo.service;

import com.example.demo.dto.CreateProductDTO;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.demo.specifications.ProductSpecification.hasNameLike;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;

    @Autowired
    CategoryRepo categoryRepo;

    public Page<Product> findAll(Pageable pageable, String name) {
        Specification<Product> specification = Specification.allOf(hasNameLike(name));
        return productRepo.findAll(specification, pageable);
    }

    public Product findById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new NotFoundException("Produto"));
    }

    public void uploadImage(MultipartFile file, Long id) throws IOException {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto"));
        try {
            product.image = file.getBytes();
        } catch (IOException e) {
            throw new IOException("Erro ao carregar imagem");
        }
        productRepo.save(product);
    }

    public byte[] getImage(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new NotFoundException("Produto"));
        return product.image;
    }

    public Product post(CreateProductDTO product) {
        if (!categoryRepo.existsById(product.categoryId)) {
            throw new IllegalArgumentException("Categoria não existente");
        }

        Product newProduct = new Product();
        return getProduct(product, newProduct);
    }

    private Product getProduct(CreateProductDTO product, Product newProduct) {
        newProduct.category = new Category();
        newProduct.category.id = product.categoryId;
        newProduct.name = product.name;
        newProduct.description = product.description;
        newProduct.price = product.price;
        newProduct.quantity = product.quantity;
        newProduct.image = product.image;
        return productRepo.save(newProduct);
    }

    public void update(Long id, CreateProductDTO product) {
        productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto"));
        if (!categoryRepo.existsById(product.categoryId)) {
            throw new IllegalArgumentException("Categoria não existente");
        }

        Product newProduct = new Product();
        newProduct.id = id;
        getProduct(product, newProduct);
    }

    public void delete(Long id) {
        if (!productRepo.existsById(id)) {
            throw new NotFoundException("Produto");
        }
        productRepo.deleteById(id);
    }
}
