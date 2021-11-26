package com.example.ourgarden.service;

import com.example.ourgarden.model.binding.RecipeBindingModel;
import com.example.ourgarden.model.entity.RecipeEntity;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface RecipeService {
    void creatRecipe(RecipeBindingModel recipeBindingModel, Principal principal) throws IOException;

    List<RecipeEntity> findMyRecipes(Principal principal);

    List<RecipeEntity> findAll();

    RecipeEntity findRecipeById(Long id);

    void deleteRecipeById(Long id);

    void addPictureToRecipe(Long id, RecipeBindingModel recipeBindingModel) throws IOException;
}
