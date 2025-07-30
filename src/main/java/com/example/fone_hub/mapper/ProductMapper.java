package com.example.fone_hub.mapper;

import com.example.fone_hub.dto.request.ProductRequest;
import com.example.fone_hub.dto.response.BrandResponse;
import com.example.fone_hub.dto.response.CategoryResponse;
import com.example.fone_hub.dto.response.ProductResponse;
import com.example.fone_hub.entity.Product;
import com.example.fone_hub.enums.ProductStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public ProductResponse productToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDiscount(),
                product.getColor(),
                product.getQuantity(),
                product.getCreateDate(),
                product.getUpdateDate(),
                product.getStatus(),
                product.getQuantitySell(),
                new CategoryResponse(
                        product.getCategory().getId(),
                        product.getCategory().getName(),
                        product.getCategory().getDescription()
                ),
                new BrandResponse(
                        product.getBrand().getId(),
                        product.getBrand().getName(),
                        product.getBrand().getImage(),
                        product.getBrand().getStatus()
                ),
                product.getImages(),
                product.getGoodsInformation(),
                product.getDesign(),
                product.getCpu(),
                product.getDisplay(),
                product.getStorage(),
                product.getCamera(),
                product.getSelfieCamera(),
                product.getConnectivity(),
                product.getBattery(),
                product.getImageDescription()
        );
    }

    public Product productRequestToProduct(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .discount(request.getDiscount())
                .color(request.getColor())
                .quantity(request.getQuantity())
                .status(ProductStatus.ACTIVE)
                .quantitySell(0L)
                .goodsInformation(request.getGoodsInformation())
                .design(request.getDesign())
                .cpu(request.getCpu())
                .display(request.getDisplay())
                .storage(request.getStorage())
                .camera(request.getCamera())
                .selfieCamera(request.getSelfieCamera())
                .connectivity(request.getConnectivity())
                .battery(request.getBattery())
                .build();
    }
}
