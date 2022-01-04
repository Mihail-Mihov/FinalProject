package com.example.myproject.web;

import com.example.myproject.model.binding.ProfileUpdateBindingModel;
import com.example.myproject.model.entity.OfferEntity;
import com.example.myproject.model.entity.UserEntity;
import com.example.myproject.model.view.ProfileDetailsView;
import com.example.myproject.model.view.ProfileHomeView;
import com.example.myproject.service.OfferService;
import com.example.myproject.service.UserService;
import com.example.myproject.service.impl.MyUser;
import com.example.myproject.service.impl.MyUserDetailsServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private final OfferService offerService;
    private final ModelMapper modelMapper;
    private final MyUserDetailsServiceImpl myUserDetailsService;
    private final UserService userService;

    public UserController(OfferService offerService, ModelMapper modelMapper, MyUserDetailsServiceImpl myUserDetailsService, UserService userService) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;

        this.myUserDetailsService = myUserDetailsService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String showProfileHome( Model model) {

        List<ProfileHomeView> allUsers = this.userService.getAllUsers();

        model.addAttribute("allUsers", allUsers);

        return "index";
    }

    @GetMapping("/profile")
    public String showMyProfile( Model model,
                              @AuthenticationPrincipal User currentUser) {

        UserEntity userEntity = userService.getUserByUsername(currentUser.getUsername());

        ProfileDetailsView profile = modelMapper.map(userEntity, ProfileDetailsView.class);
        profile.setCanUpdate(true);

        List<OfferEntity> allByAuthor = offerService.getAllByAuthor(userEntity.getUsername());

        model.addAttribute("userEntity", profile);
        model.addAttribute("offersDetails", allByAuthor);
        return "profile";
    }


    @PatchMapping("/profile/edit")
    public String editProfile(
            @Valid ProfileUpdateBindingModel profileModel,
            @AuthenticationPrincipal User currentUser,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("profileModel", profileModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.profileModel", bindingResult);

            return "redirect:/editProfile";
        }
        ProfileUpdateBindingModel profileUpdateBindingModel = modelMapper.map(profileModel, ProfileUpdateBindingModel.class);
        profileUpdateBindingModel.setId(userService.getUserByUsername(currentUser.getUsername()).getId());

        userService.updateProfile(profileUpdateBindingModel);
        return "redirect:/profile";
    }


    @GetMapping("/profile/edit")
    public String editProfile( Model model,
                            @AuthenticationPrincipal User currentUser) {

        UserEntity userEntity = userService.getUserByUsername(currentUser.getUsername());

        ProfileDetailsView profile = modelMapper.map(userEntity, ProfileDetailsView.class);

        ProfileUpdateBindingModel profileModel = modelMapper.map(profile, ProfileUpdateBindingModel.class);
        profileModel.setCanUpdate(true);

        model.addAttribute("profileModel", profile);
        return "editProfile";
    }




  @GetMapping("/users/{id}/details")
  public String showProfile(@PathVariable Long id, Model model) {

      UserEntity byId = userService.findById(id);
      List<OfferEntity> allByAuthor = offerService.getAllByAuthor(byId.getUsername());
      ProfileDetailsView profileDetailsView =  modelMapper.map(byId, ProfileDetailsView.class);
      if (profileDetailsView.getProfilePictureUrl() == null) {
          profileDetailsView.setProfilePictureUrl("https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg");
      }

      model.addAttribute("userDetails", profileDetailsView);
      model.addAttribute("offersDetails", allByAuthor);
      return "userProfile";
  }

}

