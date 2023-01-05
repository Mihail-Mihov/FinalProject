package com.example.myproject.service;

import com.example.myproject.model.binding.OfferAddBindModel;
import com.example.myproject.model.entity.OfferEntity;
import com.example.myproject.model.service.OfferAddServiceModel;
import com.example.myproject.model.service.OfferUpdateServiceModel;
import com.example.myproject.model.view.OfferDetailsView;

import java.util.List;

public interface OfferService {

    // void initializeOffers();

    List<OfferDetailsView> getAllOffers(String currentUser);

    List<OfferDetailsView> getByKeyword(String keyword);

    OfferDetailsView findOfferById(Long id, String currentUser);

    void deleteOffer(Long id);

    boolean hasPrivileges(String userName, Long id);

    void updateOffer(OfferUpdateServiceModel offerModel);

    OfferAddServiceModel addOffer(OfferAddBindModel offerAddBindModel, String ownerId);

    List<OfferEntity> getAllByAuthor(Long authorId);
}
