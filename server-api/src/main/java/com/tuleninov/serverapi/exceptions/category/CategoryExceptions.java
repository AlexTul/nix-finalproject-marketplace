package com.tuleninov.serverapi.exceptions.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Class for exceptions process for Category with specific HTTP response status codes.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public final class CategoryExceptions {

    private CategoryExceptions() {
    }

    public static ResponseStatusException categoryNotFound(int id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id '" + id + "' not found");
    }

    public static ResponseStatusException duplicateName(String name) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name '" + name + "' already taken");
    }
}
