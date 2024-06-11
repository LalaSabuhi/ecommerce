package com.informix.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_payment_methods")
public class CustomerPaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    private Users userAccount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private String accountDetails;

    public CustomerPaymentMethod() {}

    public CustomerPaymentMethod(Users userAccount, PaymentMethod paymentMethod, String accountDetails) {
        this.userAccount = userAccount;
        this.paymentMethod = paymentMethod;
        this.accountDetails = accountDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Users userAccount) {
        this.userAccount = userAccount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(String accountDetails) {
        this.accountDetails = accountDetails;
    }

    @Override
    public String toString() {
        return "CustomerPaymentMethod{" +
                "id=" + id +
                ", userAccount=" + userAccount +
                ", paymentMethod=" + paymentMethod +
                ", accountDetails='" + accountDetails + '\'' +
                '}';
    }
}
