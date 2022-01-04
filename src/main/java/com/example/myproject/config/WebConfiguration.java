package com.example.myproject.config;

import com.example.myproject.web.interceptor.SessionTimerInterceptor;
import com.example.myproject.web.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new SessionTimerInterceptor());
//        registry.addInterceptor(new UserInterceptor());
//    }


}
