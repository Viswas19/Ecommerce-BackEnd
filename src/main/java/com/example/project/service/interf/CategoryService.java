package com.example.project.service.interf;

import com.example.project.dto.CategoryDto;
import com.example.project.dto.Response;

public interface CategoryService {
    Response createCategory(CategoryDto categoryRequest);
    Response updateCategory(Long categoryId, CategoryDto categoryRequest);
    Response getAllCategories();
    Response getCategoryById(Long categoryId);
    Response deleteCategory(Long categoryId);
}
