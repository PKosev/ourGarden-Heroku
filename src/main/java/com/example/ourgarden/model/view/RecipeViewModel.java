package com.example.ourgarden.model.view;

import com.example.ourgarden.model.entity.PictureEntity;
import com.example.ourgarden.model.entity.UserEntity;

import java.util.Set;

public class RecipeViewModel {

    private Long id;
    private String title;
    private PictureViewModel mainPicture;
    private String productsNeeded;
    private String wayOfPreparation;
    private UserViewModel author;
    private Long views;
    private Set<PictureViewModel> pictures;

    public RecipeViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PictureViewModel getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(PictureViewModel mainPicture) {
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

    public UserViewModel getAuthor() {
        return author;
    }

    public void setAuthor(UserViewModel author) {
        this.author = author;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Set<PictureViewModel> getPictures() {
        return pictures;
    }

    public void setPictures(Set<PictureViewModel> pictures) {
        this.pictures = pictures;
    }
}
