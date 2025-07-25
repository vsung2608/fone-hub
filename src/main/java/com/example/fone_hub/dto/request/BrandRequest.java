package com.example.fone_hub.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record BrandRequest (
        String name,
        MultipartFile image
){
}
