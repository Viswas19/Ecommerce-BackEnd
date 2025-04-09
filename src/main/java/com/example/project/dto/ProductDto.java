package com.example.project.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
    private Long id;
    private String name;
    private  String description;
    private BigDecimal price;
    private String imageUrl;
    private CategoryDto category;
}
