package com.informix.ecommerce.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentMethodId;

    @Column(nullable = false)
    private String methodName;

    @OneToMany(mappedBy = "paymentMethod", cascade = CascadeType.ALL)
    private List<CustomerPaymentMethod> customerPaymentMethods;

    public PaymentMethod() {}

    public PaymentMethod(String methodName) {
        this.methodName = methodName;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<CustomerPaymentMethod> getCustomerPaymentMethods() {
        return customerPaymentMethods;
    }

    public void setCustomerPaymentMethods(List<CustomerPaymentMethod> customerPaymentMethods) {
        this.customerPaymentMethods = customerPaymentMethods;
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "paymentMethodId=" + paymentMethodId +
                ", methodName='" + methodName + '\'' +
                ", customerPaymentMethods=" + customerPaymentMethods +
                '}';
    }
}
