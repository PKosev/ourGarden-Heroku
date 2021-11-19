package com.example.ourgarden.service.impl;

import com.example.ourgarden.model.binding.OrderBindingModel;
import com.example.ourgarden.model.entity.CommentEntity;
import com.example.ourgarden.model.entity.DayEntity;
import com.example.ourgarden.model.entity.OrderEntity;
import com.example.ourgarden.model.entity.UserEntity;
import com.example.ourgarden.repository.CommentRepository;
import com.example.ourgarden.repository.DayRepository;
import com.example.ourgarden.repository.OrderRepository;
import com.example.ourgarden.service.DayService;
import com.example.ourgarden.service.OrderService;
import com.example.ourgarden.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final CommentRepository commentRepository;
    private final DayService dayService;

    public OrderServiceImpl(UserService userService, OrderRepository orderRepository, CommentRepository commentRepository, DayRepository dayRepository, DayService dayService) {
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.commentRepository = commentRepository;
        this.dayService = dayService;
    }

    @Override
    public void addOffer(String name, DayEntity dayEntity, OrderBindingModel orderBindingModel) {
        UserEntity user = userService.findByUsername(name);

        OrderEntity order = new OrderEntity();
        order.setDate(LocalDate.now());
        if (!orderBindingModel.getComment().isEmpty()){
            CommentEntity comment = new CommentEntity();
            comment.setComment(orderBindingModel.getComment());
            comment.setDateTime(LocalDateTime.now());
            comment.setUser(user);
            commentRepository.save(comment);
            order.addComment(comment);
        }
        order.setDayEntity(dayEntity);
        order.setQuantity(orderBindingModel.getQuantity());
        order.setUser(user);
        orderRepository.save(order);
        userService.addOrder(order);
        dayService.addOrder(order);
    }
}
