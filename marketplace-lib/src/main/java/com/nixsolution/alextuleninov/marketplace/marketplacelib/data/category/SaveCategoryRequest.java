/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplacelib.data.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Record for the category request.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record SaveCategoryRequest(
        @NotBlank(message = "The name of category is mandatory")
        @Size(min = 2, max = 30, message = "The name of category should be between 2 and 30 characters")
        String name
) {
}
