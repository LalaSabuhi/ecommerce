package com.informix.ecommerce.controller;

import com.informix.ecommerce.entity.SellerProfile;
import com.informix.ecommerce.entity.Users;
import com.informix.ecommerce.repository.UsersRepository;
import com.informix.ecommerce.service.SellerProfileService;
import com.informix.ecommerce.service.UsersService;
import com.informix.ecommerce.util.FileUploadUtil;
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

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/seller-profile")
public class SellerProfileController {
    private final UsersRepository usersRepository;
    private final UsersService usersService;
    private final SellerProfileService sellerProfileService;

    public SellerProfileController(UsersRepository usersRepository, SellerProfileService sellerProfileService,UsersService usersService) {
        this.usersRepository = usersRepository;
        this.sellerProfileService = sellerProfileService;
        this.usersService =usersService;
    }

    @GetMapping("/")
    public String sellerProfile(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could not " + "found user"));
            Optional<SellerProfile> sellerProfile = sellerProfileService.getOne(users.getUserId());

            if (!sellerProfile.isEmpty())
                model.addAttribute("profile", sellerProfile.get());

        }
        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication1 instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication1.getName();
            model.addAttribute("username", currentUsername);
        }

        model.addAttribute("user", currentUserProfile);

        return "seller_profile";
    }

    @PostMapping("/addNew")
    public String addNew(SellerProfile sellerProfile, @RequestParam("image") MultipartFile multipartFile, Model model) {
        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication1 instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication1.getName();
            model.addAttribute("username", currentUsername);
        }

        model.addAttribute("user", currentUserProfile);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could not " + "found user"));
            sellerProfile.setUserId(users);
            sellerProfile.setUserAccountId(users.getUserId());
        }
        model.addAttribute("profile", sellerProfile);
        String fileName = "";
        if (!multipartFile.getOriginalFilename().equals("")) {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            sellerProfile.setProfilePhoto(fileName);
        }
        SellerProfile savedUser = sellerProfileService.addNew(sellerProfile);

        String uploadDir = "photos/seller/" + savedUser.getUserAccountId();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "redirect:/dashboard/";
    }
}
