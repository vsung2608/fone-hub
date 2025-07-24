package com.example.fone_hub.dto.request;

import com.example.fone_hub.entity.Order;
import com.example.fone_hub.entity.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequest {
    Long id;
    Long quantity;
    Order order;
    Product product;
}
