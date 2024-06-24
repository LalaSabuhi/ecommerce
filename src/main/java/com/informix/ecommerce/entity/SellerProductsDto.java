package com.informix.ecommerce.entity;

public class SellerProductsDto {
    private Long totalCustomer;
    private Integer productPostId;
    private String productTitle;
    private ProductLocation productLocation;
    private ProductCompany productCompany;

    public SellerProductsDto(Long totalCustomer, Integer productPostId, String productTitle, ProductLocation productLocation, ProductCompany productCompany) {
        this.totalCustomer = totalCustomer;
        this.productPostId = productPostId;
        this.productTitle = productTitle;
        this.productLocation = productLocation;
        this.productCompany = productCompany;
    }

    public Long getTotalCustomer() {
        return totalCustomer;
    }

    public void setTotalCustomer(Long totalCustomer) {
        this.totalCustomer = totalCustomer;
    }

    public Integer getProductPostId() {
        return productPostId;
    }

    public void setProductPostId(Integer productPostId) {
        this.productPostId = productPostId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public ProductLocation getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(ProductLocation productLocation) {
        this.productLocation = productLocation;
    }

    public ProductCompany getProductCompany() {
        return productCompany;
    }

    public void setProductCompany(ProductCompany productCompany) {
        this.productCompany = productCompany;
    }
}
