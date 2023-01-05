package com.example.myproject.model.binding;

import com.example.myproject.model.entity.CategoryEnum;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OfferAddBindModel {

    private Long id;
    @NotNull
    private String name;
    private String imageUrl;
    @NotNull
    @DecimalMin("1")
    private Double price;
    @NotNull
    private String description;
    private CategoryEnum category;
}