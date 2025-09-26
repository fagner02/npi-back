package com.example.demo.controller;

import com.example.demo.dto.CreateProductDTO;
import com.example.demo.dto.DataResponse;
import com.example.demo.exceptions.ValidationError;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.demo.config.Image.detectMimeType;

@RestController
@RequestMapping("produtos")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping(path = "")
    public ResponseEntity<DataResponse> getAll(Pageable pageable, @RequestParam(required = false) String name) {
        return ResponseEntity.ok(
                new DataResponse(productService.findAll(pageable, name)));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DataResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(new DataResponse(productService.findById(id)));
    }

    @GetMapping(path = "/{id}/image")
    public ResponseEntity<byte[]> findByImage(@PathVariable Long id) {
        byte[] image = productService.getImage(id);
        String mimeType = detectMimeType(image);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(mimeType)).body(productService.getImage(id));
    }

    @PostMapping("")
    public ResponseEntity<DataResponse> save(@Valid @RequestBody CreateProductDTO product, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationError(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new DataResponse(productService.post(product)));
    }

    @PostMapping("/image")
    public ResponseEntity<DataResponse> uploadImage(@RequestParam("image") MultipartFile image, @RequestParam("id") Long id) throws IOException {
        productService.uploadImage(image, id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse> update(@PathVariable Long id, @Valid @RequestBody CreateProductDTO product, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationError(result);
        }
        productService.update(id, product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new DataResponse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse> deleteById(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new DataResponse(null));
    }
}
