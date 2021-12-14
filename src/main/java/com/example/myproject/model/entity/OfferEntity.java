package com.example.myproject.model.entity;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "offers")
public class OfferEntity extends BaseEntity{

    private String name;
    private Integer rate;
    private String imageUrl;
    private Double price;
    private String description;
    private UserEntity author;
    @Enumerated(STRING)
    @Column(nullable = false)
    private CategoryEnum category;
    private List<CommentEntity> comments;
    private List<PictureEntity> pictures;

    public OfferEntity() {
    }

    @OneToMany(mappedBy = "offer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "offer", fetch = FetchType.LAZY)
    public List<PictureEntity> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureEntity> pictures) {
        this.pictures = pictures;
    }

    // TODO @Column(columnDefinition = "longtext")
    @Lob
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @Column(nullable = false)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(nullable = false)
    @Lob
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }
}
