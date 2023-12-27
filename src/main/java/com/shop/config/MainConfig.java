package com.shop.config;

import com.shop.converter.ImageFileConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAspectJAutoProxy
public class MainConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {registry.addConverter(new ImageFileConverter());}

    @Bean
    public RestOperations restOperations(){
        return new RestTemplate();
    }
}
