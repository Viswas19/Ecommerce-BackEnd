package com.example.project.controller;


import com.example.project.dto.Response;
import com.example.project.exception.InvalidCredentialsException;
import com.example.project.service.implementation.ProductServiceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceimpl productServiceimpl;


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createProduct(@RequestParam Long categoryId, @RequestParam MultipartFile image, @RequestParam String name, @RequestParam String description, @RequestParam BigDecimal price){
        if (categoryId == null || image.isEmpty() || name.isEmpty() || description.isEmpty() || price == null){
            throw new InvalidCredentialsException("All Fields are Required");
        }
        return ResponseEntity.ok(productServiceimpl.createProduct(categoryId, image, name, description, price));
    }


    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProduct(@RequestParam Long productId, @RequestParam(required = false) Long categoryId, @RequestParam(required = false) MultipartFile image, @RequestParam(required = false)  String name, @RequestParam(required = false)  String description, @RequestParam(required = false) BigDecimal price){
        return ResponseEntity.ok(productServiceimpl.updateProduct(productId, categoryId, image, name, description, price));
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long productId){
        return ResponseEntity.ok(productServiceimpl.deleteProduct(productId));

    }


    @GetMapping("/{productId}")
    public ResponseEntity<Response> getProductById(@PathVariable Long productId){
        return ResponseEntity.ok(productServiceimpl.getProductById(productId));
    }

    @GetMapping
    public ResponseEntity<Response> getAllProducts(){
        return ResponseEntity.ok(productServiceimpl.getAllProducts());
    }


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Response> getProductsByCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(productServiceimpl.getProductsByCategory(categoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchForProduct(@RequestParam String searchValue){
        return ResponseEntity.ok(productServiceimpl.searchProduct(searchValue));
    }
}
