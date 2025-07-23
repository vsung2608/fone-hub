package com.example.fone_hub.service;

import com.example.fone_hub.entity.Address;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    Address save(Address address);
    void deleteById(Long id);
} 