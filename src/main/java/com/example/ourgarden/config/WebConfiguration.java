package com.example.ourgarden.config;

import com.example.ourgarden.web.interceptor.RecipeViewsInterceptor;
import com.example.ourgarden.web.interceptor.StatsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private final  StatsInterceptor statsInterceptor;
    private final RecipeViewsInterceptor recipeViewsInterceptor;


    public WebConfiguration(StatsInterceptor statsInterceptor, RecipeViewsInterceptor recipeViewsInterceptor) {
        this.statsInterceptor = statsInterceptor;
        this.recipeViewsInterceptor = recipeViewsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(statsInterceptor);
        registry.addInterceptor(recipeViewsInterceptor).addPathPatterns("/recipes/{id}/viewRecipeDetails");

    }
}
