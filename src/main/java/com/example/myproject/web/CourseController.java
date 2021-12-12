package com.example.myproject.web;

import com.example.myproject.model.binding.OfferAddBindModel;
import com.example.myproject.model.binding.OfferUpdateBindModel;
import com.example.myproject.model.service.OfferAddServiceModel;
import com.example.myproject.model.service.OfferUpdateServiceModel;
import com.example.myproject.model.view.OfferDetailsView;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.service.OfferService;
import com.example.myproject.service.UserService;
import com.example.myproject.service.impl.MyUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CourseController {

    private final OfferService offerService;
    private final ModelMapper modelMapper;
    private final UserService userService;


    public CourseController(OfferService offerService, ModelMapper modelMapper, UserRepository userRepository, UserService userService) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/")
    public String indexpage (){
        return "index";
    }

    @GetMapping("/courses/add")
    public String getAddOfferPage(Model model) {

        if (!model.containsAttribute("offerAddBindModel")) {
            model.
                    addAttribute("offerAddBindModel", new OfferAddBindModel());
        }
        return "addcourse";
    }

    @PostMapping("/courses/add")
    public String addOffer (@Valid OfferAddBindModel offerAddBindModel,
                            BindingResult bindingResult,  RedirectAttributes redirectAttributes,
                            @AuthenticationPrincipal MyUser user){



        if(bindingResult.hasErrors()){
            redirectAttributes
                    .addFlashAttribute("offerAddBindModel", offerAddBindModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.offerAddBindModel", bindingResult);
            return "redirect:/courses/add";
        }

        OfferAddServiceModel savedOfferAddServiceModel  = offerService.addOffer(offerAddBindModel, user.getUserIdentifier());
        return "redirect:/courses/all";
       // return "redirect:/courses/" + savedOfferAddServiceModel.getId() + "/details";
    }





    @GetMapping("/courses/{id}/edit")
    public String editOffer(@PathVariable Long id, Model model,
                            @AuthenticationPrincipal User currentUser) {

        OfferDetailsView offerDetailsView = offerService.findById(id, currentUser.getUsername());
        OfferUpdateBindModel offerModel = modelMapper.map(
                offerDetailsView,
                OfferUpdateBindModel.class
        );
//        model.addAttribute("engines", EngineEnum.values());
//        model.addAttribute("transmissions", TransmissionEnum.values());
        model.addAttribute("offerModel", offerModel);
        return "edit";
    }

    @PatchMapping("/courses/{id}/edit")
    public String patchOffer(
            @PathVariable Long id,
            @Valid OfferUpdateBindModel offerModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        System.out.println(offerModel);


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


//    @GetMapping("/offers/{id}/edit/errors")
//    public String editOfferErrors(@PathVariable Long id, Model model) {
////        model.addAttribute("engines", EngineEnum.values());
////        model.addAttribute("transmissions", TransmissionEnum.values());
//        return "edit";
   // }

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

        model.addAttribute("offer", offerService.findById(id, user.getUsername()));
        return "course-details";
    }

    @GetMapping("/courses/all")
    public String allOffers(Model model){
        model.addAttribute("courses", offerService.getAllOffers());
        return "allcourses";
    }

//    @RequestMapping("/courses/all")
//    public String allOffers( Model model, String keyword) {
//        if(keyword!=null) {
//            List<OfferDetailsView> offers = offerService.getByKeyword(keyword);
//            model.addAttribute("courses", offers);
//        }else {
//            List<OfferDetailsView> offers = offerService.getAllOffers();
//            model.addAttribute("courses", offers);}
//        return "allcourses";
//    }



}
