package com.example.myproject.service.impl;

import com.example.myproject.model.binding.ProfileUpdateBindingModel;
import com.example.myproject.model.entity.UserEntity;
import com.example.myproject.model.entity.UserRoleEntity;
import com.example.myproject.model.entity.UserRoleEnum;
import com.example.myproject.model.service.UserRegistrationServiceModel;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.repository.UserRoleRepository;
import com.example.myproject.service.UserService;
import com.example.myproject.web.exception.ObjectNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final MyUserDetailsServiceImpl myUserDetailsService;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
                           UserRoleRepository userRoleRepository, MyUserDetailsServiceImpl myUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    public void initializeUsersAndRoles() {
        initializeRoles();
        //initializeUsers();

    }

    public UserEntity getUserByUsername(String username){
       return userRepository.findByUsername(username).orElseThrow();
    }

    private void initializeRoles() {

        if (userRoleRepository.count() ==0){
            UserRoleEntity adminRole =new UserRoleEntity();
            adminRole.setRole(UserRoleEnum.ADMIN);

            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setRole(UserRoleEnum.USER);

            UserRoleEntity moderatorRole = new UserRoleEntity();
            moderatorRole.setRole(UserRoleEnum.MODERATOR);

            userRoleRepository.save(adminRole);
            userRoleRepository.save(userRole);
            userRoleRepository.save(moderatorRole);
        }
    }

    @Override
    public void updateProfile(ProfileUpdateBindingModel profile) {
        UserEntity userEntity =  userRepository.findById(profile.getId()).orElseThrow(() ->
                new ObjectNotFoundException("There is no profile with id: " + profile.getId()));
        userEntity.setId(profile.getId());
        userEntity.setEmail(profile.getEmail());
        userEntity.setFirstName(profile.getFirstName());
        userEntity.setLastName(profile.getLastName());
        userEntity.setProfilePictureUrl(profile.getProfilePictureUrl());
        userEntity.setNumber(profile.getNumber());
        userEntity.setHomeTown(profile.getHomeTown());
        userEntity.setDescription(profile.getDescription());
        userRepository.save(userEntity);
    }

    //    private void initializeUsers(){
//            if (userRepository.count() == 0){
//                UserRoleEntity adminRole = userRoleRepository.findByRole(UserRoleEnum.ADMIN);
//                UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER);
//
//                UserEntity admin = new UserEntity();
//                admin.setUsername("admin");
//                admin.setPassword(passwordEncoder.encode("password"));
//                admin.setFirstName("Kalin");
//                admin.setLastName("Kirevski");
//                admin.setActive(true);
//                admin.setRoles(Set.of(adminRole, userRole));
//                admin.setEmail("email");
//
//                userRepository.save(admin);
//
//                UserEntity pesho = new UserEntity();
//                pesho.setUsername("random");
//                pesho.setPassword(passwordEncoder.encode("password"));
//                pesho.setFirstName("Valentin");
//                pesho.setLastName("Simeonov");
//                pesho.setActive(true);
//                pesho.setRoles(Set.of(userRole));
//                pesho.setEmail("peshovemail");
//
//                userRepository.save(pesho);
//            }
//        }
//



    @Override
    public void registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel) {

         UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER);

        UserEntity user = new UserEntity();


        user.setUsername(userRegistrationServiceModel.getUsername());
        user.setEmail(userRegistrationServiceModel.getEmail());
        user.setActive(true);
        user.setNumber(userRegistrationServiceModel.getNumber());
        user.setHomeTown(userRegistrationServiceModel.getHomeTown());
        user.setFirstName(userRegistrationServiceModel.getFirstName());
        user.setLastName(userRegistrationServiceModel.getLastName());
        user.setPassword(passwordEncoder.encode(userRegistrationServiceModel.getPassword()));
        if (user.getUsername().equals("admin")) {
            user.setRoles(Set.of(userRoleRepository.findByRole(UserRoleEnum.ADMIN)));
        } else {
        user.setRoles(Set.of(userRole));
        }

        userRepository.save(user);

        // this is the Spring representation of a user

        UserDetails principal =  myUserDetailsService.loadUserByUsername(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            principal,
                user.getPassword()
                ,principal.getAuthorities()
        );

        SecurityContextHolder
                .getContext().setAuthentication(authentication);

    }

    @Override
    public boolean isUserNameFree(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }
}
