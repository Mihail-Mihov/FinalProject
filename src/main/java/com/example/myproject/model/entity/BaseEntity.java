package com.example.myproject.model.entity;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract  class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    private Instant created;
//
//    private Instant modified;


//    @Column(nullable = false)
//    public Instant getCreated() {
//        return created;
//    }
//
//    public void setCreated(Instant created) {
//        this.created = created;
//    }
//
//    public Instant getModified() {
//        return modified;
//    }
//
//    public void setModified(Instant modified) {
//        this.modified = modified;
//    }
}
