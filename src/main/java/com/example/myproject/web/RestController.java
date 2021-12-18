package com.example.myproject.web;

import com.example.myproject.model.binding.NewCommentBindModel;
import com.example.myproject.model.service.CommentServiceModel;
import com.example.myproject.model.validator.ApiError;
import com.example.myproject.model.view.CommentViewModel;
import com.example.myproject.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public RestController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/api/{courseId}/comments")
    public ResponseEntity<List<CommentViewModel>> getComments(
            @PathVariable Long courseId
            , Principal principal
    ) {
        return ResponseEntity.ok(commentService.getComments(courseId));
    }


    @PostMapping ("/api/{courseId}/comments")
    public ResponseEntity<CommentViewModel> newComment(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long courseId,
            @RequestBody @Valid NewCommentBindModel newCommentBindModel
            ){
          CommentServiceModel commentServiceModel =  modelMapper.map(newCommentBindModel, CommentServiceModel.class);
          commentServiceModel.setAuthor(principal.getUsername());
          commentServiceModel.setCourseId(courseId);

          CommentViewModel newComment =
          commentService.createComment(commentServiceModel);

          URI locationOfComment =
                  URI.create(String.format("/api/%s/comment/%s", courseId, newComment.getId()));

          return ResponseEntity.created(locationOfComment).body(newComment);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> onValidationFaulure(MethodArgumentNotValidException exception){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        exception.getFieldErrors().forEach(e -> apiError.addFieldWithError(e.getField()));

        return ResponseEntity.badRequest().body(apiError);
    }

}
