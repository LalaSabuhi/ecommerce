package com.informix.ecommerce.entity;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "product_images")
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Products product;

    private String image;
    private Boolean isMainImage;


    public ProductImages(){

    }

    public ProductImages(int id, Products product, String image, Boolean isMainImage) {
        this.id = id;
        this.product = product;
        this.image = image;
        this.isMainImage = isMainImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public String getImageUrl() {
        return image;
    }

    public void setImageUrl(String image) {
        this.image = image;
    }

    public Boolean getMainImage() {
        return isMainImage;
    }

    public void setMainImage(Boolean mainImage) {
        isMainImage = mainImage;
    }
    @Transient
    public String getPhotosImagePath() {
        if (image == null || id == null) return null;
        return "photos/product/" + id + "/" + image;
    }


    @Override
    public String toString() {
        return "ProductImages{" +
                "id=" + id +
                ", product=" + product +
                ", imageUrl='" + image + '\'' +
                ", isMainImage=" + isMainImage +
                '}';
    }

}
