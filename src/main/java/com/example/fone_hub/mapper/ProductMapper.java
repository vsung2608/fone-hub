package com.example.fone_hub.mapper;

import com.example.fone_hub.dto.request.ProductRequest;
import com.example.fone_hub.dto.response.ProductResponse;
import com.example.fone_hub.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public ProductResponse productToProductResponse(Product product) {
        return new ProductResponse();
    }

    public Product productRequestToProduct(ProductRequest request) {
        return Product.builder().build();
    }
}
