package com.informix.ecommerce.entity;

import jakarta.persistence.*;


@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "product"})
})
public class CustomerSave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "user_account_id")
    private CustomerProfile userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job", referencedColumnName = "jobPostId")
    private Products product;
    public CustomerSave(){

    }

    public CustomerSave(Integer id, CustomerProfile userId, Products product) {
        this.id = id;
        this.userId = userId;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerProfile getUserId() {
        return userId;
    }

    public void setUserId(CustomerProfile userId) {
        this.userId = userId;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
}
