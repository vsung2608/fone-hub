package com.example.fone_hub.service;

import com.example.fone_hub.dto.request.AddressRequest;
import com.example.fone_hub.dto.request.CreateUserRequest;
import com.example.fone_hub.dto.request.UpdateUserRequest;
import com.example.fone_hub.dto.response.UserResponse;
import com.example.fone_hub.entity.User;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {

    // View user quantity
    public int countUsers();

    // View all users
    public List<UserResponse> getUsers();

    // View user by Id
    public UserResponse getUserById(Long userId);

    // Register user
    User registerUser(CreateUserRequest request) ;

    // Create user
    public User createUser(CreateUserRequest request, MultipartFile file);

    // Update user
    public UserResponse updateUser(UpdateUserRequest request, Long userId, MultipartFile file);

    // Delete user
    public void deleteUser(Long userId);

    // Find By Username
    public User getUserByUsername(String username);

    // Add Address
    public void addAddress(Long userId, AddressRequest request);

    // Add Address
    public void deleteAddress(Long addressId);

    // Checkout 
    public String checkout(Long orderId, Long addressId);

    void updateOrderAddress(Long orderId, Long addressId);

    public User findByUserName(String username);

}
