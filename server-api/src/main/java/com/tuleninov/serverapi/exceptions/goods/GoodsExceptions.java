package com.tuleninov.serverapi.exceptions.goods;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Class for exceptions process for Goods with specific HTTP response status codes.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public final class GoodsExceptions {

    private GoodsExceptions() {
    }

    public static ResponseStatusException goodsNotFound(int id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Goods with id '" + id + "' was not found");
    }

    public static ResponseStatusException duplicateName(String name) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name '" + name + "' already taken");
    }

    public static ResponseStatusException duplicateDescription(String description) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description '" + description + "' already taken");
    }

    public static ResponseStatusException duplicateImageName(String imageName) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "ImageName '" + imageName + "' already taken");
    }
}
