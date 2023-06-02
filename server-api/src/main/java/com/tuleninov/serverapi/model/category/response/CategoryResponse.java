package com.tuleninov.serverapi.model.category.response;

import com.tuleninov.serverapi.model.category.Category;

/**
 * Record for the category response.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record CategoryResponse(int id,
                               String name) {

    /**
     * Create the new record for the category.
     *
     * @param category category
     * @return new record from Category
     */
    public static CategoryResponse fromCategory(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}
