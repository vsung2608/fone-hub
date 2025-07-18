package com.example.fone_hub.repository;

import com.example.fone_hub.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    Optional<Category> findByName(String name);

    Page<Category> findALl(Pageable pageable);
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
