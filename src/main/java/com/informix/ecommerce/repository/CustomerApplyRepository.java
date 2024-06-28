package com.informix.ecommerce.repository;

import com.informix.ecommerce.entity.CustomerApply;
import com.informix.ecommerce.entity.CustomerProfile;
import com.informix.ecommerce.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerApplyRepository extends JpaRepository<CustomerApply,Integer> {
    List<CustomerApply> findByUserId(CustomerProfile userAccountId);

    List<CustomerApply> findByProduct(Products products);
}
