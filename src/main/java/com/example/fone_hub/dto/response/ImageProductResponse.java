package com.example.fone_hub.dto.response;

import com.example.fone_hub.entity.Product;

public record ImageProductResponse(
        Long id,
        String imageLink,
        Product product
) {
}
