package com.informix.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name="customer_payment")
public class CustomerPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String paymentName;
    @Column(name="number")
    private String yearsOfExperience;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_profile")
    private CustomerProfile customerProfile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public CustomerProfile getCustomerProfile() {
        return customerProfile;
    }

    public void setCustomerProfile(CustomerProfile customerProfile) {
        this.customerProfile = customerProfile;
    }

    public CustomerPayment() {
    }

    public CustomerPayment(int id, String paymentName, String yearsOfExperience, CustomerProfile customerProfile) {
        this.id = id;
        this.paymentName = paymentName;
        this.yearsOfExperience = yearsOfExperience;
        this.customerProfile = customerProfile;
    }

    @Override
    public String toString() {
        return "CustomerPayment{" +
                "id=" + id +
                ", paymentName='" + paymentName + '\'' +
                ", yearsOfExperience='" + yearsOfExperience + '\'' +
                ", customerProfile=" + customerProfile +
                '}';
    }
}
