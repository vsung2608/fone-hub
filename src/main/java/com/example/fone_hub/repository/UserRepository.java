package com.example.fone_hub.repository;

import com.example.fone_hub.entity.User;
import com.example.fone_hub.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByStatus(StatusEnum status);
    boolean existsByIdAndStatus(Long id, StatusEnum status);
    boolean existsByUsernameAndStatus(String username, StatusEnum status);
    User findByUsernameAndStatus(String username, StatusEnum status);
    User findByIdAndStatus(Long userId, StatusEnum status);
}
