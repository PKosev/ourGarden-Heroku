package com.example.ourgarden.web;

import com.example.ourgarden.model.binding.UserBindingRegistrationModel;
import com.example.ourgarden.model.service.UserEntityServiceModel;
import com.example.ourgarden.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }



    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserBindingRegistrationModel userBindingRegistrationModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors() || !userBindingRegistrationModel.getPassword().equals(userBindingRegistrationModel.getConfirmPassword()) ||
                userService.findByPhoneNumber(userBindingRegistrationModel.getPhoneNumber()) != null ||
                userService.findByUsername(userBindingRegistrationModel.getUsername()) != null){
            redirectAttributes.addFlashAttribute("userBindingRegistrationModel", userBindingRegistrationModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userBindingRegistrationModel",bindingResult);
            if (userService.findByPhoneNumber(userBindingRegistrationModel.getPhoneNumber()) != null){
                userBindingRegistrationModel.setPhoneNumberExist(true);
            }
            if (userService.findByUsername(userBindingRegistrationModel.getUsername()) != null){
                userBindingRegistrationModel.setUsernameExist(true);
            }
            return "redirect:register";
        }
        userService.registerUser(modelMapper.map(userBindingRegistrationModel, UserEntityServiceModel.class));
        return "redirect:login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login-error")
    public String failedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
            String userName,
            RedirectAttributes attributes
    ) {

        attributes.addFlashAttribute("bad_credentials", true);
        attributes.addFlashAttribute("username", userName);

        return "redirect:/users/login";
    }

    @ModelAttribute
    public UserBindingRegistrationModel userBindingRegistrationModel(){
        return new UserBindingRegistrationModel();
    }
}
