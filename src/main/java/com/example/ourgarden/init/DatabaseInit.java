package com.example.ourgarden.init;

import com.example.ourgarden.service.ProductService;
import com.example.ourgarden.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements CommandLineRunner {
    private final ProductService productService;
    private final UserService userService;

    public DatabaseInit(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        productService.initProducts();
        userService.initAdmin();
    }
}
