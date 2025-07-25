package com.example.fone_hub.service.impl;

import com.example.fone_hub.entity.Address;
import com.example.fone_hub.repository.AddressRepository;

import com.example.fone_hub.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }
} 