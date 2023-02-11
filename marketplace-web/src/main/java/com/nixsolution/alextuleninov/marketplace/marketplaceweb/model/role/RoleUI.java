/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.role;

/**
 * Class for Role entity.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class RoleUI {

    private int id;

    private String name;

    public RoleUI(int id, String name) {
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
}
