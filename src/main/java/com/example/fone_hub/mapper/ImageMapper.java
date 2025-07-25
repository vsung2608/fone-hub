package com.example.fone_hub.mapper;

import com.example.fone_hub.dto.response.ImageResponse;
import com.example.fone_hub.entity.ImageProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageResponse toImageResponse(ImageProduct imageEntity);
}
