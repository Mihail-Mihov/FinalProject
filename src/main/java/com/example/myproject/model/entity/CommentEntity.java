package com.example.myproject.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CommentEntity extends BaseEntity{

    @ManyToOne
    private UserEntity author;
    private String comment;
    @ManyToOne
    private OfferEntity offer;
    private String created;
    private boolean canApprove;
    private boolean canDelete;

}
