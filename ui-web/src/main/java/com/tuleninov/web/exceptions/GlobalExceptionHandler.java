package com.tuleninov.web.exceptions;

import com.tuleninov.web.Routes;
import feign.FeignException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tuleninov.web.AppConstants.*;

/**
 * Controller for handling exceptions.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle validation exceptions on UI side.
     *
     * @param ex                 errors
     * @param redirectAttributes the given flash attribute
     * @return the page where the error occurred
     */
    @ExceptionHandler(BindException.class)
    public String handleMethodArgumentNotValidException(BindException ex,
                                                        RedirectAttributes redirectAttributes,
                                                        HttpServletRequest req) {
        String objectName = ex.getObjectName();

        switch (objectName) {
            case "forgotPasswordUIRequest" -> {
                handleUIValidationError(ex, redirectAttributes);

                return "redirect:" + Routes.WEB_USERS + "/forgot";
            }
            case "saveUserUIRequest" -> {
                handleUIValidationError(ex, redirectAttributes);
                Object login = req.getSession().getAttribute(SCOPE_LOGIN);

                return (login != null) ? "redirect:" + Routes.WEB_USERS + "/admins"
                        : "redirect:" + Routes.WEB_USERS + "/register";
            }
            case "overrideUserUIPasswordRequest" -> {
                handleUIValidationError(ex, redirectAttributes);
                String url = req.getRequestURL().toString();
                long id = extractIdForUrlByOverrideUserUIPasswordRequest(url);
                return "redirect:" + Routes.WEB_USERS + "/" + id + "/password";
            }
            case "mergeUserUIRequest" -> {
                handleUIValidationError(ex, redirectAttributes);
                String url = req.getRequestURL().toString();
                long id = extractIdFromUrlForMergeUserUIRequest(url);
                if (id > -1) {
                    return "redirect:" + Routes.WEB_USERS + "/" + id;
                } else {
                    return "redirect:" + Routes.WEB_PROFILE + "/me";
                }
            }
            case "changeUserUIPasswordRequest" -> {
                handleUIValidationError(ex, redirectAttributes);
                return "redirect:" + Routes.WEB_PROFILE + "/me/password";
            }
            case "saveCategoryUIRequest" -> {
                handleUIValidationError(ex, redirectAttributes);
                return "redirect:" + Routes.WEB_CATEGORIES + "/create";
            }
            case "saveGoodsUIRequest" -> {
                handleUIValidationError(ex, redirectAttributes);
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
     * @param ex    error
     * @param model holder for model attributes
     * @return error page
     */
    @ExceptionHandler(FeignException.class)
    public String handleServerBadRequestException(FeignException ex, HttpServletRequest req, Model model) {
        HttpStatus status = HttpStatus.resolve(ex.status());
        if (status == HttpStatus.METHOD_NOT_ALLOWED
                || status == HttpStatus.NOT_FOUND
                || status == HttpStatus.BAD_REQUEST
                || status == HttpStatus.FORBIDDEN
                || status == HttpStatus.UNAUTHORIZED) {
            String errorMessage = ex.getMessage();
            String message = createMessage(status, errorMessage);

            log.error(errorMessage);
            model.addAttribute(SCOPE_MESSAGE, message);

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
     * Handle File exceptions on UI side.
     *
     * @param model holder for model attributes
     * @return error page
     */
    @ExceptionHandler(RuntimeException.class)
    public String handleUIFileException(RuntimeException ex, Model model) {
        String message = ex.getMessage();
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
    public String handleException(Exception e, Model model) {
        model.addAttribute(SCOPE_MESSAGE, "Something is wrong");
        log.error(e.getMessage());

        return "error/error";
    }

    private void handleUIValidationError(BindException ex, RedirectAttributes redirectAttributes) {
        log.error(ex.getMessage());
        FieldError fieldError = ex.getFieldError();
        String message = Objects.requireNonNull(fieldError).getDefaultMessage();
        redirectAttributes.addFlashAttribute(Objects.requireNonNull(fieldError).getField(), message);
    }

    private String createMessage(HttpStatus status, String errorMessage) {
        String regex = "error\":\"" + status + " \\\\\"(.*?)\\\\\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(errorMessage);
        return matcher.find() ? matcher.group(1) : null;
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
