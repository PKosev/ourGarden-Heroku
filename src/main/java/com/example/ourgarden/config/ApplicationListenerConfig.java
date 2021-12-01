package com.example.ourgarden.config;


import com.example.ourgarden.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Component
public class ApplicationListenerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerConfig.class);

    //ToDo make further implementation
    @EventListener(ServletRequestHandledEvent.class)
    public void onServletRequest(ServletRequestHandledEvent event){
        LOGGER.info("Some action at the moment {}", event);
    }
}
