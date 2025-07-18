package com.example.fone_hub.mapper;

import com.example.fone_hub.dto.response.ImageProductResponse;
import com.example.fone_hub.entity.ImageProduct;
import org.springframework.stereotype.Service;

@Service
public class ImageProductMapper {

    public ImageProductResponse imageProductToImageProductResponse(ImageProduct imageProduct) {
        return new ImageProductResponse(imageProduct.getId(), imageProduct.getImageLink(), imageProduct.getProduct());
    }
}
