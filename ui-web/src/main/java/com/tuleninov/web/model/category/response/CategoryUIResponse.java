package com.tuleninov.web.model.category.response;

import com.tuleninov.web.model.category.CategoryUI;

/**
 * Record for the category response.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record CategoryUIResponse(

        int id,

        String name

) {

    public static CategoryUI fromCategoryUIResponse(CategoryUIResponse response) {
        return new CategoryUI(
                response.id(),
                response.name()
        );
    }
}
