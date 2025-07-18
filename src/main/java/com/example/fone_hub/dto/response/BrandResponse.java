package com.example.fone_hub.dto.response;

import com.example.fone_hub.enums.LinkedStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record BrandResponse (
        Long id,
        String name,
        String image,
        LinkedStatus status
){
}
