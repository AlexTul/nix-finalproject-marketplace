/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.category;

import com.nixsolution.alextuleninov.marketplace.marketplaceweb.data.category.CategoryUIResponse;

/**
 * Class for Category entity.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class CategoryUI {

    private int id;

    private String name;

    public CategoryUI() {
    }

    public CategoryUI(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CategoryUI fromCategoryResponse(CategoryUIResponse response) {
        return new CategoryUI(
                response.id(),
                response.name()
        );
    }
}
