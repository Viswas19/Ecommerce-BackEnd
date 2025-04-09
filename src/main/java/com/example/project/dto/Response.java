package com.example.project.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class Response {
    private UserDto user;

    private String message;
    private List<UserDto>userList;
    private String token;
    private String role;
    private Integer totalPage;
    private Long totalElement;
    private String expirationTime;
    private AddressDto addressDto;
    private CategoryDto category;
    private List<CategoryDto> categoryList;
    private ProductDto product;
    private List<ProductDto> productList;
    private OrderItemDto orderItem;
    private List<OrderItemDto> orderItemList;
    private OrderDto order;
    private List<OrderDto> orderList;

}
