package com.example.fone_hub.repository;

import com.example.fone_hub.dto.response.ImageProductResponse;
import com.example.fone_hub.entity.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProduct, Long> {
    Optional<List<ImageProduct>> findByProductId(Long productId);
}
