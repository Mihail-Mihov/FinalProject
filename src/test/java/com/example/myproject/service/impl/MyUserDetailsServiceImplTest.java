package com.example.myproject.service.impl;

import com.example.myproject.model.entity.UserEntity;
import com.example.myproject.model.entity.UserRoleEntity;
import com.example.myproject.model.entity.UserRoleEnum;
import com.example.myproject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
 class MyUserDetailsServiceImplTest {

    private UserEntity testUser;
    private UserRoleEntity adminRole, userRole;

    private MyUserDetailsServiceImpl serviceToTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void init(){

        // Arrange
        serviceToTest = new MyUserDetailsServiceImpl(mockUserRepository);

        adminRole = new UserRoleEntity();
        adminRole.setRole(UserRoleEnum.ADMIN);

        userRole = new UserRoleEntity();
        userRole.setRole(UserRoleEnum.USER);

        testUser = new UserEntity();
        testUser.setUsername("testName");
        testUser.setEmail("test@test.com");
        testUser.setRoles(Set.of(adminRole, userRole));
        testUser.setPassword("password");
    }

    @Test
    void testUserNotFound(){
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("invalidUserName")
        );

    }

    @Test
    void testUserFound(){

        //Arrange
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).
                thenReturn(Optional.of(testUser));
        // act
        var actual =  serviceToTest.loadUserByUsername(testUser.getUsername());
        //assert
        Assertions.assertEquals(actual.getUsername(), testUser.getUsername());
        String actualRoles = actual.getAuthorities().stream().map(GrantedAuthority::getAuthority).
                collect(Collectors.joining(", "));
        String expectedRoles = "ROLE_ADMIN, ROLE_USER";
        Assertions.assertEquals(expectedRoles, actualRoles);
    }


}
