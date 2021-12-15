package com.example.myproject.service.impl;

import com.example.myproject.model.entity.CommentEntity;
import com.example.myproject.model.entity.OfferEntity;
import com.example.myproject.model.service.CommentServiceModel;
import com.example.myproject.model.view.CommentViewModel;
import com.example.myproject.repository.OfferRepository;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.service.CommentService;
import com.example.myproject.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(OfferRepository offerRepository, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    @Override
    public List<CommentViewModel> getComments(Long courseId) {
        Optional<OfferEntity> course = offerRepository.findById(courseId);

        if (course.isEmpty()){
            throw new ObjectNotFoundException("Course with id " + courseId + " is not found.");
        }
            return course
                    .get()
                    .getComments()
                    .stream().map(this::mapAsComment)
                    .collect(Collectors.toList());

    }

    private CommentViewModel mapAsComment(CommentEntity comment){
        CommentViewModel commentViewModel = new CommentViewModel();

        commentViewModel.setId(comment.getId());
        commentViewModel.setCanApprove(true);
        commentViewModel.setCanDelete(true);
        commentViewModel.setCreated(LocalDateTime.now());
        commentViewModel.setComment(comment.getComment());
        commentViewModel.setAuthor(comment.getAuthor().getUsername());

        return commentViewModel;
    }

    @Override
    public CommentViewModel createComment(CommentServiceModel commentServiceModel) {
        var offer = offerRepository.findById(
                commentServiceModel.getCourseId())
        .orElseThrow(()-> new ObjectNotFoundException("Course with id" + commentServiceModel.getCourseId() + " is not found"));

        var author = userRepository.findByUsername(commentServiceModel.getAuthor())
                .orElseThrow(()-> new ObjectNotFoundException("Author with username " + commentServiceModel.getAuthor() + " is not found"));


        CommentEntity comment = new CommentEntity();
        comment.setCanApprove(false);
        comment.setComment(commentServiceModel.getComment());
        comment.setCreated(LocalDateTime.now());
        comment.setOffer(offer);
        comment.setAuthor(author);
    }

}
