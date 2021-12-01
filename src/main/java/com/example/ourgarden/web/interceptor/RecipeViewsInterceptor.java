package com.example.ourgarden.web.interceptor;

import com.example.ourgarden.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RecipeViewsInterceptor implements HandlerInterceptor {
    private final Logger LOG = LoggerFactory.getLogger(RecipeViewsInterceptor.class);
    private final RecipeService recipeService;

    public RecipeViewsInterceptor(RecipeService recipeService) {

        this.recipeService = recipeService;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            Long id = Long.parseLong(request.getRequestURI().replaceFirst("/recipes/","").replaceFirst("/viewRecipeDetails",""));
            LOG.info("Add 1 view to the recipe with the extracted id = " + id);
            recipeService.addViewToId(id);
        }catch (Exception ignored){

        }

    }
}
