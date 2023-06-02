package com.tuleninov.web.config.mvc;

import com.tuleninov.web.config.mvc.file.FileProperties;
import com.tuleninov.web.config.mvc.mail.MailProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;
import java.util.Properties;

/**
 * MVC configuration.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final FileProperties fileProperties;
    private final MailProperties mailProperties;

    public MvcConfig(FileProperties fileProperties, MailProperties mailProperties) {
        this.fileProperties = fileProperties;
        this.mailProperties = mailProperties;
    }

    //region server static resources

    /**
     * Configure ResourceHttpRequestHandlers for serving static resources from the classpath,
     * the WAR, or the file system.
     *
     * @param registry ResourceHandlerRegistry to configure ResourceHttpRequestHandlers for serving
     *                 static resources from the classpath, the WAR, or the file system
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(fileProperties.getPatterns())
                .addResourceLocations("file:" + fileProperties.getPath() + "/");
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations("file:./images");
    }

    //endregion

    //region internationalization

    /**
     * Allows for both locale resolution via the request and locale modification via request and response.
     *
     * @return CookieLocaleResolver with customize locale
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
     * @param registry LocaleChangeInterceptor - interceptor that allows for changing the current
     *                 locale on every request
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * Change the current locale on every request, via a configurable request parameter
     * (default parameter name: "locale").
     *
     * @return LocaleChangeInterceptor - interceptor that allows for changing the current locale
     * on every request
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        return new LocaleChangeInterceptor();
    }

    /**
     * Create Bean for changing locale in different Http request.
     * (For using LocaleContextHolder.getLocale() at any programmatic moment).
     */
    @Bean
    public FilterRegistrationBean<RequestContextFilter> requestContextFilterRegistrationBean() {
        FilterRegistrationBean<RequestContextFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestContextFilter());
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    //endregion

    //region mail

    /**
     * Local override of all values.
     *
     * @return JavaMailSenderImpl - production implementation of the JavaMailSender interface.
     * Allows for defining all settings locally as bean properties.
     */
    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());

        Properties properties = mailSender.getJavaMailProperties();

        properties.setProperty("mail.transport.protocol", mailProperties.getProtocol());

        return mailSender;
    }

    //endregion

    //region recaptcha

    /**
     * Synchronous client to perform HTTP requests.
     * */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    //endregion
}
