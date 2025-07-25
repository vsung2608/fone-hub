package com.example.fone_hub.dto.response;

import com.example.fone_hub.entity.Brand;
import com.example.fone_hub.entity.Category;
import com.example.fone_hub.entity.ImageProduct;
import com.example.fone_hub.entity.product_spec.*;
import com.example.fone_hub.enums.ProductStatus;

import java.time.LocalDate;
import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        Long price,
        Long discount,
        String color,
        Long quantity,
        LocalDate createDate,
        LocalDate updateDate,
        ProductStatus status,
        Long quantitySell,
        CategoryResponse brand,
        BrandResponse category,
        List<ImageProduct> images,
        GoodsInformation goodsInformation,
        Design design,
        Cpu cpu,
        Display display,
        Storage storage,
        Camera camera,
        SelfieCamera selfieCamera,
        Connectivity connectivity,
        Battery battery,
        String imageDescription
) {
}
