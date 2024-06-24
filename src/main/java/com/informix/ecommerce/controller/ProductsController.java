package com.informix.ecommerce.controller;

import com.informix.ecommerce.entity.*;
import com.informix.ecommerce.service.ProductsService;
import com.informix.ecommerce.service.UsersService;
import com.informix.ecommerce.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class ProductsController {

    private final UsersService usersService;
    private final ProductsService productsService;

    @Autowired
    public ProductsController(UsersService usersService,ProductsService productsService) {
        this.usersService = usersService;
        this.productsService=productsService;
    }

    @GetMapping("/dashboard/")
        public String searchProduct(Model model) {

            Object currentUserProfile = usersService.getCurrentUserProfile();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUsername = authentication.getName();
                model.addAttribute("username", currentUsername);
                if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Seller"))){
                    List<SellerProductsDto> sellerProducts = productsService.getSellerProducts(((SellerProfile) currentUserProfile).getUserAccountId());
                }
            }

            model.addAttribute("user", currentUserProfile);

            return "dashboard";
        }
        @GetMapping("/dashboard/add")
        public String addProducts(Model model) {
            model.addAttribute("products", new Products());
            model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-products";
    }

    @PostMapping("/dashboard/addNew")
    public String addNew(@ModelAttribute("products") Products products,
                         @RequestParam("image") MultipartFile multipartFile,
                         Model model) {

        Users user = usersService.getCurrentUser();
        if (user != null) {
            products.setPostedById(user);
        }
        products.setPostedDate(new Date());
        model.addAttribute("products", products);

        String fileName = "";
        if (!multipartFile.isEmpty()) {
            fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            products.setProductImage(fileName);
        }
        Products savedProduct = productsService.addNew(products);

        String uploadDir = "photos/product/" + savedProduct.getProductId();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/dashboard/";
    }

}