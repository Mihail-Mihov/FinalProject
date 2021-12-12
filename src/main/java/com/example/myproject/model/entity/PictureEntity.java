package com.example.myproject.model.entity;

import javax.persistence.*;

@Entity
@Table(name ="pictures")
public class PictureEntity extends BaseEntity {


    private String title;
    private String imageUrl;
    private UserEntity author;
    private OfferEntity offer;

    public PictureEntity() {
    }

//    public String getPublicId() {
//        return publicId;
//    }
//
//    public void setPublicId(String publicId) {
//        this.publicId = publicId;
//    }

    @ManyToOne
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    @ManyToOne
    public OfferEntity getOffer() {
        return offer;
    }

    public void setOffer(OfferEntity offer) {
        this.offer = offer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
