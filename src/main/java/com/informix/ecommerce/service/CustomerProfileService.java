package com.informix.ecommerce.service;

import com.informix.ecommerce.entity.CustomerProfile;
import com.informix.ecommerce.repository.CustomerProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerProfileService {
    private final CustomerProfileRepository customerProfileRepository;

    public CustomerProfileService(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }

    public Optional<CustomerProfile> getOne(Integer id) {
        return customerProfileRepository.findById(id);
    }

    public CustomerProfile addNew(CustomerProfile customerProfile) {
        return customerProfileRepository.save(customerProfile);
    }
}
