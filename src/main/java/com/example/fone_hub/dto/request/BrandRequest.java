package com.example.fone_hub.dto.request;

import com.example.fone_hub.enums.LinkedStatus;
import org.springframework.web.multipart.MultipartFile;

public record BrandRequest (
        String name,
        MultipartFile image
){
}
