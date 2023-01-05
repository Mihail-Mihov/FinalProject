package com.example.myproject.model.entity;

import lombok.*;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OfferEntity extends BaseEntity{

    @Column(nullable = false)
    private String name;
    private Integer rate;
    // TODO @Column(columnDefinition = "longtext")
    @Lob@Enumerated(EnumType.STRING)
    private String imageUrl;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    @Lob
    private String description;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private UserEntity author;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private CategoryEnum category;
    @OneToMany(mappedBy = "offer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
    @OneToMany(mappedBy = "offer", fetch = FetchType.LAZY)
    private List<PictureEntity> pictures;
}
