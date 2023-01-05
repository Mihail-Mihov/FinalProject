package com.example.myproject.model.view;

import com.example.myproject.model.entity.CategoryEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OfferDetailsView {

    private Long id;
    private String description;
    private String imageUrl;
    private Double price;
    private Integer rate;
    private String sellerFullName;
    private CategoryEnum category;
    private boolean canDelete;
    private String name;
//    private Instant created;
//    private Instant modified;

}
