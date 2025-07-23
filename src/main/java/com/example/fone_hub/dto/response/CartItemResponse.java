package com.example.fone_hub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemResponse {
    String productName;
    int quantity;
    long price;
    String imageLink;
}
