package com.example.fone_hub.dto.response;

import com.example.fone_hub.entity.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageResponse {
    Long id;
    String imageLink;
    Product product;
}


