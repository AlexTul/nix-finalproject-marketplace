package com.tuleninov.web.controller.exceptions;

import com.tuleninov.web.Routes;
import com.tuleninov.web.controller.logout.LogoutController;
import feign.FeignException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.tuleninov.web.AppConstants.*;

/**
 * Controller for handling exceptions.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

    /**
     * Handle validation exceptions on UI side.
     *
     * @param e                  errors
     * @param redirectAttributes the given flash attribute
     * @return the page where the error occurred
     */
    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException e, RedirectAttributes redirectAttributes, HttpServletRequest req) {
        String objectName = e.getBindingResult().getObjectName();
        Object login = req.getSession().getAttribute(SCOPE_LOGIN);

        switch (objectName) {
            case "forgotPasswordUIRequest" -> {
                createMessage(e, EMAIL, redirectAttributes, SCOPE_MESSAGE_ERROR_EMAIL);
                return "redirect:" + Routes.WEB_USERS + "/forgot";
            }
            case "saveUserUIRequest" -> {
                createMessage(e, EMAIL, redirectAttributes, SCOPE_MESSAGE_ERROR_EMAIL);
                createMessage(e, NICKNAME, redirectAttributes, SCOPE_MESSAGE_ERROR_NICKNAME);
                createMessage(e, PASSWORD, redirectAttributes, SCOPE_MESSAGE_ERROR_PASSWORD);
                createMessage(e, PASSWORD_CONFIRM, redirectAttributes, SCOPE_MESSAGE_ERROR_PASSWORD_CONFIRM);
                if (login != null) {
                    return "redirect:" + Routes.WEB_USERS + "/admins";
                } else {
                    return "redirect:" + Routes.WEB_USERS + "/register";
                }
            }
            case "overrideUserUIPasswordRequest" -> {
                createMessage(e, PASSWORD, redirectAttributes, SCOPE_MESSAGE_ERROR_PASSWORD);
                createMessage(e, PASSWORD_CONFIRM, redirectAttributes, SCOPE_MESSAGE_ERROR_PASSWORD_CONFIRM);
                String url = req.getRequestURL().toString();
                long id = extractIdForUrlByOverrideUserUIPasswordRequest(url);
                return "redirect:" + Routes.WEB_USERS + "/" + id + "/password";
            }
            case "mergeUserUIRequest" -> {
                createMessage(e, EMAIL, redirectAttributes, SCOPE_MESSAGE_ERROR_EMAIL);
                createMessage(e, NICKNAME, redirectAttributes, SCOPE_MESSAGE_ERROR_NICKNAME);
                String url = req.getRequestURL().toString();
                long id = extractIdFromUrlForMergeUserUIRequest(url);
                if (id > -1) {
                    return "redirect:" + Routes.WEB_USERS + "/" + id;
                } else {
                    return "redirect:" + Routes.WEB_PROFILE + "/me";
                }
            }
            case "changeUserUIPasswordRequest" -> {
                createMessage(e, PASSWORD_OLD, redirectAttributes, SCOPE_MESSAGE_ERROR_PASSWORD_OLD);
                createMessage(e, PASSWORD_NEW, redirectAttributes, SCOPE_MESSAGE_ERROR_PASSWORD_NEW);
                createMessage(e, PASSWORD_CONFIRM, redirectAttributes, SCOPE_MESSAGE_ERROR_PASSWORD_CONFIRM);
                return "redirect:" + Routes.WEB_PROFILE + "/me/password";
            }
            case "saveCategoryUIRequest" -> {
                createMessage(e, NAME, redirectAttributes, SCOPE_MESSAGE_ERROR_NAME);
                return "redirect:" + Routes.WEB_CATEGORIES + "/create";
            }
            case "saveGoodsUIRequest" -> {
                createMessage(e, NAME, redirectAttributes, SCOPE_MESSAGE_ERROR_NAME);
                createMessage(e, CATEGORY_ID, redirectAttributes, SCOPE_MESSAGE_ERROR_CATEGORY_ID);
                createMessage(e, PRICE, redirectAttributes, SCOPE_MESSAGE_ERROR_PRICE);
                createMessage(e, WEIGHT, redirectAttributes, SCOPE_MESSAGE_ERROR_WEIGHT);
                createMessage(e, DESCRIPTION, redirectAttributes, SCOPE_MESSAGE_ERROR_DESCRIPTION);
                createMessage(e, IMAGE_NAME, redirectAttributes, SCOPE_MESSAGE_ERROR_IMAGE_NAME);
                return "redirect:" + Routes.WEB_GOODS + "/create";
            }
            default -> {
                return "error/error";
            }
        }
    }

    /**
     * Handle exceptions on server side.
     *
     * @param e     errors
     * @param model holder for model attributes
     * @return error page
     */
    @ExceptionHandler(FeignException.class)
    public String handleBadRequestException(FeignException e, HttpServletRequest req, Model model) {
        HttpStatus status = HttpStatus.resolve(e.status());
        if (status == HttpStatus.NOT_FOUND ||
                status == HttpStatus.BAD_REQUEST ||
                status == HttpStatus.FORBIDDEN) {
            String message = StringUtils.substringBetween(e.getMessage(), "message\":\"", "\",\"path");
            model.addAttribute(SCOPE_MESSAGE, message);
            log.error(message);

            return "error/error";
        } else {
            HttpSession session = req.getSession();
            session.removeAttribute(SCOPE_LOGIN);
            session.removeAttribute(SCOPE_ACCESS_TOKEN);
            session.removeAttribute(SCOPE_REFRESH_TOKEN);
            session.removeAttribute(SCOPE_CONTROL_DATE_TIME_TOKEN);
            session.removeAttribute(SCOPE_HEADER_CONTENT);

            log.error("User not authorized");

            return "redirect:" + Routes.WEB_INDEX;
        }
    }

    /**
     * Handle File exceptions.
     *
     * @param model holder for model attributes
     * @return error page
     */
    @ExceptionHandler(RuntimeException.class)
    public String handleFileException(RuntimeException e, Model model) {
        String message = e.getMessage();
        model.addAttribute(SCOPE_MESSAGE, message);
        log.error(message);

        return "error/error";
    }

    /**
     * Handle another exceptions.
     *
     * @param model holder for model attributes
     * @return error page
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Model model) {
        model.addAttribute(SCOPE_MESSAGE, "Something is wrong");
        log.error("Something is wrong in the UI side");

        return "error/error";
    }

    private void createMessage(BindException e, String field, RedirectAttributes redirectAttributes, String scopeMessageError) {
        FieldError fieldError = e.getBindingResult().getFieldError(field);
        if (fieldError != null) {
            String message = Objects.requireNonNull(e.getBindingResult().getFieldError(field)).getDefaultMessage();
            log.error(message);
            redirectAttributes.addFlashAttribute(scopeMessageError, message);
        }
    }

    private long extractIdForUrlByOverrideUserUIPasswordRequest(String url) {
        String[] parts = url.split("/");
        String id = parts[parts.length - 2];
        return Long.parseLong(id);
    }

    private long extractIdFromUrlForMergeUserUIRequest(String url) {
        String[] parts = url.split("/");
        String id = parts[parts.length - 1];
        boolean isNumber = id.matches("-?\\d+(\\.\\d+)?");
        if (isNumber) {
            return Long.parseLong(id);
        } else {
            return -1;
        }
    }
}
