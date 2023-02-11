/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.exception.goods;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Class for exceptions process for Goods with specific HTTP response status codes.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class GoodsExceptions {

    public static ResponseStatusException goodsNotFound(int goodsId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Goods with id " + goodsId + " was not found");
    }
}
