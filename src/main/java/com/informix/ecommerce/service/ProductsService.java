package com.informix.ecommerce.service;

import com.informix.ecommerce.entity.*;
import com.informix.ecommerce.repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductsService {
    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Products addNew(Products products) {
        return productsRepository.save(products);
    }

    public List<SellerProductsDto> getSellerProducts(int seller){
        List<ISellerProducts> sellerProductsDto = productsRepository.getSellerProducts(seller);

        List<SellerProductsDto> sellerProductsDtoList = new ArrayList<>();

        for( ISellerProducts sel : sellerProductsDto){
            ProductLocation loc = new ProductLocation(sel.getLocationId(),sel.getCity(),sel.getState(),sel.getCountry());
            ProductCompany comp = new ProductCompany(sel.getCompanyId(),sel.getName(), "");
            sellerProductsDtoList.add(new SellerProductsDto(sel.getTotalCustomer(),sel.getProductPostId(),sel.getProductTitle(),sel.getProductPrice(),getPhotosImagePath(sel),loc,comp));
        }
        return sellerProductsDtoList;
    }
    public String getPhotosImagePath(ISellerProducts product) {
        if (product.getProductImage() == null) return null;
        return "/photos/product/" + product.getProductPostId() + "/" + product.getProductImage();
    }

    public Products getOne(int id) {
        return productsRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
    public List<Products> getAll() {
        return productsRepository.findAll();
    }

    public List<Products> search(String computer, String phone, String location, String laptop, String camera, LocalDate searchDate) {
        return Objects.isNull(searchDate) ? productsRepository.searchWithoutDate(computer, phone, location, laptop, camera) :
                productsRepository.search(computer, phone, location, laptop, camera, searchDate);
    }
}
