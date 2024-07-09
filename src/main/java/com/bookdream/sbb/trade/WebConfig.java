package com.bookdream.sbb.trade;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/trade/images/**")
                .addResourceLocations("file:C:/Users/TJ/git/Book-Dream/src/main/resources/templates/trade/images/");
    }
}
