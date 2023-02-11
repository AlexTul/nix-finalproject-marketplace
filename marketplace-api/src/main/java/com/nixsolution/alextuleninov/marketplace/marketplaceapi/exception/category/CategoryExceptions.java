/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.exception.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Class for exceptions process for Category with specific HTTP response status codes.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public final class CategoryExceptions {

    public static ResponseStatusException categoryNotFound(int categoryId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id " + categoryId + " was not found");
    }
}
