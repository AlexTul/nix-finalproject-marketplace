package com.tuleninov.web.model.category;

import com.tuleninov.web.model.category.response.CategoryUIResponse;

/**
 * Class for Category entity.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class CategoryUI {

    private int id;

    private String name;

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
