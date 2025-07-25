package com.example.fone_hub.service;

import com.example.fone_hub.dto.request.BrandRequest;
import com.example.fone_hub.dto.response.BrandResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface BrandService {
    List<BrandResponse> getAllBrands();

    BrandResponse getBrandById(Long brandId);

    BrandResponse createBrand(BrandRequest brandRequest, MultipartFile file);

    BrandResponse updateBrand(Long brandId, BrandRequest brandRequest, MultipartFile file);

    void softDeleteBrand(Long brandId);

    void restoreBrand(Long brandId);

    void deleteBrand(Long brandId);

    void updateImage(Long brandId, String image);

    void deleteImage(Long brandId);
}
