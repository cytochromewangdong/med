package com.students.mum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMVCConfig implements WebMvcConfigurer {


    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addFormatter(roleFormatter);
    }
}

