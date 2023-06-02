package com.tuleninov.web.config.pagination;

import javax.servlet.http.HttpServletRequest;

import static com.tuleninov.web.AppConstants.SCOPE_PAGE;
import static com.tuleninov.web.AppConstants.SCOPE_SIZE;

/**
 * Pagination configuration.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class PaginationConfig {

    /**
     * Customize the pagination.
     *
     * @param req the servlet container, which provide req information for HTTP servlets
     * @return DTO with pagination settings
     */
    public static ConfigDTO config(HttpServletRequest req) {
        int page = 0;
        int size = 2;

        if (req.getParameter(SCOPE_PAGE) != null
                && !req.getParameter(SCOPE_PAGE).isEmpty()
                && req.getParameter(SCOPE_PAGE).matches("\\d+")) {
            page = Integer.parseInt(req.getParameter(SCOPE_PAGE)) - 1;
        }
        if (req.getParameter(SCOPE_SIZE) != null
                && !req.getParameter(SCOPE_SIZE).isEmpty()
                && req.getParameter(SCOPE_SIZE).matches("\\d+")) {
            size = Integer.parseInt(req.getParameter(SCOPE_SIZE));
        }

        return new ConfigDTO(page, size);
    }
}
