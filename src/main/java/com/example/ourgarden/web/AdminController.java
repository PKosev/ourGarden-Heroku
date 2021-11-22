package com.example.ourgarden.web;

import com.example.ourgarden.model.binding.ProductPriceBindingModel;
import com.example.ourgarden.model.view.ProductViewModel;
import com.example.ourgarden.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/editPrices")
    public String editPrices(Model model){
        List<ProductViewModel> products = productService.findAll();
        model.addAttribute("products",products);
        return "editPrices";
    }
    //if (bindingResult.hasErrors() || !userBindingRegistrationModel.getPassword().equals(userBindingRegistrationModel.getConfirmPassword()) ||
    //                userService.findByPhoneNumber(userBindingRegistrationModel.getPhoneNumber()) != null ||
    //                userService.findByUsername(userBindingRegistrationModel.getUsername()) != null){
    //            redirectAttributes.addFlashAttribute("userBindingRegistrationModel", userBindingRegistrationModel);
    //            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userBindingRegistrationModel",bindingResult);
    //            if (userService.findByPhoneNumber(userBindingRegistrationModel.getPhoneNumber()) != null){
    //                userBindingRegistrationModel.setPhoneNumberExist(true);
    //            }
    //            if (userService.findByUsername(userBindingRegistrationModel.getUsername()) != null){
    //                userBindingRegistrationModel.setUsernameExist(true);
    //            }
    //            return "redirect:register";
    //        }
    @PostMapping("/editPrice/{id}")
    public String editProductPrice(@PathVariable Long id,ProductPriceBindingModel productPriceBindingModel){
        if (!(productPriceBindingModel.getPrice() == null) && !productPriceBindingModel.getPrice().equals(BigDecimal.ZERO)){
            productService.editPrice(id, productPriceBindingModel.getPrice());
        }
        return "redirect:/admin/editPrices";
    }

    @ModelAttribute
    public ProductPriceBindingModel productPriceBindingModel(){
        return new ProductPriceBindingModel();
    }
}
