package com.informix.ecommerce.repository;

import com.informix.ecommerce.entity.CustomerProfile;
import com.informix.ecommerce.entity.CustomerSave;
import com.informix.ecommerce.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerSaveRepository extends JpaRepository<CustomerSave, Integer> {

    public List<CustomerSave> findByUserId(CustomerProfile userAccountId);

    List<CustomerSave> findByProduct(Products product);
}
