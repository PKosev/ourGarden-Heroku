package com.example.ourgarden.web;

import com.example.ourgarden.model.binding.RecipeBindingModel;
import com.example.ourgarden.model.entity.RecipeEntity;
import com.example.ourgarden.model.view.RecipeViewModel;
import com.example.ourgarden.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
//todo Create RecipeViewModel and GalleryView in RecipeDetails
@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/shareRecipe")
    public String addPicture(){
    return "addRecipe";
    }

    @PostMapping("/shareRecipe")
    public String addPicture(RecipeBindingModel recipeBindingModel, Principal principal) throws IOException {
        recipeService.creatRecipe(recipeBindingModel,principal);
        return "redirect:/recipes/viewRecipes/mine";
    }

    @GetMapping("/viewRecipes/all")
    public String allRecipes(Model model){
        List<RecipeViewModel> recipes = recipeService.findAll();
        model.addAttribute("recipes",recipes);
        return "viewRecipes";
    }
    @GetMapping("/viewRecipes/mine")
    public String mineRecipes(Model model, Principal principal){
        List<RecipeViewModel> recipes = recipeService.findMyRecipes(principal);
        model.addAttribute("recipes",recipes);
        return "viewRecipes";
    }
    @GetMapping("/{id}/viewRecipeDetails")
    public String viewRecipe(@PathVariable Long id,Model model, Principal principal){
        RecipeViewModel recipe = recipeService.findRecipeById(id);
        if (recipe != null){
            model.addAttribute("recipe",recipe);
            model.addAttribute("isOwner",recipe.getAuthor().getUsername().equals(principal.getName()));
            return "viewRecipeDetails";
        }
        return "redirect:/recipes/viewRecipes/mine";
    }
    @PostMapping("/{id}/addPicture")
    public String addPictureToRecipe(@PathVariable Long id,RecipeBindingModel recipeBindingModel) throws IOException {
        recipeService.addPictureToRecipe(id, recipeBindingModel);
        return "redirect:/recipes/"+id+"/viewRecipeDetails";
    }
    @Transactional
    @DeleteMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable Long id){
        recipeService.deleteRecipeById(id);
        return "redirect:/recipes/viewRecipes/mine";
    }

    @ModelAttribute
    public RecipeBindingModel recipeBindingModel(){
        return new RecipeBindingModel();
    }
}