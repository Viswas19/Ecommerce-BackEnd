package com.example.project.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemDto {

    private Long id;
    private int quantity;
    private BigDecimal price;
    private String  status;
    private  UserDto user;
    private ProductDto product;
    private LocalDateTime createdAt;
}
