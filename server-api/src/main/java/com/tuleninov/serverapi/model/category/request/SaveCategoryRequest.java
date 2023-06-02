package com.tuleninov.serverapi.model.category.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Record for the category request.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record SaveCategoryRequest(

        @NotBlank(message = "the name of category is mandatory")
        @Size(min = 2, max = 30, message = "the name of category should be between 2 and 30 characters")
        String name

) {
}
