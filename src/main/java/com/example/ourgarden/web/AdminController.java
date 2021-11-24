package com.example.ourgarden.web;

import com.example.ourgarden.model.binding.ProductPriceBindingModel;
import com.example.ourgarden.model.binding.UserBindingRegistrationModel;
import com.example.ourgarden.model.view.ProductViewModel;
import com.example.ourgarden.model.view.UserViewModel;
import com.example.ourgarden.service.ProductService;
import com.example.ourgarden.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.userService = userService;

        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/editPrices")
    public String editPrices(Model model){
        List<ProductViewModel> products = productService.findAll();
        model.addAttribute("products",products);
        return "editPrices";
    }

    @PostMapping("/editPrice/{id}")
    public String editProductPrice(@PathVariable Long id,ProductPriceBindingModel productPriceBindingModel){
        if (!(productPriceBindingModel.getPrice() == null) && !productPriceBindingModel.getPrice().equals(BigDecimal.ZERO)){
            productService.editPrice(id, productPriceBindingModel.getPrice());
        }
        return "redirect:/admin/editPrices";
    }

    @GetMapping("/viewUsers")
    public String viewUsers(Model model){
        List<UserViewModel> activeUsers = userService.findAllActive();
        List<UserViewModel> inActiveUsers = userService.findAllInActive();
        model.addAttribute("activeUsers",activeUsers);
        model.addAttribute("inActiveUsers",inActiveUsers);
        return "viewUsers";
    }

    @PostMapping("/{id}/changeStatus")
    public String changeStatus(@PathVariable Long id){
        userService.changeStatus(id);
        return "redirect:/admin/viewUsers";
    }
    @GetMapping("/{id}/editProfile")
    public String editProfile(@PathVariable Long id, Model model){
        UserViewModel user = userService.findUserById(id);
        model.addAttribute("user",user);
        return "editProfile";
    }
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       UserBindingRegistrationModel userBindingRegistrationModel,
                       RedirectAttributes redirectAttributes){
        if (!userBindingRegistrationModel.getPassword().equals(userBindingRegistrationModel.getConfirmPassword()) ||
                userService.findByPhoneNumber(userBindingRegistrationModel.getPhoneNumber()) != null ||
                userService.findByUsername(userBindingRegistrationModel.getUsername()) != null){
            redirectAttributes.addFlashAttribute("userBindingRegistrationModel", userBindingRegistrationModel);
            if (userService.findByPhoneNumber(userBindingRegistrationModel.getPhoneNumber()) != null){
                userBindingRegistrationModel.setPhoneNumberExist(true);
            }
            return "redirect:/admin/{id}/editProfile";
        }
        UserViewModel user = userService.findUserById(id);
        if (!userBindingRegistrationModel.getAddress().isBlank()){
            user.setAddress(userBindingRegistrationModel.getAddress());
        }
        if (!userBindingRegistrationModel.getFirstName().isBlank()){
            user.setFirstName(userBindingRegistrationModel.getFirstName());
        }
        if (!userBindingRegistrationModel.getLastName().isBlank()){
            user.setLastName(userBindingRegistrationModel.getLastName());
        }
        if (!userBindingRegistrationModel.getPhoneNumber().isBlank()){
            user.setPhoneNumber(userBindingRegistrationModel.getPhoneNumber());
        }
        if (userBindingRegistrationModel.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(userBindingRegistrationModel.getAddress()));
        }
        userService.editRegisteredUser(user,id);
        return "redirect:/admin/{id}/editProfile";
    }

    @ModelAttribute
    public ProductPriceBindingModel productPriceBindingModel(){
        return new ProductPriceBindingModel();
    }
    @ModelAttribute
    public UserBindingRegistrationModel userBindingRegistrationModel(){
        return new UserBindingRegistrationModel();
    }
}
