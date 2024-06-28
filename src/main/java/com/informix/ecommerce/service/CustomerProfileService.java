package com.informix.ecommerce.service;

import com.informix.ecommerce.entity.CustomerProfile;
import com.informix.ecommerce.entity.Users;
import com.informix.ecommerce.repository.CustomerProfileRepository;
import com.informix.ecommerce.repository.UsersRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerProfileService {
    private final CustomerProfileRepository customerProfileRepository;
    private final UsersRepository usersRepository;

    public CustomerProfileService(CustomerProfileRepository customerProfileRepository, UsersRepository usersRepository) {
        this.customerProfileRepository = customerProfileRepository;
        this.usersRepository = usersRepository;
    }

    public Optional<CustomerProfile> getOne(Integer id) {
        return customerProfileRepository.findById(id);
    }

    public CustomerProfile addNew(CustomerProfile customerProfile) {
        return customerProfileRepository.save(customerProfile);
    }
    public CustomerProfile getCurrentCustomerProfile() {
        // Kullanıcının kimlik doğrulama bilgilerini al
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kullanıcı anonim değilse devam et
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            // Mevcut kullanıcının kullanıcı adını (email) al
            String currentUsername = authentication.getName();

            // Kullanıcıyı email adresine göre bul, bulunamazsa hata fırlat
            Users user = usersRepository.findByEmail(currentUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Kullanıcının satıcı profilini al
            Optional<CustomerProfile> customerProfile = getOne(user.getUserId());

            // Satıcı profili varsa döndür, yoksa null döndür
            return customerProfile.orElse(null);
        } else {
            // Kullanıcı anonimse null döndür
            return null;
        }
    }
}
