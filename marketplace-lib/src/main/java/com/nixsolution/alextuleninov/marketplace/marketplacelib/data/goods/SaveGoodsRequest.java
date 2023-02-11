/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplacelib.data.goods;

import javax.validation.constraints.*;

/**
 * Record for the goods request.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record SaveGoodsRequest (

    @NotBlank(message = "The name of goods is mandatory")
    @Size(min = 2, max = 30, message = "The name of goods should be between 2 and 30 characters")
    String name,

    @PositiveOrZero(message = "The category id of goods is mandatory")
    int categoryId,

    @Positive(message = "Price of goods should be greater then 0.0")
    double price,

    @Positive(message = "The weight of goods should be greater then 0")
    int weight,

    @NotBlank(message = "The description of goods is mandatory")
    @Size(min = 10, max = 2048, message = "The description of goods to long (more then 2 kB)")
    String description,

    @Size(min = 5, max = 30, message = "The image name of goods should be between 5 and 30 characters")
    String imageName
) {
}

