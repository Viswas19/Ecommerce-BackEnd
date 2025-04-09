package com.example.project.service.implementation;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.project.Mapper.EntityDtoMapper;
import com.example.project.dto.OrderItemDto;
import com.example.project.dto.OrderRequest;
import com.example.project.dto.Response;
import com.example.project.entity.Order;
import com.example.project.entity.OrderItem;
import com.example.project.entity.Product;
import com.example.project.entity.User;
import com.example.project.enums.OrderStatus;
import com.example.project.repository.OrderItemRepo;
import com.example.project.repository.OrderRepo;
import com.example.project.repository.ProductRepo;

import com.example.project.specification.OrderItemSpecification;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;



import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemimpl  {

    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;
    private final UserServiceimpl userServiceimpl;
    private final EntityDtoMapper entityDtoMapper;




    public Response placeOrder(OrderRequest orderRequest) {
        User user = userServiceimpl.getLoginUser();
        List<OrderItem> orderItems = orderRequest.getItems().stream().map(orderItemRequest -> {
            Product product = productRepo.findById(orderItemRequest.getProductId()).orElseThrow(()->new NotFoundException("Product Not Found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity())));
            orderItem.setStatus(OrderStatus.PENDING);
            orderItem.setUser(user);
            return orderItem;
        }).collect(Collectors.toList());

        BigDecimal totalPrice = orderRequest.getTotalPrice()!=null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO)>0 ? orderRequest.getTotalPrice() : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add);

        Order order = new Order();
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);

        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        orderRepo.save(order);

        return Response.builder().message("Order Placed Successfully").build();
    }


    public Response updateOrderItemStatus(Long orderItemId, String status) {
        OrderItem orderItem = orderItemRepo.findById(orderItemId).orElseThrow(()->new NotFoundException("Order Item Not Found"));

        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderItemRepo.save(orderItem);
        return Response.builder().message("Order Status Updated Successfully").build();
    }


    public Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable) {
        Specification<OrderItem> spec = Specification.where(OrderItemSpecification.hasStatus(status))
                .and(OrderItemSpecification.createdBetween(startDate,endDate)).and(OrderItemSpecification.hasItemId(itemId));

        Page<OrderItem> orderItemPage = orderItemRepo.findAll(spec,pageable);

        if(orderItemPage.isEmpty()){
            throw new NotFoundException("No Order Found");
        }

        List<OrderItemDto> orderItemDtos = orderItemPage.getContent().stream().map(entityDtoMapper::mapOrderItemToDtoPlusProductAndUser).collect(Collectors.toList());

        return Response.builder().orderItemList(orderItemDtos).totalPage(orderItemPage.getTotalPages()).totalElement(orderItemPage.getTotalElements()).build();

    }
}
