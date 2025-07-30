package com.example.fone_hub.service.impl;

import com.example.fone_hub.dto.request.AddressRequest;
import com.example.fone_hub.dto.request.CreateUserRequest;
import com.example.fone_hub.dto.request.UpdateUserRequest;
import com.example.fone_hub.dto.response.UserResponse;
import com.example.fone_hub.entity.User;
import com.example.fone_hub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public int countUsers() {
        return 0;
    }

    @Override
    public List<UserResponse> getUsers() {
        return List.of();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        return null;
    }

    @Override
    public User registerUser(CreateUserRequest request) {
        return null;
    }

    @Override
    public User createUser(CreateUserRequest request, MultipartFile file) {
        return null;
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest request, Long userId, MultipartFile file) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public void addAddress(Long userId, AddressRequest request) {

    }

    @Override
    public void deleteAddress(Long addressId) {

    }

    @Override
    public String checkout(Long orderId, Long addressId) {
        return "";
    }

    @Override
    public void updateOrderAddress(Long orderId, Long addressId) {

    }

    @Override
    public User findByUserName(String username) {
        return null;
    }
}

