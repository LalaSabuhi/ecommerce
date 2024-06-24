package com.informix.ecommerce.entity;

public class SellerProductsDto {
    private Long totalCustomer;
    private Integer productPostId;
    private String productTitle;
    private ProductLocation productLocationId;
    private ProductCompany productCompany;

    public SellerProductsDto(Long totalCustomer, Integer productPostId, String productTitle, ProductLocation productLocationId, ProductCompany productCompany) {
        this.totalCustomer = totalCustomer;
        this.productPostId = productPostId;
        this.productTitle = productTitle;
        this.productLocationId = productLocationId;
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

    public ProductLocation getProductLocationId() {
        return productLocationId;
    }

    public void setProductLocationId(ProductLocation productLocationId) {
        this.productLocationId = productLocationId;
    }

    public ProductCompany getProductCompany() {
        return productCompany;
    }

    public void setProductCompany(ProductCompany productCompany) {
        this.productCompany = productCompany;
    }
}
