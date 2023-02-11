/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.feignclient;

import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.user.SaveUserRequest;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.data.user.UserUIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Feign Client for the User.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@FeignClient(name = "UserController.class", url = "${services.marketplace.api}")
public interface UserServiceFeignClient {

    /**
     * Create the user in the database.
     *
     * @param request       request with user parameters
     * @return              the user from database in response format
     */
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    UserUIResponse register(@RequestBody SaveUserRequest request);

    /**
     * Get all users from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @return              all users from database in response format
     */
    @GetMapping(value = "/admins/users", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<UserUIResponse> getAll(Pageable pageable);

    /**
     * Get the user by email from the database in response format.
     *
     * @param email         email of user
     * @return              the user from database in response format
     */
    @GetMapping(value = "/admins/users/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserUIResponse getCurrentUser(@PathVariable String email);

    /**
     * Get the user by id from the database in response format.
     *
     * @param id            id of user
     * @return              the user from database in response format
     */
    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserUIResponse getUserById(@PathVariable int id);

    /**
     * Exists user by email in the database in boolean format.
     *
     * @param email         email of user
     * @return              true - if user exists in database and false - is user does not exist in database
     */
    @GetMapping(value = "/register/{email}")
    boolean existsByEmail(@PathVariable String email);

    /**
     * Update the user in the database.
     *
     * @param id            id of goods
     * @param request       request with user parameters
     */
    @PutMapping(value = "/admins/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void update(@PathVariable int id, @RequestBody SaveUserRequest request);

    /**
     * Delete the user in the database.
     *
     * @param email         email of user
     */
    @DeleteMapping(value = "/admins/users/{email}")
    void delete(@PathVariable String email);
}
