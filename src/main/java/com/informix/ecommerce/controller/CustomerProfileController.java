package com.informix.ecommerce.controller;

import com.informix.ecommerce.entity.CustomerProfile;
import com.informix.ecommerce.entity.Users;
import com.informix.ecommerce.repository.UsersRepository;
import com.informix.ecommerce.service.CustomerProfileService;
import com.informix.ecommerce.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/customer-profile")
public class CustomerProfileController {
    private final CustomerProfileService customerProfileService;
    private final UsersRepository usersRepository;

    @Autowired
    public CustomerProfileController(CustomerProfileService customerProfileService, UsersRepository usersRepository){
        this.customerProfileService = customerProfileService;
        this.usersRepository = usersRepository;
    }
    @GetMapping("/")
    public String jobSeekerProfile(Model model) {
        CustomerProfile customerProfile = new CustomerProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found."));
            Optional<CustomerProfile> customProfile = customerProfileService.getOne(user.getUserId());
            if (customProfile.isPresent()) {
                customerProfile = customProfile.get();
            }
            model.addAttribute("profile", customerProfile);
        }

        return "customer-profile";
    }

    @PostMapping("/addNew")
    public String addNew(CustomerProfile customerProfile,
                         @RequestParam("image") MultipartFile image,
                         Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found."));
            customerProfile.setUserId(user);
            customerProfile.setUserAccountId(user.getUserId());
        }
        model.addAttribute("profile", customerProfile);

        String imageName = "";


        if (!Objects.equals(image.getOriginalFilename(), "")) {
            imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            customerProfile.setProfilePhoto(imageName);
        }


        CustomerProfile customProfile = customerProfileService.addNew(customerProfile);

        try {
            String uploadDir = "photos/customer/" + customProfile.getUserAccountId();
            if (!Objects.equals(image.getOriginalFilename(), "")) {
                FileUploadUtil.saveFile(uploadDir, imageName, image);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return "redirect:/dashboard/";
    }
}
