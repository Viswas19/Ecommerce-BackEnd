package com.example.project.repository;

import com.example.project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);
}
