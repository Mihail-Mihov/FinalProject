package com.example.myproject.model.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRoleEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;
}
