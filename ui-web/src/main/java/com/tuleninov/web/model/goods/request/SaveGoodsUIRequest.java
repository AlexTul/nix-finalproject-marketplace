package com.tuleninov.web.model.goods.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 * Class for Goods DTO.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class SaveGoodsUIRequest {

    private int id;

    @NotBlank(message = "the name of goods is mandatory")
    @Size(min = 2, max = 30, message = "the name of goods should be between 2 and 30 characters")
    private String name;

    @PositiveOrZero(message = "the category`s id of goods is mandatory")
    private int categoryId;

    @Positive(message = "the price of goods should be greater then 0.0")
    private double price;

    @Positive(message = "the weight of goods should be greater then 0")
    private int weight;

    @NotBlank(message = "the description of goods is mandatory")
    @Size(min = 10, max = 2048, message = "the description of goods should be between 10 and 2048 characters")
    private String description;

    @Size(min = 5, max = 30, message = "the image name of goods should be between 5 and 30 characters")
    private String imageName;

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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

}
