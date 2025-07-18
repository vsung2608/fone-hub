package com.example.fone_hub.service;

import com.example.fone_hub.dto.request.CategoryRequest;
import com.example.fone_hub.dto.response.CategoryResponse;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    CategoryResponse getCategoryById(Long categoryId);

    CategoryResponse createCategory(CategoryRequest categoryRequest);

    CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);

    void deleteCategory(Long categoryId);
}
