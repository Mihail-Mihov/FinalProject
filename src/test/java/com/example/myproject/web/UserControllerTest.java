package com.example.myproject.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.myproject.model.entity.UserEntity;
import com.example.myproject.model.entity.UserRoleEntity;
import com.example.myproject.model.service.UserRegistrationServiceModel;
import com.example.myproject.model.view.ProfileDetailsView;
import com.example.myproject.model.view.ProfileHomeView;
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
class UserRegistrationControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    void delete(){
       // userRepository.deleteAll();
    }

//    @BeforeEach
//    void setUp(){
//
//      UserEntity  user = new UserEntity();
//        user.setPassword("admin");
//        user.setUsername("ceco");
//        user.setFirstName("ceco");
//        user.setLastName("radev");
//        user.setEmail("ceco@email.com");
//
//        UserEntity  user2 = new UserEntity();
//        user2.setPassword("admin");
//        user2.setUsername("vasko");
//        user2.setFirstName("vasilev");
//        user2.setLastName("vvasko");
//        user2.setEmail("vasko@email.com");
//
//        UserEntity  user3 = new UserEntity();
//        user3.setPassword("admin");
//        user3.setUsername("petq");
//        user3.setFirstName("paneva");
//        user3.setLastName("ppetya");
//        user3.setEmail("paneva@email.com");
//
//        this.userRepository.saveAll(List.of(user, user2, user3));
//
//    }

    @Test
    void testOpenRegisterForm() throws Exception {

        UserEntity first = userRepository.findAll().stream().findFirst().get();

        ProfileHomeView dto = modelMapper.map(first, ProfileHomeView.class);

        Assertions.assertEquals(first.getUsername(), dto.getUsername());

        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testShowMyProfile() throws Exception {
        UserEntity first = userRepository.findByUsername("kmkalin").get();

        ProfileDetailsView dto = modelMapper.map(first, ProfileDetailsView.class);
        dto.setCanUpdate(true);

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }

    @Test
    void testShowEditProfile() throws Exception {
        UserEntity first = userRepository.findByUsername("kmkalin").get();
        ProfileDetailsView dto = modelMapper.map(first, ProfileDetailsView.class);
        dto.setCanUpdate(true);

        mockMvc.perform(get("/profile/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProfile"));
    }


    @Test
    void testShowIndexPage() throws Exception {
        UserEntity first = userRepository.findByUsername("kmkalin").get();
        ProfileHomeView dto = modelMapper.map(first, ProfileHomeView.class);
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

    }

    private static final String FIRST_NAME = "kalin";
    private static final String LAST_NAME = "kirevski";
    private static final String PASSWORD = "admin";
    private static final String USERNAME = "kmkalin";
    private static final String EMAIL = "kalin@kalin";
    private static final String NUMBER = "08972727";
    private static final String HOMETOWN = "GABROVO";
    private static final String PROFILE_PICTURE_URL = "asdasd";
    private static final String DESCRIPTION = "asdqweasdqwe";

    @Test
    void testEditProfile() throws Exception {
        mockMvc.perform(patch("/profile/edit")
                .param("firstName", FIRST_NAME)
                .param("lastName", LAST_NAME)
                .param("profilePictureUrl", PROFILE_PICTURE_URL)
                .param("number", NUMBER)
                .param("email", EMAIL)
                .param("homeTown", HOMETOWN)
                .param("description", DESCRIPTION)
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(status().is3xxRedirection());

        Optional<UserEntity> userEntity = userRepository.findByUsername("kmkalin");

        Assertions.assertTrue(userEntity.isPresent());

        UserEntity user = userEntity.get();
        Assertions.assertEquals(FIRST_NAME, user.getFirstName());
        Assertions.assertEquals(LAST_NAME, user.getLastName());
        Assertions.assertEquals(PROFILE_PICTURE_URL, user.getProfilePictureUrl());
        Assertions.assertEquals(EMAIL, user.getEmail());
        Assertions.assertEquals(NUMBER, user.getNumber());
        Assertions.assertEquals(HOMETOWN, user.getHomeTown());
        Assertions.assertEquals(DESCRIPTION, user.getDescription());
    }

    @Test
    void testShowUserProfile() throws Exception {
        UserEntity kmkalin = userService.getUserByUsername("kmkalin");

        mockMvc.perform(get("/users/"+ kmkalin.getId() + "/details"))
                .andExpect(status().isOk())
                .andExpect(view().name("userProfile"));
    }

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

        Assertions.assertEquals(5, userRepository.count());

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