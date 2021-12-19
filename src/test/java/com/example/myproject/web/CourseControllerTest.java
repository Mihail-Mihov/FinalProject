package com.example.myproject.web;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.myproject.model.entity.CategoryEnum;
import com.example.myproject.model.entity.UserEntity;
import com.example.myproject.model.entity.UserRoleEntity;
import com.example.myproject.model.service.UserRegistrationServiceModel;
import com.example.myproject.model.view.ProfileDetailsView;
import com.example.myproject.model.view.ProfileHomeView;
import com.example.myproject.repository.OfferRepository;
import com.example.myproject.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.myproject.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser("kmkalin")
@SpringBootTest
@AutoConfigureMockMvc
 class CourseControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OfferRepository offerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

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
    void delete(){
        userRepository.deleteAll();
        offerRepository.deleteAll();
    }


    @Test
    void testAddOfferPage() throws Exception {

        mockMvc.perform(get("/courses/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addcourse"));
    }

    @Test
    void testAddOffer() throws Exception {

        mockMvc.perform(post("/courses/add")
                .param("name", "kurs")
                .param("imageUrl", "asdasd")
                .param("price", "1.55")
                .param("description", "opisanie")
                .param("category", "FRONTEND")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(status().is3xxRedirection());

        Assertions.assertEquals(0, offerRepository.count());

        Optional<UserEntity> userEntity = userRepository.findByUsername("mbmihail");

        Assertions.assertTrue(userEntity.isPresent());
//
//        UserEntity user = userEntity.get();
//        Assertions.assertEquals(FIRST_NAME, user.getFirstName());
//        Assertions.assertEquals(LAST_NAME, user.getLastName());
//        passwordEncoder.matches(PASSWORD, user.getPassword());
//        Assertions.assertEquals(USERNAME, user.getUsername());
//        Assertions.assertEquals(EMAIL, user.getEmail());
//        Assertions.assertEquals(NUMBER, user.getNumber());
//        Assertions.assertEquals(HOMETOWN, user.getHomeTown());

    }


}
