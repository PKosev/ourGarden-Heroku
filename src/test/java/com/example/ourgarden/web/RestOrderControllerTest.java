package com.example.ourgarden.web;

import com.example.ourgarden.model.binding.DayAddStockBindingModel;
import com.example.ourgarden.model.binding.OrderBindingModel;
import com.example.ourgarden.model.entity.OrderEntity;
import com.example.ourgarden.model.entity.UserEntity;
import com.example.ourgarden.model.view.DayViewModel;
import com.example.ourgarden.model.view.OrderViewModel;
import com.example.ourgarden.repository.*;
import com.example.ourgarden.service.DayService;
import com.example.ourgarden.service.OrderService;
import com.example.ourgarden.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.example.ourgarden.model.entity.enums.ProductNameEnum.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RestOrderControllerTest {
    private Logger LOG = LoggerFactory.getLogger(RestOrderControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DayService dayService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CommentRepository commentRepository;




    @BeforeEach
    public void SetUp(){
            initDayEntry();
            createSomeOrders();
    }

    @AfterEach
    public void tearDown(){
        int count = orderService.findAllByDateAfter(LocalDate.now().minusDays(10)).size();
        for (int i = 1; i <= count; i++) {
            dayService.removeOrder(modelMapper.map(orderService.findById((long) i),OrderEntity.class));
        }
        orderService.removeOrder(1L);
        orderService.removeOrder(2L);
        assertEquals(0,orderService.findAllByDateAfter(LocalDate.now().minusDays(10)).size());
    }


    @Test
    @WithMockUser(username = "admin",roles = "ADMIN")
    void restApiCheck() throws Exception {

        UserEntity user = userRepository.findById(3L).orElse(null);
        List<UserEntity> users = userRepository.findAll();

        assertEquals(3, users.size());
        assertEquals("user", user.getUsername());


        mockMvc.perform(get("/rest/ordersRest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$.[0].user.username", is("user")))
                .andExpect(jsonPath("$.[1].user.username", is("staff")));

        orderService.addComment(1L,"Hello", "user");
        orderService.addComment(1L,"Hello", "user");
        orderService.addComment(1L,"Hello", "user");
        orderService.addComment(1L,"Hello", "user");
        //4 tests + 4 comment adds = 8
        List<OrderViewModel> orders = orderService.findByUserNameAndDateAfter("user",LocalDate.now());
        OrderViewModel order = orderService.findById(1L);

        assertEquals(8,order.getComments().size());
        orderService.updateStatus(1L);
        OrderEntity orderRepositoryById = orderRepository.findById(1L).orElse(null);
        assertEquals(true, orderRepositoryById.getReady());

        mockMvc.perform(get("/rest/datesRest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));
        mockMvc.perform(get("/rest/orders/1/ready"))
                .andExpect(status().is(204));

    }


    private void createSomeOrders() {
        DayViewModel dayViewModel = dayService.findByID(1L);

        OrderBindingModel orderInfo = new OrderBindingModel();
        orderInfo.setQuantity(BigDecimal.valueOf(5));
        orderInfo.setComment("Test");
        orderInfo.setIdentity("user");
        orderService.addOffer(dayViewModel,orderInfo);

        //adding 2nd order
        OrderBindingModel orderInfo2 = new OrderBindingModel();
        orderInfo2.setQuantity(BigDecimal.valueOf(20));
        orderInfo2.setComment("Test");
        orderInfo2.setIdentity("0876673562");
        orderService.addOrderByNumber(dayViewModel, orderInfo2);
        //re-adding for some additional coverage
        orderService.addOrderByNumber(dayViewModel, orderInfo2);


    }

    private void initDayEntry() {
        DayAddStockBindingModel dayAddStockBindingModel = new DayAddStockBindingModel();
        dayAddStockBindingModel.setDate(LocalDate.of(2021,12, 5));
        dayAddStockBindingModel.setQuantity(BigDecimal.valueOf(200));
        dayAddStockBindingModel.setProductNameEnum(TOMATO);
        dayService.addOrCreate(dayAddStockBindingModel);
        //make another 200 quantity more cases covered
        dayService.addOrCreate(dayAddStockBindingModel);
    }
}