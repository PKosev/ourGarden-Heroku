package com.example.ourgarden.service.impl;

import com.example.ourgarden.model.binding.OrderBindingModel;
import com.example.ourgarden.model.entity.CommentEntity;
import com.example.ourgarden.model.entity.DayEntity;
import com.example.ourgarden.model.entity.OrderEntity;
import com.example.ourgarden.model.entity.UserEntity;
import com.example.ourgarden.model.view.CommentViewModel;
import com.example.ourgarden.model.view.DayViewModel;
import com.example.ourgarden.model.view.OrderViewModel;
import com.example.ourgarden.model.view.UserViewModel;
import com.example.ourgarden.repository.CommentRepository;
import com.example.ourgarden.repository.DayRepository;
import com.example.ourgarden.repository.OrderRepository;
import com.example.ourgarden.service.DayService;
import com.example.ourgarden.service.OrderService;
import com.example.ourgarden.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    //goodPractice to call service of Comment instead
    private final CommentRepository commentRepository;
    private final DayService dayService;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(UserService userService, OrderRepository orderRepository, CommentRepository commentRepository, DayRepository dayRepository, DayService dayService, ModelMapper modelMapper) {
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.commentRepository = commentRepository;
        this.dayService = dayService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addOffer(DayViewModel day, OrderBindingModel orderBindingModel) {
        UserEntity user = userService.findByUsername(orderBindingModel.getIdentity());

        internalAddOrder(day, orderBindingModel, user);
    }
    @Override
    public void addOrderByNumber(DayViewModel day, OrderBindingModel orderBindingModel) {
        UserEntity user = userService.findByPhoneNumber(orderBindingModel.getIdentity());

        internalAddOrder(day, orderBindingModel, user);
    }

    private void internalAddOrder(DayViewModel day, OrderBindingModel orderBindingModel, UserEntity user) {
        DayEntity dayEntity = modelMapper.map(day,DayEntity.class);
        OrderEntity order = orderRepository.findByUserAndDayEntity(user,dayEntity);
        if (order == null){
            OrderEntity order1 = new OrderEntity();
            order1.setDate(LocalDate.now());
            addComment(orderBindingModel, user, order1);
            order1.setReady(false);
            order1.setDayEntity(dayEntity);
            order1.setQuantity(orderBindingModel.getQuantity());
            order1.setUser(user);
            orderRepository.save(order1);
            userService.addOrder(order1);
            dayService.addOrder(order1);
            return;
        }
        order.setQuantity(order.getQuantity().add(orderBindingModel.getQuantity()));
        addComment(orderBindingModel, user, order);
        orderRepository.save(order);
        userService.addOrder(order);
        dayService.addOrder(order);
    }

    @Override
    public List<OrderViewModel> findAllByDateAfter(LocalDate minusDays) {
        return orderRepository.findAllByDayEntity_DateAfter(minusDays)
                .stream().map(order -> modelMapper.map(order,OrderViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(Long id) {
        OrderEntity order = orderRepository.findById(id).orElse(null);
        assert order != null;
        order.setReady(true);
        orderRepository.save(order);
    }

    @Override
    public List<OrderViewModel> findAllByDateAfterAndNotReady(LocalDate minusDays) {
        return orderRepository.findAllByReadyAndDayEntity_DateAfter(false,minusDays)
                .stream().map(order -> modelMapper.map(order,OrderViewModel.class))
                .collect(Collectors.toList());
    }

    private void addComment(OrderBindingModel orderBindingModel, UserEntity user, OrderEntity order) {
        if (!orderBindingModel.getComment().isEmpty()){
            CommentEntity comment = new CommentEntity();
            comment.setComment(orderBindingModel.getComment());
            comment.setDateTime(LocalDateTime.now());
            comment.setUser(user);
            commentRepository.save(comment);
            order.addComment(comment);
        }
    }

    @Override
    public List<OrderViewModel> findByUserNameAndDateAfter(String name, LocalDate minusDays) {
       return orderRepository
               .findAllByUser_UsernameAndDayEntity_DateAfter(name,minusDays)
               .stream().map(order -> modelMapper.map(order,OrderViewModel.class))
               .collect(Collectors.toList());
    }

    @Override
    public void removeOrder(Long id) {
        OrderEntity order = orderRepository.findById(id).orElse(null);
        assert order != null;
        order.setQuantity(BigDecimal.ZERO);
        dayService.removeOrder(order);
        userService.removeOrder(order);
        orderRepository.delete(order);
    }

    @Override
    public OrderViewModel findById(Long id) {
        return orderRepository.findById(id).map(order -> {

            OrderViewModel orderViewModel = new OrderViewModel();
            orderViewModel.setId(order.getId());
            orderViewModel.setQuantity(order.getQuantity());
            orderViewModel.setUser(modelMapper.map(order.getUser(), UserViewModel.class));
            orderViewModel.setDate(order.getDate());
            orderViewModel.setDayEntity(modelMapper.map(order.getDayEntity(), DayViewModel.class));
            order.getComments().stream().sorted(Comparator.comparing(CommentEntity::getDateTime)).forEach(comment -> {
                CommentViewModel commentViewModel = new CommentViewModel();
                commentViewModel.setComment(comment.getComment());
                commentViewModel.setDateTime(comment.getDateTime());
                commentViewModel.setUsername(comment.getUser().getUsername());
                commentViewModel.setFirstName(comment.getUser().getFirstName());
                commentViewModel.setLastName(comment.getUser().getLastName());
                orderViewModel.addComment(commentViewModel);
            });
            return orderViewModel;
        }).orElse(null);
    }

    @Override
    public void addComment(Long id, String comment, String username) {
        UserEntity user = userService.findByUsername(username);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setComment(comment);
        commentEntity.setDateTime(LocalDateTime.now());
        commentEntity.setUser(user);
        commentRepository.save(commentEntity);
        OrderEntity order = orderRepository.findById(id).orElse(null);
        assert order != null;
        order.addComment(commentEntity);
        orderRepository.save(order);
    }
}
