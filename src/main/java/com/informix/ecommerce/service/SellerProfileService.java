package com.informix.ecommerce.service;

import com.informix.ecommerce.entity.SellerProfile;
import com.informix.ecommerce.entity.Users;
import com.informix.ecommerce.repository.SellerProfileRepository;
import com.informix.ecommerce.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerProfileService {

    private final SellerProfileRepository sellerRepository;
    private final UsersRepository usersRepository;
    @Autowired
    public SellerProfileService(SellerProfileRepository sellerRepository, UsersRepository usersRepository) {
        this.sellerRepository = sellerRepository;
        this.usersRepository = usersRepository;
    }


    public Optional<SellerProfile> getOne(Integer id) {
        return sellerRepository.findById(id);
    }

    public SellerProfile addNew(SellerProfile sellerProfile) {
        return sellerRepository.save(sellerProfile);
    }
    public SellerProfile getCurrentSellerProfile() {
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
            Optional<SellerProfile> sellerProfile = getOne(user.getUserId());

            // Satıcı profili varsa döndür, yoksa null döndür
            return sellerProfile.orElse(null);
        } else {
            // Kullanıcı anonimse null döndür
            return null;
        }
    }

}
