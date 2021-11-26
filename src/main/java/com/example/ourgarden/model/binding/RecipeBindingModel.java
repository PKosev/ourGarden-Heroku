package com.example.ourgarden.model.binding;

import org.springframework.web.multipart.MultipartFile;

public class RecipeBindingModel {
    private String title;
    private MultipartFile mainPicture;
    private String productsNeeded;
    private String wayOfPreparation;

    public RecipeBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(MultipartFile mainPicture) {
        this.mainPicture = mainPicture;
    }

    public String getProductsNeeded() {
        return productsNeeded;
    }

    public void setProductsNeeded(String productsNeeded) {
        this.productsNeeded = productsNeeded;
    }

    public String getWayOfPreparation() {
        return wayOfPreparation;
    }

    public void setWayOfPreparation(String wayOfPreparation) {
        this.wayOfPreparation = wayOfPreparation;
    }
}
