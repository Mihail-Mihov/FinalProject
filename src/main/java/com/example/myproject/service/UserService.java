package com.example.myproject.service;

import com.example.myproject.model.binding.ProfileUpdateBindingModel;
import com.example.myproject.model.entity.UserEntity;
import com.example.myproject.model.service.UserRegistrationServiceModel;
import com.example.myproject.model.view.ProfileDetailsView;
import com.example.myproject.model.view.ProfileHomeView;

import java.util.List;
import java.util.Set;

public interface UserService {

    void initializeUsersAndRoles();

    void registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel);

    boolean isUserNameFree(String username);

     UserEntity getUserByUsername(String username);

     void updateProfile(ProfileUpdateBindingModel profile);

     Set<ProfileHomeView> getAllUsers();

     UserEntity findById(Long id);

}
