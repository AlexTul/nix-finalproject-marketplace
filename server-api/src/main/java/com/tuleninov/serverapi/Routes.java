package com.tuleninov.serverapi;

import java.util.UUID;

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

    public static final String USERS = API_ROOT + "/users";

    public static final String FILES = API_ROOT + "/files";

    public static final String TOKEN = API_ROOT + "/token";

    public static final String CATEGORIES = API_ROOT + "/categories";
    public static final String GOODS = API_ROOT + "/goods";

    public static String user(long id) {
        return USERS + '/' + id;
    }
}
