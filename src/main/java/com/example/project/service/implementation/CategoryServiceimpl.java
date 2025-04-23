package com.example.project.service.implementation;

import com.example.project.Mapper.EntityDtoMapper;
import com.example.project.dto.CategoryDto;
import com.example.project.dto.Response;
import com.example.project.entity.Category;
import com.example.project.exception.NotFoundException;
import com.example.project.repository.CategoryRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryServiceimpl  {




        private final CategoryRepo categoryRepo;
       private final EntityDtoMapper entityDtoMapper;




        public Response createCategory(CategoryDto categoryRequest) {
            Category category = new Category();
            category.setName(categoryRequest.getName());
            categoryRepo.save(category);
            return Response.builder().message("Category created successfully").build();
        }


        public Response updateCategory(Long categoryId, CategoryDto categoryRequest) {
            Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"));
            category.setName(categoryRequest.getName());
            categoryRepo.save(category);
            return Response.builder().message("category updated successfully").build();
        }


        public Response getAllCategories() {
            List<Category> categories = categoryRepo.findAll();
            List<CategoryDto> categoryDtoList = categories.stream().map(entityDtoMapper::mapCategoryToDtoBasic).collect(Collectors.toList());

            return Response.builder().categoryList(categoryDtoList).build();
        }




        public Response getCategoryById(Long categoryId) {
            Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"));
            CategoryDto categoryDto = entityDtoMapper.mapCategoryToDtoBasic(category);
            return Response.builder().category(categoryDto).build();
        }


        public Response deleteCategory(Long categoryId) {
            Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"));
            categoryRepo.delete(category);
            return Response.builder().message("Category was deleted successfully").build();
        }
    }

