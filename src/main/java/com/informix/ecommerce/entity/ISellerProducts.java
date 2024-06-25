package com.informix.ecommerce.entity;

public interface ISellerProducts {
    Long getTotalCustomer();

    Integer getProductPostId();
    String getProductImage();

    String getProductPrice();

    String getProductTitle();

    int getLocationId();

    String getCity();

    String getState();

    String getCountry();

    int getCompanyId();

    String getName();
}
