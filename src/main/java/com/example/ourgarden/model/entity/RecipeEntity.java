package com.example.ourgarden.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipes")
public class RecipeEntity extends BaseEntity {
    private String title;
    private PictureEntity mainPicture;
    private String productsNeeded;
    private String wayOfPreparation;
    private UserEntity author;
    private Long views;
    private Set<PictureEntity> pictures;


    public RecipeEntity() {
        pictures = new HashSet<>();
    }

    @Column(unique = true, nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(columnDefinition = "TEXT")
    public String getProductsNeeded() {
        return productsNeeded;
    }

    public void setProductsNeeded(String productsNeeded) {
        this.productsNeeded = productsNeeded;
    }

    @Column(columnDefinition = "TEXT")
    public String getWayOfPreparation() {
        return wayOfPreparation;
    }

    public void setWayOfPreparation(String wayOfPreparation) {
        this.wayOfPreparation = wayOfPreparation;
    }

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    public Set<PictureEntity> getPictures() {
        return pictures;
    }

    public void setPictures(Set<PictureEntity> pictures) {
        this.pictures = pictures;
    }

    public void addPicture(PictureEntity picture){
        this.pictures.add(picture);
    }

    public void removePicture(PictureEntity picture){
        this.pictures.remove(picture);
    }

    @OneToOne
    public PictureEntity getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(PictureEntity mainPicture) {
        this.mainPicture = mainPicture;
    }


    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    @OneToOne
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }
}
