package com.informix.ecommerce.service;

import com.informix.ecommerce.entity.Products;
import com.informix.ecommerce.repository.ProductsRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {
    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Products addNew(Products products) {
        return productsRepository.save(products);
    }
}
