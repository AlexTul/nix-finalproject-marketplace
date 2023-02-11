/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.user;

import com.nixsolution.alextuleninov.marketplace.marketplaceweb.data.user.UserUIResponse;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.role.RoleUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for User entity.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class UserUI {

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<RoleUI> roles;


    public UserUI(int id, String firstName, String lastName, String email, String password, List<RoleUI> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserUI(UserUI userUI) {
        this.id = userUI.getId();
        this.firstName = userUI.getFirstName();
        this.lastName = userUI.getLastName();
        this.email = userUI.getEmail();
        this.password = userUI.getPassword();
        this.roles = userUI.roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleUI> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleUI> roles) {
        this.roles = roles;
    }

    public static UserUI fromUserResponse(UserUIResponse response) {
        List<RoleUI> roles = new ArrayList<>();
        roles.add(new RoleUI(response.roleId(), response.roleName()));
        return new UserUI(
                response.id(),
                response.firstName(),
                response.lastName(),
                response.email(),
                response.password(),
                roles
        );
    }
}
