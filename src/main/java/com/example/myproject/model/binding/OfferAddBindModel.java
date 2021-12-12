package com.example.myproject.model.binding;

import com.example.myproject.model.entity.CategoryEnum;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class OfferAddBindModel {

    private Long id;
    private String name;
    private String imageUrl;
    private Double price;
    private String description;
    private CategoryEnum category;

    public OfferAddBindModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NotNull
    @DecimalMin("1")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }
}