package com.informix.ecommerce.service;

import com.informix.ecommerce.entity.CustomerProfile;
import com.informix.ecommerce.entity.SellerProfile;
import com.informix.ecommerce.entity.Users;
import com.informix.ecommerce.repository.CustomerProfileRepository;
import com.informix.ecommerce.repository.SellerProfileRepository;
import com.informix.ecommerce.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final SellerProfileRepository sellerProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, CustomerProfileRepository customerProfileRepository, SellerProfileRepository sellerProfileRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.customerProfileRepository = customerProfileRepository;
        this.sellerProfileRepository = sellerProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users addNew(Users users) {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = usersRepository.save(users);
        int userTypeId = users.getUserTypeId().getUserTypeId();

        if (userTypeId == 1) {
            sellerProfileRepository.save(new SellerProfile(savedUser));
        }
        else {
            customerProfileRepository.save(new CustomerProfile(savedUser));
        }

        return savedUser;
    }

    public Object getCurrentUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could not found " + "user"));
            int userId = users.getUserId();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Seller"))) {
                SellerProfile sellerProfile = sellerProfileRepository.findById(userId).orElse(new SellerProfile());
                return sellerProfile;
            } else {
                CustomerProfile customerProfile = customerProfileRepository.findById(userId).orElse(new CustomerProfile());
                return customerProfile;
            }
        }

        return null;
    }
    public Users getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
            return user;
        }

        return null;
    }
}