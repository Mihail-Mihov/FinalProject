package com.example.myproject.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AppConfig {

    private final CloudinaryConfig config;

    public AppConfig(CloudinaryConfig config) {
        this.config = config;
    }
//TODO
    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(Map.of(
                "cloud_name", "pib",
                "api_key", "633745713139312",
                "api_secret", "TfjJfvwe2VOVNm-7o9ZbiVK67x0"
        ));
    }
}
