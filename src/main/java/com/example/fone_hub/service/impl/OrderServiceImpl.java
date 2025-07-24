package com.example.fone_hub.service.impl;
import com.example.fone_hub.dto.response.DailyRevenue;
import com.example.fone_hub.dto.response.OrderResponse;
import com.example.fone_hub.entity.Order;
import com.example.fone_hub.entity.OrderDetail;
import com.example.fone_hub.entity.User;
import com.example.fone_hub.enums.OrderStatus;
import com.example.fone_hub.mapper.OrderMapper;
import com.example.fone_hub.repository.OrderDetailRepository;
import com.example.fone_hub.repository.OrderRepository;
import com.example.fone_hub.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Long getCount(){
        return orderRepository.count();
    }

    @Override
    public Long getIncrease(){
        return orderRepository.findAll()
                .stream()
                .mapToLong(order -> order.getTotal() != null ? order.getTotal() : 0L)
                .sum();
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        List<OrderResponse> result = new ArrayList<>();

        for(Order order : orderRepository.findAll()){
            if (order.getTotal() == null) {
                order.setTotal(0L);
                orderRepository.save(order);
            }
            result.add(orderMapper.toOrderResponse(order));
        }
        return result;
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        if (order.getTotal() == null) {
            order.setTotal(0L);
            orderRepository.save(order);
        }
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public Long AddOrder(User user) {
        Order order = new Order();

        order.setDate(LocalDate.now());
        order.setUser(user);
        order.setTotal(0L);
        order.setQuantity(0L);
        order.setStatus(OrderStatus.PENDING);

        orderRepository.save(order);

        return order.getId();
    }

    @Override
    public void UpdateToTalPrice(Long orderId) {
        Long totalPrice = 0L;
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        for(OrderDetail item : orderDetails){
            totalPrice += item.getTotal();
        }

        Order order = orderRepository.findById(orderId).get();

        order.setTotal(totalPrice);
        order.setQuantity(Long.valueOf(orderDetails.size()));

        orderRepository.save(order);
    }

    @Override
    public List<OrderResponse> historyBuy(Long userId) {
        List<OrderResponse> result = new ArrayList<>();

        for(Order order : orderRepository.findByUserId(userId)){
            result.add(orderMapper.toOrderResponse(order));
        }
        return result;
    }

    @Override
    public void updateStatusOrder(Long orderId, OrderStatus status){
        Order order = orderRepository.findById(orderId).get();

        order.setStatus(status);

        orderRepository.save(order);
    }

    @Override
    public List<DailyRevenue> getRevenueByDay(LocalDate startDate, LocalDate endDate) {
        // Chuyển đổi LocalDate sang LocalDateTime
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Object[]> rawData = orderRepository.findRevenueByDay(startDateTime, endDateTime);
        List<DailyRevenue> dailyRevenues = new ArrayList<>();

        for (Object[] row : rawData) {
            String date = ((Date) row[0]).toString();
            BigDecimal revenue = (BigDecimal) row[1];
            dailyRevenues.add(new DailyRevenue(date, revenue));
        }

        return dailyRevenues;
    }
}
