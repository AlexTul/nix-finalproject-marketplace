/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.service.category;

import com.nixsolution.alextuleninov.marketplace.marketplaceapi.data.category.CategoryResponse;
import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.category.SaveCategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interface CRUD for Category.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface CategoryCRUD {

    /**
     * Create the category in the database.
     *
     * @param request       request with category parameters
     * @return              the category from database in response format
     */
    CategoryResponse create(SaveCategoryRequest request);

    /**
     * Find all categories from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @return              all categories from database in response format
     */
    Page<CategoryResponse> findAll(Pageable pageable);

    /**
     * Find all categories from database in response format.
     *
     * @return              all categories from database in response format
     */
    List<CategoryResponse> findAllList();

    /**
     * Find the category by id from the database in response format.
     *
     * @param id            id of category
     * @return              the category from database in response format
     */
    Optional<CategoryResponse> findById(int id);

    /**
     * Update the category in the database.
     *
     * @param id            id of category
     * @param request       request with category parameters
     */
    void update(int id, SaveCategoryRequest request);

    /**
     * Delete the category in the database.
     *
     * @param id            id of category
     */
    void deleteById(int id);
}
