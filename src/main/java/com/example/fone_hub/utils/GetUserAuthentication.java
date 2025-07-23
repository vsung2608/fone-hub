package com.example.fone_hub.utils;

import com.example.fone_hub.entity.User;
import com.example.fone_hub.enums.StatusEnum;
import com.example.fone_hub.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class GetUserAuthentication {
    @Autowired
    private UserRepository userRepository;

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername(); // Username của người dùng
        }
        return null; // Trường hợp không có người dùng nào đăng 
    }
    
    public User getUser() {
        String username = getUsername();
        if (username != null) {
            return userRepository.findByUsernameAndStatus(username, StatusEnum.Active); // Truy vấn thông tin người dùng
        }
        return null; // Không tìm thấy người dùng
    }
}
