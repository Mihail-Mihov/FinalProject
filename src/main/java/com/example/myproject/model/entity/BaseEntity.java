package com.example.myproject.model.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract  class BaseEntity {

    private Long id;


//    private Instant created;
//
//    private Instant modified;

    public BaseEntity() {
    }

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
