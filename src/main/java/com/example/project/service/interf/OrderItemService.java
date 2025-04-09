package com.example.project.service.interf;

import com.example.project.dto.OrderRequest;
import com.example.project.dto.Response;
import com.example.project.enums.OrderStatus;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public interface OrderItemService {
    Response placeOrder(OrderRequest orderRequest);
    Response updateOrderItemStatus(Long orderItemId, String status);
    Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable);
}
