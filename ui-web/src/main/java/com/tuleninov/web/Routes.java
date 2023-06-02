package com.tuleninov.web;

/**
 * The Routes class contains the routes for application.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public final class Routes {

    private Routes() {
        throw new AssertionError("non-instantiable class");
    }

    public static final String API_ROOT = "/api/v1";
    public static final String API_TOKEN = API_ROOT + "/token";
    public static final String API_USERS = API_ROOT + "/users";
    public static final String API_CATEGORIES = API_ROOT + "/categories";
    public static final String API_GOODS = API_ROOT + "/goods";
    public static final String WEB_INDEX = "/";
    public static final String WEB_ROOT = "/web/v1";
    public static final String WEB_TOKEN = WEB_ROOT + "/token";
    public static final String WEB_ADMINS = WEB_ROOT + "/admins";
    public static final String WEB_USERS = WEB_ROOT + "/users";
    public static final String WEB_PROFILE = WEB_ROOT + "/profile";
    public static final String WEB_LOGOUT = WEB_ROOT + "/logout";
    public static final String WEB_CATEGORIES = WEB_ROOT + "/categories";
    public static final String WEB_GOODS = WEB_ROOT + "/goods";
    public static final String WEB_GOODS_CATEGORY = WEB_GOODS + "/category";
    public static final String WEB_GOODS_VIEW = WEB_GOODS + "/view";
    public static final String WEB_CART = WEB_ROOT + "/cart";

}
