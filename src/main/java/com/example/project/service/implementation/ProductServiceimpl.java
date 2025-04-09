package com.example.project.service.implementation;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.project.Mapper.EntityDtoMapper;
import com.example.project.dto.ProductDto;
import com.example.project.dto.Response;
import com.example.project.entity.Category;
import com.example.project.entity.Product;
import com.example.project.repository.CategoryRepo;
import com.example.project.repository.ProductRepo;
import com.example.project.service.AwsS3Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceimpl  {


    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;
    private final AwsS3Service awsS3Service;


    public Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new NotFoundException("Category Not Found"));
        String productImageUrl = awsS3Service.saveImageToS3(image);

        Product product = new Product();
        product.setCategory(category);
        product.setPrice(price);
        product.setName(name);
        product.setDescription(description);
        product.setImageUrl(productImageUrl);

        productRepo.save(product);
        return Response.builder().message("Product Successfully Created").build();
    }


    public Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        Product product = productRepo.findById(productId).orElseThrow(()->new NotFoundException("Product Not Found"));
        Category category = null;
        String productImageUrl = null;
        if(categoryId!=null){
            category = categoryRepo.findById(categoryId).orElseThrow(()->new NotFoundException("Category Not Found"));
        }
        if(image!=null && !image.isEmpty()){
            productImageUrl = awsS3Service.saveImageToS3(image);
        }


        if(category!=null) product.setCategory(category);
        if(name!=null) product.setName(name);
        if(price!=null) product.setPrice(price);
        if(description!=null) product.setDescription(description);
        if(productImageUrl!=null) product.setImageUrl(productImageUrl);

        productRepo.save(product);
        return Response.builder().message("Product Updated Successfully").build();
    }


    public Response deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(()->new NotFoundException("Product Not Found"));
        productRepo.delete(product);
        return Response.builder().message("Product Deleted SuccessFully").build();
    }


    public Response getProductById(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(()->new NotFoundException("Product Not Found"));
        ProductDto productDto = entityDtoMapper.mapProductToDtoBasic(product);

        return Response.builder().product(productDto).build();
    }


    public Response getAllProducts() {
        List<ProductDto>productList = productRepo.findAll().stream().map(entityDtoMapper::mapProductToDtoBasic).collect(Collectors.toList());
        return  Response.builder().productList(productList).build();
    }


    public Response getProductsByCategory(Long categoryId) {
        List<Product> products = productRepo.findByCategoryId(categoryId);
        if(products.isEmpty()){
            throw new NotFoundException("No Products Found for this Category");
        }

        List<ProductDto> productDtoList = products.stream().map(entityDtoMapper::mapProductToDtoBasic).collect(Collectors.toList());

        return Response.builder().productList(productDtoList).build();
    }


    public Response searchProduct(String searchValue) {
        List<Product>products = productRepo.findByNameContainingOrDescriptionContaining(searchValue,searchValue);
        if(products.isEmpty()){
            throw new NotFoundException("No Products Found");
        }
        List<ProductDto> productDtoList = products.stream().map(entityDtoMapper::mapProductToDtoBasic).collect(Collectors.toList());

        return Response.builder().productList(productDtoList).build();
    }
}
