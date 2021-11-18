package com.example.ourgarden.web;

import com.example.ourgarden.model.binding.DayAddStockModel;
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
    public String addStock(@Valid DayAddStockModel dayAddStockModel,
                      BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("dayAddStockModel", dayAddStockModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.dayAddStockModel",bindingResult);

            return "redirect:addStock";
        }
        DayEntity dayEntity = dayService.findByDateAnDProduct(dayAddStockModel.getDate(), dayAddStockModel.getProductNameEnum());
        if (dayEntity != null){
            dayEntity.setQuantity(dayEntity.getQuantity().add(dayAddStockModel.getQuantity()));
            dayService.save(dayEntity);
        }else {
            dayService.addStock(modelMapper.map(dayAddStockModel, DayAddStockServiceModel.class));
        }
        return "redirect:/staff/stockView";
    }
    @GetMapping("/stockView")
    public String stockView(Model model){
        HomeController.daysInformation(model, dayService);
        return "stockView";
    }



    @ModelAttribute
    public DayAddStockModel dayAddStockModel(){
        return new DayAddStockModel();
    }
}
