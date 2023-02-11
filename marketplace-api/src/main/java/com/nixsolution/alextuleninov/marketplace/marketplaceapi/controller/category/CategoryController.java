/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.controller.category;

import com.nixsolution.alextuleninov.marketplace.marketplaceapi.service.category.CategoryCRUD;
import com.nixsolution.alextuleninov.marketplace.marketplaceapi.data.category.CategoryResponse;
import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.category.SaveCategoryRequest;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static com.nixsolution.alextuleninov.marketplace.marketplaceapi.exception.category.CategoryExceptions.categoryNotFound;

/**
 * Rest controller for the Category.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@RestController
public class CategoryController {

    private final CategoryCRUD categoryCRUD;

    public CategoryController(CategoryCRUD categoryCRUD) {
        this.categoryCRUD = categoryCRUD;
    }

    /**
     * Create the category in the database.
     *
     * @param request       request with category parameters
     * @param ucb           builder for UriComponents
     * @return              the category from database in response format
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/admins/categories", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody SaveCategoryRequest request, UriComponentsBuilder ucb) {
        CategoryResponse response = categoryCRUD.create(request);
        return ResponseEntity
                .created(ucb.path("/categories/{id}").build(response.id()))
                .body(response);
    }

    /**
     * Get all categories from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @return              all categories from database in response format
     */
    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public Page<CategoryResponse> getAll(@Parameter(hidden = true) Pageable pageable) {
        return categoryCRUD.findAll(pageable);
    }

    /**
     * Get all categories from database in response format.
     *
     * @return              all categories from database in response format
     */
    @GetMapping(value = "/categories/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CategoryResponse> getAllList() {
        return categoryCRUD.findAllList();
    }

    /**
     * Get the category by id from the database in response format.
     *
     * @param id            id of category
     * @return              the category from database in response format
     */
    @GetMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryResponse getById(@PathVariable int id) {
        return categoryCRUD.findById(id)
                .orElseThrow(() -> categoryNotFound(id));
    }

    /**
     * Update the category in the database.
     *
     * @param id            id of category
     * @param request       request with category parameters
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/admins/categories/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable int id, @Valid @RequestBody SaveCategoryRequest request) {
        categoryCRUD.update(id, request);
    }

    /**
     * Delete the category in the database.
     *
     * @param id            id of category
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/admins/categories/{id}")
    public void delete(@PathVariable int id) {
        categoryCRUD.deleteById(id);
    }
}
