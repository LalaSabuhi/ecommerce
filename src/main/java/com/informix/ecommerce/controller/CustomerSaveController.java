package com.informix.ecommerce.controller;

import com.informix.ecommerce.entity.CustomerProfile;
import com.informix.ecommerce.entity.CustomerSave;
import com.informix.ecommerce.entity.Products;
import com.informix.ecommerce.entity.Users;
import com.informix.ecommerce.service.CustomerProfileService;
import com.informix.ecommerce.service.CustomerSaveService;
import com.informix.ecommerce.service.ProductsService;
import com.informix.ecommerce.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerSaveController {
    private final UsersService usersService;
    private final CustomerProfileService customerProfileService;
    private final ProductsService productsService;
    private final CustomerSaveService customerSaveService;
    @Autowired

    public CustomerSaveController(UsersService usersService, CustomerProfileService customerProfileService, ProductsService productsService, CustomerSaveService customerSaveService) {
        this.usersService = usersService;
        this.customerProfileService = customerProfileService;
        this.productsService = productsService;
        this.customerSaveService = customerSaveService;
    }
    @PostMapping("product-details/save/{id}")
    public String save(@PathVariable("id") int id, CustomerSave customerSave) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersService.findByEmail(currentUsername);
            Optional<CustomerProfile> seekerProfile = customerProfileService.getOne(user.getUserId());
            Products products = productsService.getOne(id);
            if (seekerProfile.isPresent() && products != null) {
                customerSave.setProduct(products);
                customerSave.setUserId(seekerProfile.get());
            } else {
                throw new RuntimeException("User not found");
            }
            customerSaveService.addNew(customerSave);
        }
        return "redirect:/dashboard/";
    }

    @GetMapping("saved-products/")
    public String savedJobs(Model model) {

        List<Products> productsPost = new ArrayList<>();
        Object currentUserProfile = usersService.getCurrentUserProfile();

        List<CustomerSave> customerSaveList = customerSaveService.getCandidatesJob((CustomerProfile) currentUserProfile);
        for (CustomerSave customerSave : customerSaveList) {
            productsPost.add(customerSave.getProduct());
        }

        model.addAttribute("productPost", productsPost);
        model.addAttribute("user", currentUserProfile);

        return "saved-products";
    }
}
