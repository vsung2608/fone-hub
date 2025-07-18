package com.example.fone_hub.service;

import com.example.fone_hub.dto.response.ImageProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ImageProductService {
    List<ImageProductResponse> getImageByProductId(Long productId);

    String createImage(MultipartFile file);

    void createImage(MultipartFile file, Long productId);

    void createImage(List<MultipartFile> files, Long productId);

    void deleteImage(Long imageId);
}