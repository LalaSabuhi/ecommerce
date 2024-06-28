package com.informix.ecommerce.service;

import com.informix.ecommerce.entity.CustomerApply;
import com.informix.ecommerce.entity.CustomerProfile;
import com.informix.ecommerce.entity.Products;
import com.informix.ecommerce.repository.CustomerApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerApplyService {
    private final CustomerApplyRepository customerApplyRepository;

    @Autowired
    public CustomerApplyService(CustomerApplyRepository customerApplyRepository) {
        this.customerApplyRepository = customerApplyRepository;
    }

    public List<CustomerApply> getCandidatesJobs(CustomerProfile userAccountId) {
        return customerApplyRepository.findByUserId(userAccountId);
    }

    public List<CustomerApply> getProductApplicants(Products products) {
        return customerApplyRepository.findByProduct(products);
    }

    public void addNew(CustomerApply customerApply) {
        customerApplyRepository.save(customerApply);
    }

}

