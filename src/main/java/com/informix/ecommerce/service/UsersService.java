package com.informix.ecommerce.service;

import com.informix.ecommerce.entity.CustomerProfile;
import com.informix.ecommerce.entity.SellerProfile;
import com.informix.ecommerce.entity.Users;
import com.informix.ecommerce.repository.CustomerProfileRepository;
import com.informix.ecommerce.repository.SellerProfileRepository;
import com.informix.ecommerce.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private  final SellerProfileRepository sellerProfileRepository;
    private final CustomerProfileRepository customerProfileRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository,SellerProfileRepository sellerProfileRepository,CustomerProfileRepository customerProfileRepository){
        this.usersRepository=usersRepository;
        this.sellerProfileRepository=sellerProfileRepository;
        this.customerProfileRepository=customerProfileRepository;
    }
    public Users addNew(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        Users savedUser =  usersRepository.save(users);
        int userTypeId = users.getUserTypeId().getUserTypeId();
        if(userTypeId == 1){
            sellerProfileRepository.save(new SellerProfile(savedUser));
        }else{
            customerProfileRepository.save(new CustomerProfile(savedUser));
        }
        return savedUser;
    }
}