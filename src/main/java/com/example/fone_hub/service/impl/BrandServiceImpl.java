package com.example.fone_hub.service.impl;

import com.example.fone_hub.dto.request.BrandRequest;
import com.example.fone_hub.dto.response.BrandResponse;
import com.example.fone_hub.entity.Brand;
import com.example.fone_hub.entity.Product;
import com.example.fone_hub.enums.LinkedStatus;
import com.example.fone_hub.exception.AppException;
import com.example.fone_hub.exception.ErrorCode;
import com.example.fone_hub.mapper.BrandMapper;
import com.example.fone_hub.repository.BrandRepository;
import com.example.fone_hub.repository.ProductRepository;
import com.example.fone_hub.service.BrandService;
import com.example.fone_hub.service.ImageProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BrandServiceImpl implements BrandService {
    private final ImageProductService imageProductService;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final BrandMapper brandMapper;

    @Override
    public BrandResponse getBrandById(Long brandId) {
        var brand = brandRepository.findByIdAndStatus(brandId, LinkedStatus.LINKED)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST));
        return brandMapper.brandToBrandResponse(brand);
    }

    @Override
    public BrandResponse createBrand(BrandRequest brandRequest, MultipartFile file) {
        if(brandRepository.existsByNameAndStatus(brandRequest.name(), LinkedStatus.LINKED)){
            throw new AppException(ErrorCode.ENTITY_EXIST);
        }

        var newBrand = brandMapper.brandRequestToBrand(brandRequest);
        newBrand.setStatus(LinkedStatus.LINKED);
        newBrand.setImage(imageProductService.createImage(file));

        return brandMapper.brandToBrandResponse(brandRepository.save(newBrand));
    }

    @Override
    public BrandResponse updateBrand(Long brandId, BrandRequest brandRequest, MultipartFile file) {
        var updatedBrand = brandRepository.findByIdAndStatus(brandId, LinkedStatus.LINKED)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST));

        if(brandRequest.name() != null && !brandRequest.name().isEmpty()){
            updatedBrand.setName(brandRequest.name());
        }

        if(file != null && !file.isEmpty()){
            updatedBrand.setImage(imageProductService.createImage(file));
        }

        return brandMapper.brandToBrandResponse(brandRepository.save(updatedBrand));
    }

    @Override
    public void softDeleteBrand(Long brandId) {
        Brand brand = brandRepository.findByIdAndStatus(brandId, LinkedStatus.LINKED)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST));

        brand.setStatus(LinkedStatus.UNLINKED);
        brandRepository.save(brand);
    }

    @Override
    public void restoreBrand(Long brandId) {
        Brand brand = brandRepository.findByIdAndStatus(brandId, LinkedStatus.UNLINKED)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST));

        brand.setStatus(LinkedStatus.LINKED);
        brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(Long brandId) {
        List<Product> products = productRepository.findByBrandId(brandId)
                .stream()
                .peek(od -> od.setBrand(null))
                .toList();

        productRepository.saveAll(products);
        brandRepository.deleteById(brandId);
    }

    @Override
    public void updateImage(Long brandId, String image) {
        Brand brand = brandRepository.findByIdAndStatus(brandId, LinkedStatus.LINKED)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST));

        brand.setImage(image);
        brandRepository.save(brand);
    }

    @Override
    public void deleteImage(Long brandId) {
        Brand brand = brandRepository.findByIdAndStatus(brandId, LinkedStatus.LINKED)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST));

        brand.setImage(null);
        brandRepository.save(brand);
    }
}
