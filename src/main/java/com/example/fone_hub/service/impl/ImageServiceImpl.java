package com.example.fone_hub.service.impl;

import com.example.fone_hub.dto.response.ImageProductResponse;
import com.example.fone_hub.entity.ImageProduct;
import com.example.fone_hub.exception.AppException;
import com.example.fone_hub.exception.ErrorCode;
import com.example.fone_hub.mapper.ImageProductMapper;
import com.example.fone_hub.repository.ImageProductRepository;
import com.example.fone_hub.repository.ProductRepository;
import com.example.fone_hub.service.CloudinaryService;
import com.example.fone_hub.service.ImageProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageProductService {
    private final ImageProductRepository imageRepository;
    private final ProductRepository productRepository;
    private final ImageProductMapper imageProductMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<ImageProductResponse> getImageByProductId(Long productId) {
        return imageRepository.findByProductId(productId)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST))
                .stream().map(imageProductMapper::imageProductToImageProductResponse)
                .toList();
    }

    @Override
    public String createImage(MultipartFile file) {
        if(!file.isEmpty()){
            try{
                return cloudinaryService.uploadFile(file);
            } catch (Exception e){
                throw new AppException(ErrorCode.UPLOAD_IMAGE_ERROR);
            }
        }
        return "";
    }

    @Override
    public void createImage(MultipartFile file, Long productId){
        if(!file.isEmpty()){
            try{
                ImageProduct newImage = ImageProduct.builder()
                        .imageLink(cloudinaryService.uploadFile(file))
                        .product(productRepository.findById(productId).get())
                        .build();
                imageRepository.save(newImage);
            } catch (Exception e){
                throw new AppException(ErrorCode.UPLOAD_IMAGE_ERROR);
            }
        }
    }

    @Override
    public void createImage(List<MultipartFile> files, Long productId) {
        for (MultipartFile file : files){
            if(!file.isEmpty()){
                try{
                    ImageProduct newImage = ImageProduct.builder()
                            .imageLink(cloudinaryService.uploadFile(file))
                            .product(productRepository.findById(productId).get())
                            .build();
                    imageRepository.save(newImage);
                } catch (Exception e){
                    throw new AppException(ErrorCode.UPLOAD_IMAGE_ERROR);
                }
            }
        }
    }

    @Override
    public void deleteImage(Long imageId) {
        imageRepository.deleteById(imageId);
    }
}

