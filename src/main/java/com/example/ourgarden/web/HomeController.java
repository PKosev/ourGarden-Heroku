package com.example.ourgarden.web;

import com.example.ourgarden.service.DayService;
import com.example.ourgarden.service.OrderService;
import com.example.ourgarden.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class HomeController {
    private final DayService dayService;
    private final OrderService orderService;
    private final UserService userService;

    public HomeController(DayService dayService, OrderService orderService, UserService userService) {
        this.dayService = dayService;
        this.orderService = orderService;
        this.userService = userService;
    }
    @GetMapping()
    public String homeGet(Model model,Principal principal){
        if (principal != null) {
            String names = userService.getNames(principal.getName());
            model.addAttribute("names", names);
        }
        return "index";
    }
    @PostMapping()
    public String homePost(){
        return "redirect:/";
    }
}
