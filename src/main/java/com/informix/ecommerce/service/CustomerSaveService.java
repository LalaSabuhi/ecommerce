package com.informix.ecommerce.service;

import com.informix.ecommerce.entity.CustomerProfile;
import com.informix.ecommerce.entity.CustomerSave;
import com.informix.ecommerce.entity.Products;
import com.informix.ecommerce.repository.CustomerSaveRepository;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerSaveService {

    private final CustomerSaveRepository customerSaveRepository;

    public CustomerSaveService(CustomerSaveRepository customerSaveRepository) {
        this.customerSaveRepository = customerSaveRepository;
    }

    public List<CustomerSave> getCandidatesJob(CustomerProfile userAccountId) {
        return customerSaveRepository.findByUserId(userAccountId);
    }

    public List<CustomerSave> getProductApplicants(Products products) {
        return customerSaveRepository.findByProduct(products);
    }

    public void addNew(CustomerSave customerSave) {
        customerSaveRepository.save(customerSave);
    }
}
