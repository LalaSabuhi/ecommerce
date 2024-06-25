package com.informix.ecommerce.controller;

import com.informix.ecommerce.entity.Products;
import com.informix.ecommerce.service.ProductsService;
import com.informix.ecommerce.service.UsersService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductApplyController {
    private final ProductsService productsService;

    private final UsersService usersService;

    @Autowired
    public ProductApplyController(ProductsService productsService, UsersService usersService){
        this.productsService=productsService;
        this.usersService=usersService;
    }
    @GetMapping("/product-apply/{id}")
    public String display(@PathVariable("id") int id, Model model){
        Products productDetails = productsService.getOne(id);
        System.out.println("Image Path: " + productDetails.getPhotosImagePath());
        model.addAttribute("productDetails", productDetails);
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "product-detail";
    }
    @PostMapping("/dashboard/edit/{id}")
    public String editProduct(@PathVariable("id")int id, Model model){
        Products products = productsService.getOne(id);
        model.addAttribute("products", products);
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-products";
    }
}
