package com.example.sweater.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**") // каждое обращение к серверу по пути "/img/и тут что угодно" будет перенаправлять все запросы
                .addResourceLocations("file://" + uploadPath + "/"); // по этому пути (file:// здесь это протокол файл - место в файловой системе)
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/"); // "classpath:/" - а это означает, что ресурсы будут искаться
                                                            // не где-то в файловой системе, а в дереве проекта
    }
}
