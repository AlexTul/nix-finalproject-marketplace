package com.tuleninov.web.model.goods;

import com.tuleninov.web.model.category.CategoryUI;
import com.tuleninov.web.model.goods.response.GoodsUIResponse;

/**
 * Class for Goods entity.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class GoodsUI {

    private final int id;

    private final String name;

    private final CategoryUI category;

    private final double price;

    private final int weight;

    private final String description;

    private final String imageName;

    private GoodsUI(int id, String name, CategoryUI category, double price,
                    int weight, String description, String imageName) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.weight = weight;
        this.description = description;
        this.imageName = imageName;
    }

    public static GoodsUI create(int id, String name, CategoryUI category, double price,
                                 int weight, String description, String imageName) {
        return new GoodsUI(id, name, category, price, weight, description, imageName);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryUI getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }

    public String getImageName() {
        return imageName;
    }

    public static GoodsUI fromGoodsResponse(GoodsUIResponse response) {
        return new GoodsUIBuilder()
                .id(response.id())
                .name(response.name())
                .category(new CategoryUI(response.categoryId(), response.categoryName()))
                .price(response.price())
                .weight(response.weight())
                .description(response.description())
                .imageName(response.imageName())
                .build();
    }

    public static class GoodsUIBuilder {

        private int id;

        private String name;

        private CategoryUI category;

        private double price;

        private int weight;

        private String description;

        private String imageName;

        public GoodsUIBuilder id(int id) {
            this.id = id;
            return this;
        }

        public GoodsUIBuilder name(String name) {
            this.name = name;
            return this;
        }

        public GoodsUIBuilder category(CategoryUI category) {
            this.category = category;
            return this;
        }

        public GoodsUIBuilder price(double price) {
            this.price = price;
            return this;
        }

        public GoodsUIBuilder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public GoodsUIBuilder description(String description) {
            this.description = description;
            return this;
        }

        public GoodsUIBuilder imageName(String imageName) {
            this.imageName = imageName;
            return this;
        }

        public GoodsUI build() {
            return GoodsUI.create(id, name, category,
                    price, weight, description, imageName);
        }
    }
}
