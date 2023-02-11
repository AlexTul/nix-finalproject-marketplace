/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.data.goods;

import com.nixsolution.alextuleninov.marketplace.marketplaceapi.model.goods.Goods;

/**
 * Record for the goods response.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record GoodsResponse(

        int id,

        String name,

        int categoryId,

        String categoryName,

        double price,

        int weight,

        String description,

        String imageName
) {

    /**
     * Create the new record from Goods.
     *
     * @param goods         goods
     * @return              new record from Goods
     */
    public static GoodsResponse fromGoods(Goods goods) {
        return new GoodsResponse(
                goods.getId(),
                goods.getName(),
                goods.getCategory().getId(),
                goods.getCategory().getName(),
                goods.getPrice(),
                goods.getWeight(),
                goods.getDescription(),
                goods.getImageName()
        );
    }
}
