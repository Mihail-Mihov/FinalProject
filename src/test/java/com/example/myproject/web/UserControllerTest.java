package com.example.myproject.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.myproject.model.entity.UserEntity;
import com.example.myproject.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserRegistrationControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    void delete(){
        userRepository.deleteAll();
    }

    @Test
    void testOpenRegisterForm() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    private static final String FIRST_NAME = "kalin";
    private static final String LAST_NAME = "kirevski";
    private static final String PASSWORD = "admin";
    private static final String USERNAME = "kmkalin";
    private static final String EMAIL = "kalin@kalin";
    private static final String NUMBER = "08972727";
    private static final String HOMETOWN = "GABROVO";

    @Test
    void testRegisterUser() throws Exception {

        mockMvc.perform(post("/users/register")
                .param("firstName", FIRST_NAME)
                .param("lastName", LAST_NAME)
                .param("password", PASSWORD)
                .param("confirmPassword", PASSWORD)
                .param("username", USERNAME)
                .param("email", EMAIL)
                .param("number", NUMBER)
                .param("homeTown", HOMETOWN)

                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, userRepository.count());

        Optional<UserEntity> userEntity = userRepository.findByUsername("kmkalin");

        Assertions.assertTrue(userEntity.isPresent());

        UserEntity user = userEntity.get();
        Assertions.assertEquals(FIRST_NAME, user.getFirstName());
        Assertions.assertEquals(LAST_NAME, user.getLastName());
        passwordEncoder.matches(PASSWORD, user.getPassword());
        Assertions.assertEquals(USERNAME, user.getUsername());
        Assertions.assertEquals(EMAIL, user.getEmail());
        Assertions.assertEquals(NUMBER, user.getNumber());
        Assertions.assertEquals(HOMETOWN, user.getHomeTown());

    }



}