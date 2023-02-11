/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.data.category;

import com.nixsolution.alextuleninov.marketplace.marketplaceapi.model.category.Category;

/**
 * Record for the category response.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record CategoryResponse(

        int id,

        String name
) {

    /**
     * Create the new record from Category.
     *
     * @param category      category
     * @return              new record from Category
     */
    public static CategoryResponse fromCategory(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}
