package com.informix.ecommerce.service;

import com.informix.ecommerce.entity.SellerProfile;
import com.informix.ecommerce.repository.SellerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerProfileService {

    private final SellerProfileRepository sellerRepository;

    @Autowired
    public SellerProfileService(SellerProfileRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public Optional<SellerProfile> getOne(Integer id) {
        return sellerRepository.findById(id);
    }

    public SellerProfile addNew(SellerProfile sellerProfile) {
        return sellerRepository.save(sellerProfile);
    }
}
