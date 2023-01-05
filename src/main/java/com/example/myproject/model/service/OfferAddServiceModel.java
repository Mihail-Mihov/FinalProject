package com.example.myproject.model.service;

import com.example.myproject.model.entity.CategoryEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OfferAddServiceModel {

    private Long id;
    private String name;
    private String imageUrl;
    private Double price;
    private String description;
    private CategoryEnum category;

}
