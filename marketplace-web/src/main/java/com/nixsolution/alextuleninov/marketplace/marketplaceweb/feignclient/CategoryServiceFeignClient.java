/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.feignclient;

import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.category.SaveCategoryRequest;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.data.category.CategoryUIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Feign Client for the Category.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@FeignClient(name ="CategoryController.class", url = "${services.marketplace.api}")
public interface CategoryServiceFeignClient {

    /**
     * Create the category in the database.
     *
     * @param request       request with category parameters
     */
    @PostMapping(value = "/admins/categories", consumes = MediaType.APPLICATION_JSON_VALUE)
    void create(@Valid @RequestBody SaveCategoryRequest request);

    /**
     * Get all categories from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @return              all categories from database in response format
     */
    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<CategoryUIResponse> getAll(Pageable pageable);

    /**
     * Get all categories from database in response format.
     *
     * @return              all categories from database in response format
     */
    @GetMapping(value = "/categories/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CategoryUIResponse> getAllList();

    /**
     * Get the category by id from the database in response format.
     *
     * @param id            id of category
     * @return              the category from database in response format
     */
    @GetMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    CategoryUIResponse getById(@PathVariable int id);

    /**
     * Update the category in the database.
     *
     * @param id            id of category
     * @param request       request with category parameters
     */
    @PutMapping(value = "/admins/categories/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void update(@PathVariable int id, @Valid @RequestBody SaveCategoryRequest request);

    /**
     * Delete the category in the database.
     *
     * @param id            id of category
     */
    @DeleteMapping(value = "/admins/categories/{id}")
    void delete(@PathVariable int id);
}
