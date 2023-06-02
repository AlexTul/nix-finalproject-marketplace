package com.tuleninov.web;

import java.time.format.DateTimeFormatter;

/**
 * The Constants class contains the constants for application.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public final class AppConstants {

    public AppConstants() {
        throw new AssertionError("non-instantiable class");
    }

    // region scope
    public static final String SCOPE_MESSAGE = "message";
    public static final String SCOPE_USER = "user";
    public static final String SCOPE_USER_ID = "userId";
    public static final String SCOPE_OBJECTS = "objects";
    public static final String SCOPE_LOGIN = "login";
    public static final String SCOPE_ACCESS_TOKEN = "accessToken";
    public static final String SCOPE_REFRESH_TOKEN = "refreshToken";
    public static final String SCOPE_CONTROL_DATE_TIME_TOKEN = "controlDateTimeToken";
    public static final String SCOPE_HEADER_CONTENT = "headerContent";
    public static final String SCOPE_PAGE = "page";
    public static final String SCOPE_SIZE = "size";
    public static final String SCOPE_CATEGORY = "category";
    public static final String SCOPE_CATEGORIES = "categories";
    public static final String SCOPE_GOODS = "goods";
    public static final String SCOPE_CART = "cart";
    public static final String SCOPE_CART_COUNT = "cartCount";
    public static final String SCOPE_CART_TOTAL = "total";
    public static final String SCOPE_CART_SIZE = "cartSize";
    public static final String SCOPE_CAPTCHA_ERROR = "captchaError";
    public static final String SCOPE_SITE_KEY = "siteKey";
    public static final String SCOPE_MESSAGE_ERROR_EMAIL = "messageErrorEmail";
    public static final String SCOPE_MESSAGE_ERROR_NICKNAME = "messageErrorNickname";
    public static final String SCOPE_MESSAGE_ERROR_PASSWORD = "messageErrorPassword";
    public static final String SCOPE_MESSAGE_ERROR_PASSWORD_CONFIRM = "messageErrorPasswordConfirm";
    public static final String SCOPE_MESSAGE_ERROR_PASSWORD_NOT_MATCH = "messageErrorPasswordNotMatch";
    public static final String SCOPE_MESSAGE_ERROR_PASSWORD_OLD = "messageErrorPasswordOld";
    public static final String SCOPE_MESSAGE_ERROR_PASSWORD_NEW = "messageErrorPasswordNew";
    public static final String SCOPE_MESSAGE_ERROR_NAME = "messageErrorName";
    public static final String SCOPE_MESSAGE_ERROR_CATEGORY_ID = "messageErrorCategoryId";
    public static final String SCOPE_MESSAGE_ERROR_PRICE = "messageErrorNamePrice";
    public static final String SCOPE_MESSAGE_ERROR_WEIGHT = "messageErrorWeight";
    public static final String SCOPE_MESSAGE_ERROR_DESCRIPTION = "messageErrorDescription";
    public static final String SCOPE_MESSAGE_ERROR_IMAGE_NAME = "messageErrorImageName";
    public static final String EMAIL = "email";
    public static final String NICKNAME = "nickname";
    public static final String PASSWORD = "password";
    public static final String PASSWORD_CONFIRM = "confirmPassword";
    public static final String PASSWORD_NEW = "newPassword";
    public static final String PASSWORD_OLD = "oldPassword";
    public static final String NAME = "name";
    public static final String CATEGORY_ID = "categoryId";
    public static final String PRICE = "price";
    public static final String WEIGHT = "weight";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE_NAME = "imageName";
    // endregion scope

    // region to format date and time
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    // endregion to format date and time
}
