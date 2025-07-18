package com.example.fone_hub.repository;

import com.example.fone_hub.entity.Brand;
import com.example.fone_hub.enums.LinkedStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByNameAndStatus(String name, LinkedStatus status);

    Optional<Brand> findByIdAndStatus(Long brandId, LinkedStatus status);

    Optional<Brand> findByName(String name);

    Page<Brand> findByStatus(LinkedStatus status, Pageable pageable);
    Page<Brand> findByNameContainingIgnoreCaseAndStatus(String name, LinkedStatus status, Pageable pageable);
}
