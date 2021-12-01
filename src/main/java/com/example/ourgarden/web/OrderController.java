package com.example.ourgarden.web;

import com.example.ourgarden.model.binding.CommentBindingModel;
import com.example.ourgarden.model.binding.OrderBindingModel;
import com.example.ourgarden.model.view.DayViewModel;
import com.example.ourgarden.model.view.OrderViewModel;
import com.example.ourgarden.model.view.ProductViewModel;
import com.example.ourgarden.service.DayService;
import com.example.ourgarden.service.OrderService;
import com.example.ourgarden.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final DayService dayService;
    private final OrderService orderService;
    private final ProductService productService;

    public OrderController(DayService dayService, OrderService orderService, ProductService productService) {
        this.dayService = dayService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/makeOrder")
    public String makeOrder(Model model){
        List<DayViewModel> dayProducts = dayService.findByDateAndActive(LocalDate.now().minusDays(1));
        Set<LocalDate> dates = new HashSet<>();
        for (DayViewModel day: dayProducts) {
            dates.add(day.getDate());
        }

        List<LocalDate> sortedDates = dates.stream().sorted().collect(Collectors.toList());
        model.addAttribute("dayProducts", dayProducts);
        model.addAttribute("sortedDates", sortedDates);
        return "makeOrder";
    }

    @PostMapping("/{id}/add")
    public String addOffer(@PathVariable Long id, @Valid OrderBindingModel orderBindingModel,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal){
        DayViewModel dayEntity = dayService.findByID(id);
        if (orderBindingModel.getQuantity().compareTo(dayEntity.getMaxQuantity()) > 0){
            orderBindingModel.setQuantity(dayEntity.getMaxQuantity());
        }
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("offerBindingModel", orderBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerBindingModel",bindingResult);
            return "redirect:/orders/makeOrder";
        }
        orderBindingModel.setIdentity(principal.getName());
        orderService.addOffer(dayEntity,orderBindingModel);
        return "redirect:/orders/makeOrder";
    }

    @GetMapping("/viewOrders")
    public String userViewOrders(Principal principal, Model model){
        List<OrderViewModel> orders = orderService.findByUserNameAndDateAfter(principal.getName(),LocalDate.now().minusDays(1));
        Set<LocalDate> dates = new HashSet<>();
        for (OrderViewModel order: orders) {
            dates.add(order.getDayEntity().getDate());
        }

        List<LocalDate> sortedDates = dates.stream().sorted().collect(Collectors.toList());
        LocalDate dateNow = LocalDate.now();
        model.addAttribute("orders", orders);
        model.addAttribute("sortedDates", sortedDates);
        model.addAttribute("dateNow", dateNow);
        return "userViewOrders";
    }
    @PostMapping("/{id}/reset")
    public String resetOrder(@PathVariable Long id){
        orderService.removeOrder(id);
        return "redirect:/orders/viewOrders";
    }
    @PostMapping("/{id}/details")
    public String orderDetails(@PathVariable Long id, Model model, Principal principal,CommentBindingModel commentBindingModel){
        if (commentBindingModel.getComment() != null  && !commentBindingModel.getComment().isEmpty()){
            orderService.addComment(id,commentBindingModel.getComment(),principal.getName());
            commentBindingModel.setComment(null);
        }
        OrderViewModel order = orderService.findById(id);
        LocalDate dateNow = LocalDate.now();
        model.addAttribute("currentUser", principal.getName());
        model.addAttribute("order", order);
        model.addAttribute("dateNow", dateNow);
        return "orderDetails";
    }
    @GetMapping("/viewPrices")
    public String viewPrices(Model model){
        List<ProductViewModel> products = productService.findAll();
        model.addAttribute("products",products);
        return "viewPrices";
    }

    @ModelAttribute
    public OrderBindingModel orderBindingModel(){
        return new OrderBindingModel();
    }
    @ModelAttribute
    public CommentBindingModel commentBindingModel(){
        return new CommentBindingModel();
    }
}
