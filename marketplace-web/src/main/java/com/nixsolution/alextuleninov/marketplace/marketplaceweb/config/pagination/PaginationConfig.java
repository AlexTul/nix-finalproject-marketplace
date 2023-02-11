/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.config.pagination;

import com.nixsolution.alextuleninov.marketplace.marketplaceweb.data.pagination.ConfigDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * Pagination configuration.
 * @version 01
 *
 * @author Oleksandr Tuleninov
 */
public class PaginationConfig {

    /**
     * Customize the pagination.
     *
     * @param request   the servlet container, which provide request information for HTTP servlets
     * @return          DTO with pagination settings
     */
    public static ConfigDTO config(HttpServletRequest request) {
        int page = 0;
        int size = 2;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        return new ConfigDTO(page, size);
    }
}
