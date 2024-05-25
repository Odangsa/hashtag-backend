package com.odangsa.hashtag.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://dz66hkk67ryjf.cloudfront.net")
                .allowedMethods(HttpMethod.POST.name(), HttpMethod.GET.name())
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
