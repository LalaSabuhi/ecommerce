package com.informix.ecommerce.service;

import com.informix.ecommerce.entity.CustomerPaymentMethod;
import com.informix.ecommerce.entity.PaymentMethod;
import com.informix.ecommerce.entity.Users;
import com.informix.ecommerce.repository.CustomerPaymentMethodRepository;
import com.informix.ecommerce.repository.PaymentMethodRepository;
import com.informix.ecommerce.repository.UsersRepository;
import com.informix.ecommerce.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final CustomerPaymentMethodRepository customerPaymentMethodRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository,CustomerPaymentMethodRepository customerPaymentMethodRepository,PaymentMethodRepository paymentMethodRepository){
        this.usersRepository=usersRepository;
        this.customerPaymentMethodRepository=customerPaymentMethodRepository;
        this.paymentMethodRepository= paymentMethodRepository;
    }
    public Users addNew(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        return usersRepository.save(users);
    }
}
