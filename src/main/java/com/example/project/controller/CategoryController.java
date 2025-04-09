package com.example.project.controller;


import com.example.project.dto.CategoryDto;
import com.example.project.dto.Response;
import com.example.project.service.implementation.CategoryServiceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceimpl categoryServiceimpl;

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createCategory(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryServiceimpl.createCategory(categoryDto));
    }

    @GetMapping("")
    public ResponseEntity<Response> getAllCategories(){
        return ResponseEntity.ok(categoryServiceimpl.getAllCategories());
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryServiceimpl.updateCategory(categoryId, categoryDto));
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryServiceimpl.deleteCategory(categoryId));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Response> getCategoryById(@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryServiceimpl.getCategoryById(categoryId));
    }




}
