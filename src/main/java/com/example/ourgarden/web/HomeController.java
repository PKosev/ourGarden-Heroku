package com.example.ourgarden.web;

import com.example.ourgarden.model.binding.OrderBindingModel;
import com.example.ourgarden.model.entity.DayEntity;
import com.example.ourgarden.model.view.DayViewModel;
import com.example.ourgarden.service.DayService;
import com.example.ourgarden.service.OrderService;
import com.example.ourgarden.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
