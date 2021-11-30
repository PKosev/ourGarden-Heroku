package com.example.ourgarden.web;

import com.example.ourgarden.model.binding.DayAddStockBindingModel;
import com.example.ourgarden.model.binding.OrderBindingModel;
import com.example.ourgarden.model.entity.ProductEntity;
import com.example.ourgarden.model.view.DayViewModel;
import com.example.ourgarden.model.view.OrderViewModel;
import com.example.ourgarden.repository.ProductRepository;
import com.example.ourgarden.service.DayService;
import com.example.ourgarden.service.OrderService;
import com.example.ourgarden.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/staff")
public class StaffController {
    private final DayService dayService;
    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final UserService userService;

    public StaffController(DayService dayService, ProductRepository productRepository, OrderService orderService, UserService userService) {
        this.dayService = dayService;
        this.productRepository = productRepository;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/orders/makeOrder")
    public String makeOrderStaff(Model model){
        List<DayViewModel> dayProducts = dayService.findByDateAndActive(LocalDate.now().minusDays(1));
        Set<LocalDate> dates = new HashSet<>();
        for (DayViewModel day: dayProducts) {
            dates.add(day.getDate());
        }

        List<LocalDate> sortedDates = dates.stream().sorted().collect(Collectors.toList());
        model.addAttribute("dayProducts", dayProducts);
        model.addAttribute("sortedDates", sortedDates);

        return "makeOrderStaff";
    }

    @PostMapping("/orders/{id}/add")
    public String addOrderStaff(@PathVariable Long id,@Valid OrderBindingModel orderBindingModel){
        DayViewModel dayEntity = dayService.findByID(id);
        if (orderBindingModel.getQuantity().compareTo(dayEntity.getMaxQuantity()) > 0){
            orderBindingModel.setQuantity(dayEntity.getMaxQuantity());
        }
        if (orderBindingModel.getIdentity().isBlank()){
            return "redirect:/staff/orders/makeOrder";
        }

        try{
            Integer number = Integer.parseInt(orderBindingModel.getIdentity());
            if (userService.findByPhoneNumber(orderBindingModel.getIdentity()) == null){
                return "redirect:/staff/orders/makeOrder";
            }
            orderService.addOrderByNumber(dayEntity,orderBindingModel);
        }catch (NumberFormatException e){
            if (userService.findByUsername(orderBindingModel.getIdentity()) == null){
                return "redirect:/staff/orders/makeOrder";
            }
            orderService.addOffer(dayEntity,orderBindingModel);
        }
        return "redirect:/staff/orders/makeOrder";
    }
    @GetMapping("/orders/viewOrders")
    public String staffViewOrders(Model model){
        List<OrderViewModel> orders = orderService.findAllByDateAfter(LocalDate.now().minusDays(1));
        Set<LocalDate> dates = new HashSet<>();
        for (OrderViewModel order: orders) {

            dates.add(order.getDayEntity().getDate());
        }

        List<LocalDate> sortedDates = dates.stream().sorted().collect(Collectors.toList());
        LocalDate dateNow = LocalDate.now();
        model.addAttribute("orders", orders);
        model.addAttribute("sortedDates", sortedDates);
        model.addAttribute("dateNow", dateNow);
        return "staffViewOrders";
    }
    @GetMapping("/orders/viewOrders/Rest")
    public String staffViewOrdersRest(){

        return "staffViewOrdersRest";
    }

    @GetMapping("/addStock")
    public String addStock(Model model){
        List<ProductEntity> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "addStock";
    }

    @PostMapping("/addStock")
    public String addStock(@Valid DayAddStockBindingModel dayAddStockBindingModel,
                      BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("dayAddStockModel", dayAddStockBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.dayAddStockModel",bindingResult);

            return "redirect:addStock";
        }
        dayService.addOrCreate(dayAddStockBindingModel);
        return "redirect:/staff/stockView";
    }
    @GetMapping("/stockView")
    public String stockView(Model model){
        List<DayViewModel> dayProducts = dayService.findByDate(LocalDate.now().minusDays(1));
        Set<LocalDate> dates = new HashSet<>();
        for (DayViewModel day: dayProducts) {
            dates.add(day.getDate());
        }
        List<LocalDate> sortedDates = dates.stream().sorted().collect(Collectors.toList());
        model.addAttribute("dayProducts", dayProducts);
        model.addAttribute("sortedDates", sortedDates);
        return "stockView";
    }
    @PostMapping("/stockView/{id}/block")
    public String blockStock(@PathVariable Long id){
        dayService.blockDayProduct(id);
        return "redirect:/staff/stockView";
    }


    @ModelAttribute
    public DayAddStockBindingModel dayAddStockModel(){
        return new DayAddStockBindingModel();
    }
    @ModelAttribute
    public OrderBindingModel orderBindingModel(){
        return new OrderBindingModel();
    }
}
