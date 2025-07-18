package com.example.fone_hub.dto.request;

import com.example.fone_hub.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDate;

public record ProductRequest(
        String name,
        Long price,
        Long discount,
        String description,
        String color,
        Long quantity,
        ProductStatus status,
        Long quantitySell,
        Long brandId,
        Long categoryId
) {
}
