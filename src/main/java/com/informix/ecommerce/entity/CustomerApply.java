package com.informix.ecommerce.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "product"})
})
public class CustomerApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "user_account_id")
    private CustomerProfile userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product", referencedColumnName = "productId")
    private Products product;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date applyDate;

    private String coverLetter;
    public CustomerApply(){

    }

    public CustomerApply(Integer id, CustomerProfile userId, Products product, Date applyDate, String coverLetter) {
        this.id = id;
        this.userId = userId;
        this.product = product;
        this.applyDate = applyDate;
        this.coverLetter = coverLetter;
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

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    @Override
    public String toString() {
        return "CustomerApply{" +
                "id=" + id +
                ", userId=" + userId +
                ", product=" + product +
                ", applyDate=" + applyDate +
                ", coverLetter='" + coverLetter + '\'' +
                '}';
    }
}
