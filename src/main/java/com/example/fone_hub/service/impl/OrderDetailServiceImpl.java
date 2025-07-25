package com.example.fone_hub.service.impl;
import com.example.fone_hub.dto.response.OrderDetailResponse;
import com.example.fone_hub.entity.Cart;
import com.example.fone_hub.entity.Order;
import com.example.fone_hub.entity.OrderDetail;
import com.example.fone_hub.entity.Product;
import com.example.fone_hub.mapper.OrderDetailMapper;
import com.example.fone_hub.repository.OrderDetailRepository;
import com.example.fone_hub.repository.OrderRepository;
import com.example.fone_hub.repository.ProductRepository;
import com.example.fone_hub.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OrderDetailServiceImpl implements OrderDetailService{
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Override
    public void AddItem(List<Cart> carts, Long orderId) {

        for(Cart cart : carts){
            OrderDetail newItem = new OrderDetail();

            Product product = productRepository.findById(cart.getProduct().getId()).get();

            Order order = orderRepository.findById(orderId).get();

            newItem.setQuantity(cart.getQuantity());
            newItem.setDiscount(product.getDiscount());
            newItem.setPrice(product.getPrice());
            newItem.setTotal((product.getPrice() * (100 - product.getDiscount()) / 100) * cart.getQuantity());
            newItem.setProduct(product);
            newItem.setOrder(order);

            orderDetailRepository.save(newItem);
        }

    }

    @Override
    public List<OrderDetailResponse> getItemByOrderId(Long orderId) {
        List<OrderDetailResponse> result = new ArrayList<>();

        for (OrderDetail detail : orderDetailRepository.findByOrderId(orderId)){
            result.add(orderDetailMapper.toOrderDetailResponse(detail));
        }
        return result;
    }
}