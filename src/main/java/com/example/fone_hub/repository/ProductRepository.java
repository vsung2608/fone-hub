package com.example.fone_hub.repository;

import com.example.fone_hub.dto.request.FilterRequest;
import com.example.fone_hub.entity.Product;
import com.example.fone_hub.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    long countByStatusIn(List<ProductStatus> statuses);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByBrandId(Long brandId);

    boolean existsByIdAndStatus(Long id, ProductStatus status);

    Optional<Product> findByNameAndStatus(String name, ProductStatus status);

    Optional<Product> findByIdAndStatus(Long productId, ProductStatus status);

    List<Product> findByNameContainingOrDescriptionContainingAndStatus(String name, String description, ProductStatus status);

    // Thêm 2 methods này vào ProductRepository
    Page<Product> findByStatus(ProductStatus status, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseAndStatus(String name, ProductStatus status, Pageable pageable);

    boolean existsByNameAndStatus(String name, ProductStatus productStatus);

    Page<Product> filter(FilterRequest request, Pageable pageable);
}
