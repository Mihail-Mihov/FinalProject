package com.example.myproject.service;

import com.example.myproject.model.service.CommentServiceModel;
import com.example.myproject.model.view.CommentViewModel;

import java.util.List;

public interface CommentService {

    List<CommentViewModel> getComments(Long courseId );

    CommentViewModel createComment(CommentServiceModel commentServiceModel);
}
