package com.example.fone_hub.mapper;

import com.example.fone_hub.dto.request.CategoryRequest;
import com.example.fone_hub.dto.response.CategoryResponse;
import com.example.fone_hub.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public Category categoryRequestToCategory(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.name())
                .description(categoryRequest.description())
                .build();
    }

    public CategoryResponse categoryToCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
