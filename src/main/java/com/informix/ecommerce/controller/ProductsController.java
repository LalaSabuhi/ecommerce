package com.informix.ecommerce.controller;

import com.informix.ecommerce.entity.Products;
import com.informix.ecommerce.entity.Users;
import com.informix.ecommerce.service.ProductsService;
import com.informix.ecommerce.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

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
    public String searchJobs(Model model) {

        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            model.addAttribute("username", currentUsername);
        }

        model.addAttribute("user", currentUserProfile);

        return "dashboard";
    }
    @GetMapping("/dashboard/add")
    public String addJobs(Model model) {
        model.addAttribute("products", new Products());
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-products";
    }

    @PostMapping("/dashboard/addNew")
    public String addNew(Products products, Model model) {

        Users user = usersService.getCurrentUser();
        if (user != null) {
            products.setPostedById(user);
        }
        products.setPostedDate(new Date());
        model.addAttribute("products", products);
        productsService.addNew(products);
        return "redirect:/dashboard/";
    }
}