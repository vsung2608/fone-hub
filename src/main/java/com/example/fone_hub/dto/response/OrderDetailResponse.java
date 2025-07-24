package com.example.fone_hub.dto.response;



import com.example.fone_hub.entity.Order;
import com.example.fone_hub.entity.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    Long id;
    Long quantity;
    Long price;
    Long discount;
    Long total;
    Order order;
    Product product;
}