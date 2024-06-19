package com.informix.ecommerce.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "postedById", referencedColumnName = "userId")
    private Users postedById;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productLocationId", referencedColumnName = "Id")
    private ProductLocation productLocationId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productCompanyId", referencedColumnName = "Id")
    private ProductCompany productCompanyId;

    @Transient
    private Boolean isActive;

    @Transient
    private Boolean isSaved;

    @Length(max = 10000)
    private String descriptionOfProduct;
    private String price;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date postedDate;
    private String productTitle;

    public Products() {
    }

    public Products(Integer productId, Users postedById, ProductLocation productLocationId, ProductCompany productCompanyId, Boolean isActive, Boolean isSaved, String descriptionOfProduct, String price, Date postedDate, String productTitle) {
        this.productId = productId;
        this.postedById = postedById;
        this.productLocationId = productLocationId;
        this.productCompanyId = productCompanyId;
        this.isActive = isActive;
        this.isSaved = isSaved;
        this.descriptionOfProduct = descriptionOfProduct;
        this.price = price;
        this.postedDate = postedDate;
        this.productTitle = productTitle;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Users getPostedById() {
        return postedById;
    }

    public void setPostedById(Users postedById) {
        this.postedById = postedById;
    }

    public ProductLocation getProductLocationId() {
        return productLocationId;
    }

    public void setProductLocationId(ProductLocation productLocationId) {
        this.productLocationId = productLocationId;
    }

    public ProductCompany getProductCompanyId() {
        return productCompanyId;
    }

    public void setProductCompanyId(ProductCompany productCompanyId) {
        this.productCompanyId = productCompanyId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getSaved() {
        return isSaved;
    }

    public void setSaved(Boolean saved) {
        isSaved = saved;
    }

    public String getDescriptionOfProduct() {
        return descriptionOfProduct;
    }

    public void setDescriptionOfProduct(String descriptionOfProduct) {
        this.descriptionOfProduct = descriptionOfProduct;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    @Override
    public String toString() {
        return "Products{" +
                "productId=" + productId +
                ", postedById=" + postedById +
                ", productLocationId=" + productLocationId +
                ", productCompanyId=" + productCompanyId +
                ", isActive=" + isActive +
                ", isSaved=" + isSaved +
                ", descriptionOfProduct='" + descriptionOfProduct + '\'' +
                ", price='" + price + '\'' +
                ", postedDate=" + postedDate +
                ", productTitle='" + productTitle + '\'' +
                '}';
    }
}
