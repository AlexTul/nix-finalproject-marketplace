package com.tuleninov.web.controller.admin;

import com.tuleninov.web.Routes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the admin`s page.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
@RequestMapping(Routes.WEB_ADMINS)
public class AdminController {

    /**
     * Return the admin`s page.
     *
     * @return the admin`s page
     */
    @GetMapping
    public String getAdminPage() {
        return "admin/admin";
    }
}
