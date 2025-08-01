package com.example.fone_hub.dto.request;

import com.example.fone_hub.entity.product_spec.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String name;
    Long price;
    Long discount;
    String color;
    Long quantity;
    Long brandId;
    Long categoryId;
    GoodsInformation goodsInformation;
    Design design;
    Cpu cpu;
    Display display;
    Storage storage;
    Camera camera;
    SelfieCamera selfieCamera;
    Connectivity connectivity;
    Battery battery;
}
