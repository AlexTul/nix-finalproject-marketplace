/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.config.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * MVC configuration.
 * @version 01
 *
 * @author Oleksandr Tuleninov
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.pathPatterns}")
    private String pathPatterns;

    // Serve static resources  section
    /**
     * Configure ResourceHttpRequestHandlers for serving static resources from the classpath,
     * the WAR, or the file system.
     *
     * @param registry      ResourceHandlerRegistry to configure ResourceHttpRequestHandlers for serving
     *                      static resources from the classpath, the WAR, or the file system
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(pathPatterns)
                .addResourceLocations("file:" + uploadPath + "/");
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations("file:./images");
    }

    // Internationalization section
    /**
     * Allows for both locale resolution via the request and locale modification via request and response.
     *
     * @return          CookieLocaleResolver with customize locale
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(new Locale("en"));
        cookieLocaleResolver.setCookieName("language");

        return cookieLocaleResolver;
    }

    /**
     * Change the current locale.
     *
     * @param registry      LocaleChangeInterceptor - interceptor that allows for changing the current
     *                      locale on every request
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * Change the current locale on every request, via a configurable request parameter
     * (default parameter name: "locale").
     *
     * @return              LocaleChangeInterceptor - interceptor that allows for changing the current locale
     *                      on every request
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        return new LocaleChangeInterceptor();
    }
}
