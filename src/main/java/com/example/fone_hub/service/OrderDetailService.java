package com.example.fone_hub.service;
import com.example.fone_hub.dto.response.OrderDetailResponse;
import com.example.fone_hub.entity.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {
    // Add Item
    void AddItem(List<Cart> carts, Long orderId);

    // Get Item By Order Id
    List<OrderDetailResponse> getItemByOrderId(Long orderId);
}
