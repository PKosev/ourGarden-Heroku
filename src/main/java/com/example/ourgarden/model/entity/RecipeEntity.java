package com.example.ourgarden.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "recipes")
public class RecipeEntity extends BaseEntity {
    private String name;
    private String description;
    private Set<PictureEntity> pictures;
    private UserEntity author;

    public RecipeEntity() {
    }

    @Column(unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany
    public Set<PictureEntity> getPictures() {
        return pictures;
    }

    public void setPictures(Set<PictureEntity> pictures) {
        this.pictures = pictures;
    }

    @OneToOne
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }
}
