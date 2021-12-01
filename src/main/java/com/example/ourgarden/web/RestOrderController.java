package com.example.ourgarden.web;

import com.example.ourgarden.model.view.DayViewModel;
import com.example.ourgarden.model.view.OrderViewModel;
import com.example.ourgarden.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
public class RestOrderController {

    private final OrderService orderService;

    public RestOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/ordersRest")
    public ResponseEntity<List<OrderViewModel>> getAllOrders(){
        List<OrderViewModel> allOrders = orderService.findAllByDateAfterAndNotReady(LocalDate.now().minusDays(1)).stream().sorted(Comparator.comparing(o -> o.getDayEntity().getDate())).collect(Collectors.toList());
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/datesRest")
    public ResponseEntity<List<LocalDate>> getDates(){
        List<OrderViewModel> allOrders = orderService.findAllByDateAfterAndNotReady(LocalDate.now().minusDays(1));
        Set<LocalDate> dates = new HashSet<>();
        for (OrderViewModel order: allOrders) {
            dates.add(order.getDayEntity().getDate());
        }
        List<LocalDate> sortedDates = dates.stream().sorted().collect(Collectors.toList());
        return ResponseEntity.
                ok(sortedDates);
    }
    @GetMapping("/orders/{id}/ready")
    public ResponseEntity<OrderViewModel> setOrderReady(@PathVariable Long id){
        System.out.println();
        orderService.updateStatus(id);

        return ResponseEntity.
                noContent().
                build();
    }
}
