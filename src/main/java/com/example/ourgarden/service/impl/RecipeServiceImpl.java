package com.example.ourgarden.service.impl;

import com.example.ourgarden.model.binding.RecipeBindingModel;
import com.example.ourgarden.model.entity.PictureEntity;
import com.example.ourgarden.model.entity.RecipeEntity;
import com.example.ourgarden.model.entity.UserEntity;
import com.example.ourgarden.repository.RecipeRepository;
import com.example.ourgarden.service.PictureService;
import com.example.ourgarden.service.RecipeService;
import com.example.ourgarden.service.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final UserService userService;
    private final RecipeRepository recipeRepository;
    private final PictureService pictureService;

    public RecipeServiceImpl(UserService userService, RecipeRepository recipeRepository, PictureService pictureService) {
        this.userService = userService;
        this.recipeRepository = recipeRepository;
        this.pictureService = pictureService;
    }

    @Override
    public void creatRecipe(RecipeBindingModel recipeBindingModel,Principal principal) throws IOException {
        UserEntity user = userService.findByUsername(principal.getName());
        PictureEntity picture = pictureService.createPicture(recipeBindingModel.getMainPicture(),recipeBindingModel.getTitle());
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setTitle(recipeBindingModel.getTitle());
        recipeEntity.setProductsNeeded(recipeBindingModel.getProductsNeeded());
        recipeEntity.setWayOfPreparation(recipeBindingModel.getWayOfPreparation());
        recipeEntity.setAuthor(user);
        recipeEntity.setMainPicture(picture);
        recipeRepository.save(recipeEntity);
    }
    @Override
    public void addPictureToRecipe(Long id, RecipeBindingModel recipeBindingModel) throws IOException {
        RecipeEntity recipe = recipeRepository.findById(id).orElse(null);
        if (recipe != null){
            PictureEntity picture = pictureService.createPicture(recipeBindingModel.getMainPicture(),recipe.getTitle()+"_"+recipe.getPictures().size());
            recipe.addPicture(picture);
            recipeRepository.save(recipe);
        }
    }

    @Override
    public List<RecipeEntity> findMyRecipes(Principal principal) {
        return recipeRepository.findAllByAuthor_Username(principal.getName());
    }

    @Override
    public List<RecipeEntity> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public RecipeEntity findRecipeById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteRecipeById(Long id) {
        RecipeEntity recipe = recipeRepository.findById(id).orElse(null);
        List<String> publicIds = new ArrayList<>();
        if (recipe != null){
            pictureService.deletePicture(recipe.getMainPicture().getPublicId());
            for (PictureEntity p:recipe.getPictures()) {
                publicIds.add(p.getPublicId());
            }
            recipe.setPictures(null);
            recipeRepository.save(recipe);
            recipeRepository.delete(recipe);
        }
        for (String s:publicIds) {
            pictureService.deletePicture(s);
        }
    }


}
