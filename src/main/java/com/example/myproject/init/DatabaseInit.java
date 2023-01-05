package com.example.myproject.init;

import com.example.myproject.service.OfferService;
import com.example.myproject.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements CommandLineRunner {

    private final UserService userService;
    private final OfferService offerService;

    public DatabaseInit(UserService userService, OfferService offerService) {
        this.userService = userService;
        this.offerService = offerService;
    }

    @Override
    public void run(String... args) throws Exception {
        //userService.initializeUsersAndRoles();
      //  offerService.initializeOffers();
    }
}
