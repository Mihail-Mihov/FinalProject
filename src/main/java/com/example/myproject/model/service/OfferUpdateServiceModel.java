package com.example.myproject.model.service;

import com.example.myproject.model.entity.CategoryEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OfferUpdateServiceModel {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private CategoryEnum category;
    private boolean canDelete;

}
