package com.example.myproject.service.impl;

import com.example.myproject.model.binding.ProfileUpdateBindingModel;
import com.example.myproject.model.entity.UserEntity;
import com.example.myproject.model.entity.UserRoleEntity;
import com.example.myproject.model.entity.UserRoleEnum;
import com.example.myproject.model.service.UserRegistrationServiceModel;
import com.example.myproject.model.view.ProfileHomeView;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.repository.UserRoleRepository;
import com.example.myproject.service.UserService;
import com.example.myproject.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final MyUserDetailsServiceImpl myUserDetailsService;
    private final ModelMapper modelMapper;

    public UserServiceImpl( PasswordEncoder passwordEncoder,UserRepository userRepository,
                           UserRoleRepository userRoleRepository, MyUserDetailsServiceImpl myUserDetailsService, ModelMapper modelMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.myUserDetailsService = myUserDetailsService;
        this.modelMapper = modelMapper;
    }



    @Override
    public void initializeUsersAndRoles() {
        initializeRoles();
        //initializeUsers();

    }

    @Override
    public List<ProfileHomeView> getAllUsers() {

        Random random = new Random();
        List<ProfileHomeView> list = new ArrayList<>();

        if (userRepository.count() >= 3) {
            for (int i=0; i<3; i++){
                Long id = random.nextLong(1,this.userRepository.count());
                UserEntity byId = this.userRepository.findById(id).get();
                ProfileHomeView profileHomeView = mapToHomeView(byId);
                list.add(profileHomeView);
            }
        }
        return list;

    }

    @Override
    public UserEntity findById(Long id) {
        return this.userRepository.findById(id).get();
    }

    private ProfileHomeView mapToHomeView(UserEntity user){
        ProfileHomeView profileHomeView = modelMapper.map(user, ProfileHomeView.class);

        profileHomeView.setUsername(user.getUsername());
        profileHomeView.setId(user.getId());
        if (user.getProfilePictureUrl() == null) {
            profileHomeView.setProfilePictureUrl("https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg");
        } else {
        profileHomeView.setProfilePictureUrl(user.getProfilePictureUrl());
        }
        if (user.getDescription() == null){
            profileHomeView.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                    " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer");
        } else {
            if (user.getDescription().length() > 200) {
                profileHomeView.setDescription( user.getDescription().substring(0, 200));
            }
            else {
                profileHomeView.setDescription(user.getDescription());
            }
        }

        return profileHomeView;
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
        user.setProfilePictureUrl("https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg");
        user.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry.");
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
