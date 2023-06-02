package com.tuleninov.serverapi.model.goods.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 * Record for the goods request.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record SaveGoodsRequest(

        @NotBlank(message = "the name of goods is mandatory")
        @Size(min = 2, max = 30, message = "the name of goods should be between 2 and 30 characters")
        String name,

        @PositiveOrZero(message = "the category id of goods is mandatory")
        int categoryId,

        @Positive(message = "the price of goods should be greater then 0.0")
        double price,

        @Positive(message = "the weight of goods should be greater then 0")
        int weight,

        @NotBlank(message = "the description of goods is mandatory")
        @Size(min = 10, max = 2048, message = "the description of goods should be between 10 and 2048 characters")
        String description,

        @Size(min = 5, max = 30, message = "the image name of goods should be between 5 and 30 characters")
        String imageName

) {
}

