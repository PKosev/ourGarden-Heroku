package com.example.ourgarden.web;

import com.example.ourgarden.model.binding.DayAddStockBindingModel;
import com.example.ourgarden.model.entity.DayEntity;
import com.example.ourgarden.model.entity.ProductEntity;
import com.example.ourgarden.model.service.DayAddStockServiceModel;
import com.example.ourgarden.model.view.DayViewModel;
import com.example.ourgarden.repository.ProductRepository;
import com.example.ourgarden.service.DayService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private final ModelMapper modelMapper;

    public StaffController(DayService dayService, ProductRepository productRepository, ModelMapper modelMapper) {
        this.dayService = dayService;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
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
        DayEntity dayEntity = dayService.findByDateAndProduct(dayAddStockBindingModel.getDate(), dayAddStockBindingModel.getProductNameEnum());
        if (dayEntity != null){
            dayEntity.setQuantity(dayEntity.getQuantity().add(dayAddStockBindingModel.getQuantity()));
            dayService.save(dayEntity);
        }else {
            dayService.addStock(modelMapper.map(dayAddStockBindingModel, DayAddStockServiceModel.class));
        }
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


    @ModelAttribute
    public DayAddStockBindingModel dayAddStockModel(){
        return new DayAddStockBindingModel();
    }
}
