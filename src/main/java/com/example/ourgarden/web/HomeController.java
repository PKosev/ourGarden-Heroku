package com.example.ourgarden.web;

import com.example.ourgarden.model.view.DayViewModel;
import com.example.ourgarden.service.DayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final DayService dayService;

    public HomeController(DayService dayService) {
        this.dayService = dayService;
    }

    @GetMapping()
    public String home(Model model){
        daysInformation(model, dayService);
        return "index";
    }

    static void daysInformation(Model model, DayService dayService) {
        List<DayViewModel> dayProducts = dayService.findByDate(LocalDate.now().minusDays(1));
        Set<LocalDate> dates = new HashSet<>();
        for (DayViewModel day: dayProducts) {
            dates.add(day.getDate());
        }
        List<LocalDate> sortedDates = dates.stream().sorted().collect(Collectors.toList());
        model.addAttribute("dayProducts", dayProducts);
        model.addAttribute("sortedDates", sortedDates);
    }
}
