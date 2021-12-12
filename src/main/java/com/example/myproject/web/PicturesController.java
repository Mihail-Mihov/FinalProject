//package com.example.myproject.web;
//
//import com.example.myproject.model.binding.PictureBindingModel;
//import com.example.myproject.model.entity.PictureEntity;
//import com.example.myproject.model.view.PictureViewModel;
//import com.example.myproject.repository.PictureRepository;
//import com.example.myproject.service.CloudinaryImage;
//import com.example.myproject.service.CloudinaryService;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.transaction.Transactional;
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Controller
//public class PicturesController {
//
//    private final CloudinaryService cloudinaryService;
//    private final PictureRepository pictureRepository;
//
//
//    public PicturesController(CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
//        // TODO do not use repos directly in the controller
//        this.cloudinaryService = cloudinaryService;
//        this.pictureRepository = pictureRepository;
//    }
//
//    @GetMapping("/pictures/add")
//    public String addPictures(){
//        //TODO
//        return "addcourse";
//    }
//
//    @PostMapping("/pictures/add")
//    public String addPicture(PictureBindingModel bindingModel) throws IOException {
//
//        var picture = createPictureEntity(bindingModel.getPicture(), bindingModel.getTitle());
//
//        pictureRepository.save(picture);
//
//        //TODO
//        return "redirect:/";
//    }
//
//    private PictureEntity createPictureEntity(MultipartFile file, String title) throws IOException {
//        final CloudinaryImage uploader = this.cloudinaryService.upload(file);
//
//        PictureEntity pictureEntity = new PictureEntity();
//        pictureEntity.setPublicId(uploader.getPublicId());
//        pictureEntity.setUrl(uploader.getUrl());
//        return pictureEntity;
//    }
//
//    @Transactional
//    @DeleteMapping("/pictures/delete")
//    public String delete(@RequestParam("public_id") String publicId){
//        if (cloudinaryService.delete(publicId)){
//            pictureRepository.deleteAllByPublicId(publicId);
//        }
//        return "redirect:/pictures/all";
//    }
//
//    public String all(Model model){
//        List<PictureViewModel> pictures = pictureRepository
//                .findAll().stream().map(p -> asViewModel(p))
//                .collect(Collectors.toList());
//        model.addAttribute("pictures", pictures);
//        return "all";
//    }
//
//    private PictureViewModel asViewModel(PictureEntity pictureEntity){
//         PictureViewModel pictureViewModel = new PictureViewModel();
//         pictureViewModel.setPublicId(pictureEntity.getPublicId());
//         pictureViewModel.setUrl(pictureEntity.getUrl());
//         return pictureViewModel;
//    }
//
//
//}
