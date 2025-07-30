package com.example.fone_hub.repository;

import com.example.fone_hub.dto.request.FilterRequest;
import com.example.fone_hub.entity.Product;
import com.example.fone_hub.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    List<Product> findByNameContainingAndStatus(String name, ProductStatus status);

    // Thêm 2 methods này vào ProductRepository
    Page<Product> findByStatusIn(List<ProductStatus> statuses, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndStatusIn(String name, List<ProductStatus> statuses, Pageable pageable);

    boolean existsByNameAndStatus(String name, ProductStatus productStatus);

    @Query("""
        SELECT p FROM Product p
        WHERE (:#{#req.name} IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :#{#req.name}, '%')))
        AND (:#{#req.minPrice} IS NULL OR p.price >= :#{#req.minPrice})
        AND (:#{#req.maxPrice} IS NULL OR p.price <= :#{#req.maxPrice})
        AND (:#{#req.brands} IS NULL OR p.brand.id IN :#{#req.brands})
        AND (:#{#req.categories} IS NULL OR p.category.id IN :#{#req.categories})
    """)
    Page<Product> filter(@Param("req") FilterRequest request, Pageable pageable);
}
