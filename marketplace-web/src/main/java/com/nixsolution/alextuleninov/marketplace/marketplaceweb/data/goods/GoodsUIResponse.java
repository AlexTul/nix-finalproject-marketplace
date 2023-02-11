/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.data.goods;

/**
 * Record for the goods response.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record GoodsUIResponse(

        int id,

        String name,

        int categoryId,

        String categoryName,

        double price,

        int weight,

        String description,

        String imageName
) {
}
