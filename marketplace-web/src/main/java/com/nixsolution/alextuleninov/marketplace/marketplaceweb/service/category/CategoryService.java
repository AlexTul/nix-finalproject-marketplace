/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.category;

import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.category.SaveCategoryRequest;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.feignclient.CategoryServiceFeignClient;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.category.CategoryUI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Category.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class CategoryService {

    private final CategoryServiceFeignClient categoryServiceFeignClient;

    public CategoryService(CategoryServiceFeignClient categoryServiceFeignClient) {
        this.categoryServiceFeignClient = categoryServiceFeignClient;
    }

    /**
     * Create the category in the database.
     *
     * @param request       request with category parameters
     * @return              the category from database in response format
     */
    public void create(SaveCategoryRequest request) {
        categoryServiceFeignClient.create(request);
    }

    /**
     * Find all categories from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @return              all categories from database in response format
     */
    public Page<CategoryUI> findAll(Pageable pageable) {
        return categoryServiceFeignClient.getAll(pageable)
                .map(CategoryUI::fromCategoryResponse);
    }

    /**
     * Find all categories from database in response format.
     *
     * @return              all categories from database in response format
     */
    public List<CategoryUI> findAllList() {
        return categoryServiceFeignClient.getAllList()
                .stream()
                .map(CategoryUI::fromCategoryResponse).toList();
    }

    /**
     * Find the category by id from the database in response format.
     *
     * @param id            id of category
     * @return              the category from database in response format
     */
    public CategoryUI findById(int id) {
        return CategoryUI.fromCategoryResponse(
                categoryServiceFeignClient.getById(id));
    }



    /**
     * Update the category in the database.
     *
     * @param id            id of category
     * @param request       request with category parameters
     */
    public void update(int id, SaveCategoryRequest request) {
        categoryServiceFeignClient.update(id, request);
    }

    /**
     * Delete the category in the database.
     *
     * @param id            id of category
     */
    public void delete(int id) {
        categoryServiceFeignClient.delete(id);
    }
}
