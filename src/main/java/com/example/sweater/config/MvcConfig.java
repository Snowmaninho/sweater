package com.example.sweater.config;

import com.example.sweater.util.RedirectInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) { // регистрируем перехватчиков, добавка для Turbolinks
        registry.addInterceptor(new RedirectInterceptor()); // добавляем наш, соззданный ранее перехватчик
    }
}
