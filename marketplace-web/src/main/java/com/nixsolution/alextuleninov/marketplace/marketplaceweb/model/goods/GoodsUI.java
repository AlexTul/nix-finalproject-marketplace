/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.goods;

import com.nixsolution.alextuleninov.marketplace.marketplaceweb.data.goods.GoodsUIResponse;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.category.CategoryUI;

/**
 * Class for Goods entity.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class GoodsUI {

    private int id;

    private String name;

    private CategoryUI category;

    private double price;

    private int weight;

    private String description;

    private String imageName;

    public GoodsUI() {
    }

    public GoodsUI(int id, String name, CategoryUI category, double price,
                   int weight, String description, String imageName) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.weight = weight;
        this.description = description;
        this.imageName = imageName;
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

    public CategoryUI getCategory() {
        return category;
    }

    public void setCategory(CategoryUI category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public static GoodsUI fromGoodsResponse(GoodsUIResponse response) {
        return new GoodsUI(
                response.id(),
                response.name(),
                new CategoryUI(response.categoryId(), response.categoryName()),
                response.price(),
                response.weight(),
                response.description(),
                response.imageName()
        );
    }
}
