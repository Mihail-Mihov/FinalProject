package com.example.myproject.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewCommentBindModel {

    @NotBlank
    @Size(min = 10)
    private String comment;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
