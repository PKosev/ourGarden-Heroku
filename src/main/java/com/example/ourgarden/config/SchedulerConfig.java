package com.example.ourgarden.config;


import com.example.ourgarden.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerConfig {

    private final OrderService orderService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerConfig.class);

    public SchedulerConfig(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(cron = "0 17 1 * * *")
    public void deleteOrdersThatAreExpired(){
        orderService.removeAllOrdersThatAreExpired();
        LOGGER.info("Some orders are to be deleted!!!");
    }
}
