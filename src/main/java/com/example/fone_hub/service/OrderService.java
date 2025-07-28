package com.example.fone_hub.service;

import com.example.fone_hub.dto.response.DailyRevenue;
import com.example.fone_hub.dto.response.OrderResponse;
import com.example.fone_hub.entity.User;
import com.example.fone_hub.enums.OrderStatus;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface OrderService {
    // View count Order
    Long getCount();

    Long getIncrease();

    // View all Order
    List<OrderResponse> getAllOrder();

    // Get Order By orderId;
    OrderResponse getOrderById(Long orderId);

    // AddOrder
    Long AddOrder(User user);

    // Update ToTal Price
    void UpdateToTalPrice(Long orderId);

    // Get Order By UserId
    List<OrderResponse> historyBuy(Long userId);

    // Update Status Order
    void updateStatusOrder(Long orderId, OrderStatus status);

    List<DailyRevenue> getRevenueByDay(LocalDate startDate, LocalDate endDate);

}