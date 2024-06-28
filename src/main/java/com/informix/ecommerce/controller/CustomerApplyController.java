package com.informix.ecommerce.controller;

import com.informix.ecommerce.entity.*;
import com.informix.ecommerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerApplyController {

    private final ProductsService productsService;
    private final UsersService usersService;
    private final CustomerApplyService customerApplyService;
    private final CustomerSaveService customerSaveService;
    private final SellerProfileService sellerProfileService;
    private final CustomerProfileService customerProfileService;

    @Autowired
    public CustomerApplyController(ProductsService productsService, UsersService usersService, CustomerApplyService customerApplyService, CustomerSaveService customerSaveService, SellerProfileService sellerProfileService, CustomerProfileService customerProfileService) {
        this.productsService = productsService;
        this.usersService = usersService;
        this.customerApplyService = customerApplyService;
        this.customerSaveService = customerSaveService;
        this.sellerProfileService = sellerProfileService;
        this.customerProfileService = customerProfileService;
    }

    @GetMapping("product-details-apply/{id}")
    public String display(@PathVariable("id") int id, Model model) {
        Products productDetails = productsService.getOne(id);
        List<CustomerApply> customerApplyList = customerApplyService.getProductApplicants(productDetails);
        List<CustomerSave> customerSaveList = customerSaveService.getProductApplicants(productDetails);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Seller"))) {
                SellerProfile user = sellerProfileService.getCurrentSellerProfile();
                if (user != null) {
                    model.addAttribute("applyList", customerApplyList);
                }
            } else {
                CustomerProfile user = customerProfileService.getCurrentCustomerProfile();
                if (user != null) {
                    boolean exists = false;
                    boolean saved = false;
                    for (CustomerApply customerApply : customerApplyList) {
                        if (customerApply.getUserId().getUserAccountId() == user.getUserAccountId()) {
                            exists = true;
                            break;
                        }
                    }
                    for (CustomerSave customerSave : customerSaveList) {
                        if (customerSave.getUserId().getUserAccountId() == user.getUserAccountId()) {
                            saved = true;
                            break;
                        }
                    }
                    model.addAttribute("alreadyApplied", exists);
                    model.addAttribute("alreadySaved", saved);
                }
            }
        }

        CustomerApply customerApply = new CustomerApply();
        model.addAttribute("applyProduct", customerApply);

        model.addAttribute("productDetails", productDetails);
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "product-details";
    }

    @PostMapping("product-details/apply/{id}")
    public String apply(@PathVariable("id") int id, CustomerApply customerApply) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersService.findByEmail(currentUsername);
            Optional<CustomerProfile> customerProfile = customerProfileService.getOne(user.getUserId());
            Products product = productsService.getOne(id);
            if (customerProfile.isPresent() && product != null) {
                customerApply.setUserId(customerProfile.get());
                customerApply.setProduct(product);
                customerApply.setApplyDate(new Date());
            } else {
                throw new RuntimeException("User not found");
            }
            customerApplyService.addNew(customerApply);
        }

        return "redirect:/dashboard/";
    }
}
