package com.example.myproject.service.impl;

import com.example.myproject.model.entity.CommentEntity;
import com.example.myproject.model.entity.OfferEntity;
import com.example.myproject.model.service.CommentServiceModel;
import com.example.myproject.model.view.CommentViewModel;
import com.example.myproject.repository.CommentRepository;
import com.example.myproject.repository.OfferRepository;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.service.CommentService;
import com.example.myproject.web.exception.ObjectNotFoundException;
import lombok.Builder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public CommentServiceImpl(OfferRepository offerRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
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

        LocalDateTime now = LocalDateTime.now();
        String formatDateTime = now.format(FORMATTER);

        return CommentViewModel.builder()
                .id(comment.getId())
                .canApprove(true)
                .canDelete(true)
                .created(formatDateTime)
                .comment(comment.getComment())
                .author(comment.getAuthor().getUsername())
                .build();
    }

    @Override
    public CommentViewModel createComment(CommentServiceModel commentServiceModel) {
        var offer = offerRepository.findById(
                commentServiceModel.getCourseId())
        .orElseThrow(()-> new ObjectNotFoundException("Course with id" + commentServiceModel.getCourseId() + " is not found"));

        var author = userRepository.findByUsername(commentServiceModel.getAuthor())
                .orElseThrow(()-> new ObjectNotFoundException("Author with username " + commentServiceModel.getAuthor() + " is not found"));

        LocalDateTime now = LocalDateTime.now();
        String formatDateTime = now.format(FORMATTER);

        CommentEntity comment = CommentEntity.builder()
                .canApprove(false)
                .comment(commentServiceModel.getComment())
                .created(formatDateTime)
                .offer(offer)
                .author(author)
                .build();

        CommentEntity save = commentRepository.save(comment);

        return mapAsComment(save);
    }

}
