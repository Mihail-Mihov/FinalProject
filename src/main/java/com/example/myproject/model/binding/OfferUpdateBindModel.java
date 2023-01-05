package com.example.myproject.model.binding;

import com.example.myproject.model.entity.CategoryEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OfferUpdateBindModel {

    private long id;
    private String name;
    private String imageUrl;
    private Double price;
    private String description;
    private CategoryEnum category;
}
