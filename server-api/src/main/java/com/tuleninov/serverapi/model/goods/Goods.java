package com.tuleninov.serverapi.model.goods;

import com.tuleninov.serverapi.model.category.Category;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class for Goods entity.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Entity
@Table(name = "goods")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return Objects.equals(name, goods.name) && Objects.equals(description, goods.description) && Objects.equals(imageName, goods.imageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, imageName);
    }
}
