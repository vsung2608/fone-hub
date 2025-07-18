package com.example.fone_hub.mapper;

import com.example.fone_hub.dto.request.BrandRequest;
import com.example.fone_hub.dto.response.BrandResponse;
import com.example.fone_hub.entity.Brand;
import org.springframework.stereotype.Service;

@Service
public class BrandMapper {
    public Brand brandRequestToBrand(BrandRequest brandRequest) {
        return Brand.builder()
                .name(brandRequest.name())
                .build();
    }

    public BrandResponse brandToBrandResponse(Brand brand) {
        return new BrandResponse(
                brand.getId(),
                brand.getName(),
                brand.getImage(),
                brand.getStatus()
        );
    }
}
