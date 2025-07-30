package com.example.fone_hub.service.impl;

import com.example.fone_hub.dto.request.CategoryRequest;
import com.example.fone_hub.dto.response.CategoryResponse;
import com.example.fone_hub.entity.Product;
import com.example.fone_hub.exception.AppException;
import com.example.fone_hub.exception.ErrorCode;
import com.example.fone_hub.mapper.CategoryMapper;
import com.example.fone_hub.repository.CategoryRepository;
import com.example.fone_hub.repository.ProductRepository;
import com.example.fone_hub.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::categoryToCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        var category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST));
        return categoryMapper.categoryToCategoryResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        if(categoryRepository.existsByName(categoryRequest.name())){
            throw new AppException(ErrorCode.ENTITY_EXIST);
        }

        var newCategory = categoryMapper.categoryRequestToCategory(categoryRequest);

        return categoryMapper.categoryToCategoryResponse(categoryRepository.save(newCategory));
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        var updatedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXIST));

        if(categoryRequest.name() != null && !categoryRequest.name().isEmpty()){
            updatedCategory.setName(categoryRequest.name());
        }

        categoryRepository.save(updatedCategory);

        return categoryMapper.categoryToCategoryResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        List<Product> productEntities = productRepository.findByCategoryId(categoryId)
                .stream()
                .peek(od -> od.setCategory(null))
                .toList();

        productRepository.saveAll(productEntities);

        categoryRepository.deleteById(categoryId);
    }
}

