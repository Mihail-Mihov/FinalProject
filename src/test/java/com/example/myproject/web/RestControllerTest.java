package com.example.myproject.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.example.myproject.model.entity.CommentEntity;
import com.example.myproject.model.entity.OfferEntity;
import com.example.myproject.model.entity.UserEntity;
import com.example.myproject.repository.CommentRepository;
import com.example.myproject.repository.OfferRepository;
import com.example.myproject.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

@WithMockUser("mbmihail")
@SpringBootTest
@AutoConfigureMockMvc
 class RestControllerTest {

    private static final String COMMENT_1 = "comment1";
    private static final String COMMENT_2 = "comment2";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferRepository offerRepository;
//
//    @Autowired
//    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    void setUp(){
        user = new UserEntity();
        user.setPassword("password");
        user.setUsername("qweqwe");
        user.setFirstName("qweasd");
        user.setLastName("qweasd");
        user.setEmail("qweasda@email.com");

        userRepository.save(user);
    }

    @AfterEach
    void deleteAll(){
        offerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testGetComments() throws Exception {
    long offerId = initOffer();

    mockMvc.perform(get("/api/" +offerId +"/comments")).
            andExpect(status().isOk()).
            andExpect(jsonPath("$", hasSize(2))).
            andExpect(jsonPath("$.[0].comment", is(COMMENT_1))).
            andExpect(jsonPath("$.[1].comment", is(COMMENT_2)));
    }

    private long initOffer(){
        OfferEntity testOffer = new OfferEntity();
        testOffer.setAuthor(user);
        testOffer.setName("name");
        testOffer.setDescription("description");
        testOffer.setPrice(55.21);


        OfferEntity save = offerRepository.save(testOffer);

        CommentEntity comment1 = new CommentEntity();
        comment1.setCreated(LocalDateTime.now());
        comment1.setAuthor(user);
        comment1.setComment(COMMENT_1);
        comment1.setCanApprove(true);

        CommentEntity comment2 = new CommentEntity();
        comment2.setCreated(LocalDateTime.now());
        comment2.setAuthor(user);
        comment2.setComment(COMMENT_2);
        comment2.setCanApprove(true);

        testOffer.setComments(List.of(comment1, comment2));

       return offerRepository.save(testOffer).getId();
    }



}
