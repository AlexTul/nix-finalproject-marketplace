/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.config.security;

import com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.user.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Override some of Spring's built-in security protocols to use our database and hashing algorithm.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService customUserDetailService;

    public SecurityConfig(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .authorizeRequests()
                    .antMatchers("/", "/categories/**", "/register", "/activate/*", "/view/**", "/cart/**", "/order/**").permitAll()
                    .antMatchers("/files/**", "/static/**", "/images/**").permitAll()
                    .antMatchers("/admins/**").hasRole("ADMIN")
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .failureUrl("/login?error= true")
                    .defaultSuccessUrl("/")
                    .usernameParameter("email")
                    .passwordParameter("password")
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                .and()
                    .exceptionHandling();
        http.headers().frameOptions().disable();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Add authentication based upon the custom UserDetailsService that is passed in.
     * It then returns a DaoAuthenticationConfigurer to allow customization of the authentication.
     *
     * @param auth          SecurityBuilder - used to create an AuthenticationManager
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService);
    }
}
