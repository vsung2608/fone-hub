package com.example.fone_hub.dto.response;

import com.example.fone_hub.enums.ProductStatus;

import java.time.LocalDate;

public record ProductResponse(
        Long id,
        String name,
        Long price,
        Long discount,
        String description,
        String color,
        Long quantity,
        LocalDate createDate,
        LocalDate updateDate,
        ProductStatus status,
        Long quantitySell
) {
}
