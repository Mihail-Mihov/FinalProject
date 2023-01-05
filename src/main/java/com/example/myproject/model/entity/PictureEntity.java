package com.example.myproject.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name ="pictures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PictureEntity extends BaseEntity {


    private String title;
    private String imageUrl;
    @ManyToOne
    private UserEntity author;
    @ManyToOne
    private OfferEntity offer;


//    public String getPublicId() {
//        return publicId;
//    }
//
//    public void setPublicId(String publicId) {
//        this.publicId = publicId;
//    }
}
