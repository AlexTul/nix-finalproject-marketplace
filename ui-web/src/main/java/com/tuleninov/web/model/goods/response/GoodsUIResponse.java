package com.tuleninov.web.model.goods.response;

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
