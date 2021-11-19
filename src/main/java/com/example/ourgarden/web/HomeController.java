package com.example.ourgarden.web;

import com.example.ourgarden.model.binding.OrderBindingModel;
import com.example.ourgarden.model.entity.DayEntity;
import com.example.ourgarden.model.entity.UserEntity;
import com.example.ourgarden.model.view.DayViewModel;
import com.example.ourgarden.service.DayService;
import com.example.ourgarden.service.OrderService;
import com.example.ourgarden.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String home(Model model){
        List<DayViewModel> dayProducts = dayService.findByDateAndActive(LocalDate.now().minusDays(1));
        Set<LocalDate> dates = new HashSet<>();
        for (DayViewModel day: dayProducts) {
            dates.add(day.getDate());
        }
        List<LocalDate> sortedDates = dates.stream().sorted().collect(Collectors.toList());
        model.addAttribute("dayProducts", dayProducts);
        model.addAttribute("sortedDates", sortedDates);
        return "index";
    }

    @PostMapping("/order/{id}/add")
    public String addOffer(@PathVariable Long id, @Valid OrderBindingModel orderBindingModel,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal){
        //todo trow error when not enough quantity
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("offerBindingModel", orderBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerBindingModel",bindingResult);

            return "redirect:/";
        }
        DayEntity dayEntity = dayService.findByID(id);
        //UserEntity user = userService.findByUsername(principal.getName());
        orderService.addOffer(principal.getName(),dayEntity,orderBindingModel);
        return "redirect:/";
    }

    @ModelAttribute
    public OrderBindingModel orderBindingModel(){
        return new OrderBindingModel();
    }
}
