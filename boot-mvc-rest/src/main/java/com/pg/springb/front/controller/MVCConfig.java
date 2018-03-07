package com.pg.springb.front.controller;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.UrlFilenameViewController;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class MVCConfig {
     
    @Bean
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
     
    @Bean(name = "urlViewController")
    public UrlFilenameViewController getUrlViewController() {
        UrlFilenameViewController urlViewController = new UrlFilenameViewController();
        return urlViewController;
    }
     
    @Bean
    public SimpleUrlHandlerMapping getUrlHandlerMapping() {
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        Properties mappings = new Properties();
        mappings.put("/frontier/**", "urlViewController");
         
        handlerMapping.setMappings(mappings);
        return handlerMapping;
    }
}