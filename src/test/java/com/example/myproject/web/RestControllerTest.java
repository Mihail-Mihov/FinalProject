package com.example.myproject.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.example.myproject.model.binding.NewCommentBindModel;
import com.example.myproject.model.entity.CommentEntity;
import com.example.myproject.model.entity.OfferEntity;
import com.example.myproject.model.entity.UserEntity;
import com.example.myproject.repository.OfferRepository;
import com.example.myproject.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

@WithMockUser("mbmihail")
@SpringBootTest
@AutoConfigureMockMvc
 class RestControllerTest {

    private static final String COMMENT_1 = "comment1 asasasasasasas";
    private static final String COMMENT_2 = "comment2 asasasasasasas";
    int temp = 44;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferRepository offerRepository;
//
//    @Autowired
//    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity user;

    @BeforeEach
    void setUp(){
        user = new UserEntity();
        user.setPassword("password");
        user.setUsername("mbmihail");
        user.setFirstName("qweasd");
        user.setLastName("qweasd");
        user.setEmail("dfgsdfg@email.com");

       user = userRepository.save(user);

    }

    @AfterEach
    void deleteAll(){
        offerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testGetComments() throws Exception {
    var offerId = initComments(initOffer());

    mockMvc.perform(get("/api/" +offerId.getId() +"/comments")).
            andExpect(status().isOk()).
            andExpect(jsonPath("$", hasSize(1))).
            andExpect(jsonPath("$.[0].comment", is (COMMENT_1)));
           // andExpect(jsonPath("$.[1].comment", is(COMMENT_2)));
    }

    @Test
    void testCreateComment() throws Exception {
         NewCommentBindModel testcomment = new NewCommentBindModel();
         testcomment.setComment(COMMENT_1);

         var emptyOffer = initOffer();

         mockMvc.perform(
                 post("/api/"  +emptyOffer.getId() + "/comments")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(testcomment))
                         .accept(MediaType.APPLICATION_JSON)
                         .with(csrf())
         )
                 .andExpect(status().isCreated())
                 .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                // .andExpect(header().string("Location", MatchesPattern.matchesPattern("/api/" + emptyOffer.getId() + "/comment/" + offerRepository.getById(emptyOffer.getId()).getComments().size())))
                 .andExpect(jsonPath("$.comment").value(is(COMMENT_1)));
    }

    private OfferEntity initOffer(){
        OfferEntity testOffer = new OfferEntity();
        testOffer.setAuthor(user);
        testOffer.setName("name");
        testOffer.setDescription("description");
        testOffer.setPrice(55.21);

        return offerRepository.save(testOffer);
    }

    private OfferEntity initComments(OfferEntity offer){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formatDateTime = now.format(formatter);

        CommentEntity comment1 = new CommentEntity();
        comment1.setCreated(formatDateTime);
        comment1.setAuthor(user);
        comment1.setComment(COMMENT_1);
        comment1.setCanApprove(true);
        comment1.setOffer(offer);

        CommentEntity comment2 = new CommentEntity();
        comment2.setCreated(formatDateTime);
        comment2.setAuthor(user);
        comment2.setComment(COMMENT_2);
        comment2.setCanApprove(true);
        comment1.setOffer(offer);

        offer.setComments(List.of(comment1, comment2));

       return offerRepository.save(offer);
    }



}
