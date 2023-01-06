package com.example.myproject.web;

import com.example.myproject.model.binding.OfferAddBindModel;
import com.example.myproject.model.binding.OfferUpdateBindModel;
import com.example.myproject.model.entity.UserEntity;
import com.example.myproject.model.service.OfferAddServiceModel;
import com.example.myproject.model.service.OfferUpdateServiceModel;
import com.example.myproject.model.view.OfferDetailsView;
import com.example.myproject.repository.OfferRepository;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.service.OfferService;
import com.example.myproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class CourseController {

    private final OfferService offerService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final OfferRepository offerRepository;


    public CourseController(OfferService offerService, ModelMapper modelMapper, UserRepository userRepository, UserService userService, OfferRepository offerRepository) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.offerRepository = offerRepository;
    }

    @GetMapping("/courses/add")
    public String getAddOfferPage(Model model) {

        if (!model.containsAttribute("offerAddBindModel")) {
            model.addAttribute("offerAddBindModel", new OfferAddBindModel());
        }
        return "addcourse";
    }

    @PostMapping("/courses/add")
    public String addOffer (@Valid OfferAddBindModel offerAddBindModel,
                            BindingResult bindingResult,  RedirectAttributes redirectAttributes,
                            @AuthenticationPrincipal User user){



        if(bindingResult.hasErrors()){
            redirectAttributes
                    .addFlashAttribute("offerAddBindModel", offerAddBindModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.offerAddBindModel", bindingResult);
            return "redirect:/courses/add";
        }

        System.out.println(user);
        OfferAddServiceModel savedOfferAddServiceModel  = offerService.addOffer(offerAddBindModel, user.getUsername());
        return "redirect:/courses/all";
       // return "redirect:/courses/" + savedOfferAddServiceModel.getId() + "/details";
    }

    @GetMapping("/courses/{id}/edit")
    public String editOffer(@PathVariable Long id, Model model,
                            @AuthenticationPrincipal User currentUser) {

        OfferDetailsView offerDetailsView = offerService.findOfferById(id, currentUser.getUsername());
        OfferUpdateBindModel offerModel = modelMapper.map(
                offerDetailsView,
                OfferUpdateBindModel.class
        );
        model.addAttribute("offerModel", offerModel);
        return "edit";
    }

    @PatchMapping("/courses/{id}/edit")
    public String patchOffer(
            @PathVariable Long id,
            @Valid OfferUpdateBindModel offerModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("offerModel", offerModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerModel", bindingResult);

            return "redirect:/courses/" + id + "/edit";

        }

        OfferUpdateServiceModel serviceModel = modelMapper.map(offerModel,
                OfferUpdateServiceModel.class);
        serviceModel.setId(id);

        offerService.updateOffer(serviceModel);

        return "redirect:/courses/" + id + "/details";
    }


     @PreAuthorize("isOwner(#id)")
    @DeleteMapping("/courses/{id}")
    public String deleteOffer(@PathVariable Long id,
                              @AuthenticationPrincipal User user){

//        if (!offerService.isOwner(user.getUserIdentifier(), id)) {
//
//        }
        offerService.deleteOffer(id);
        return "redirect:/courses/all";
    }

    @GetMapping("/courses/{id}/details")
    public String showOffer(@PathVariable Long id, Model model,
                            @AuthenticationPrincipal User user){
        UserEntity author = offerRepository.findById(id).get().getAuthor();
        String authorAddress = author.getHomeTown();
        int commentCounter =  offerRepository.findById(id).get().getComments().size();

        model.addAttribute("author", author);
        model.addAttribute("offer", offerService.findOfferById(id, user.getUsername()));
        model.addAttribute("commentCounter", commentCounter);
        model.addAttribute("authorAddress", authorAddress);
        return "course-details";
    }

    @GetMapping("/courses/all")
    public String allOffers(Model model, String username){
        model.addAttribute("courses", offerService.getAllOffers(null));
        return "allcourses";
    }



}
