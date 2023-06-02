package com.tuleninov.web.controller.category;

import com.tuleninov.web.Routes;
import com.tuleninov.web.config.pagination.ConfigDTO;
import com.tuleninov.web.config.pagination.PaginationConfig;
import com.tuleninov.web.controller.TokenProvider;
import com.tuleninov.web.model.category.CategoryUI;
import com.tuleninov.web.model.category.request.SaveCategoryUIRequest;
import com.tuleninov.web.service.category.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.tuleninov.web.AppConstants.SCOPE_CATEGORY;
import static com.tuleninov.web.AppConstants.SCOPE_OBJECTS;

/**
 * Controller for the category.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
@RequestMapping(Routes.WEB_CATEGORIES)
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private final TokenProvider tokenProvider;
    private final CategoryService categoryService;

    public CategoryController(TokenProvider tokenProvider, CategoryService categoryService) {
        this.tokenProvider = tokenProvider;
        this.categoryService = categoryService;
    }

    /**
     * Get category`s page.
     *
     * @param req   an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @param model holder for model attributes
     * @return category page
     */
    @GetMapping
    public String getCategoriesPage(HttpServletRequest req,
                                    Model model) {
        var token = tokenProvider.provideTokenForHeader(req);
        var config = PaginationConfig.config(req);
        var categories = getCategories(token, config);

        model.addAttribute(SCOPE_OBJECTS, categories);

        return "category/category";
    }

    /**
     * Get the category create page with parameters.
     *
     * @return category`s create page
     */
    @GetMapping(value = "/create")
    public String getCreateCategoryPage() {
        return "category/category-create";
    }

    /**
     * Create the category to the database.
     *
     * @param request the request with category`s parameters
     * @param req     an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @return categories page
     */
    @PostMapping
    public String createCategory(@Valid SaveCategoryUIRequest request,
                                 HttpServletRequest req) {
        var token = tokenProvider.provideTokenForHeader(req);
        var category = categoryService.create(token, request);

        log.info("Category '" + category.getName() + "' has been added to database.");

        return "redirect:" + Routes.WEB_CATEGORIES;
    }

    /**
     * Get the merge category`s page.
     *
     * @param id    the id of the category
     * @param req   an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @param model holder for model attributes
     * @return category`s update page
     */
    @GetMapping(value = "/{id}")
    public String getMergeCategoryPage(@PathVariable(value = "id") int id,
                                       HttpServletRequest req,
                                       Model model) {
        var token = tokenProvider.provideTokenForHeader(req);
        var category = categoryService.getCategoryById(token, id);

        model.addAttribute(SCOPE_CATEGORY, category);

        return "category/category-merge";
    }

    /**
     * Merge the category by id in database.
     *
     * @param id      the id of the category
     * @param request the request with category parameters
     * @param req     an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @return categories page
     */
    @PatchMapping(value = "/{id}")
    public String mergeCategoryById(@PathVariable(value = "id") int id,
                                    @Valid SaveCategoryUIRequest request,
                                    HttpServletRequest req) {
        var token = tokenProvider.provideTokenForHeader(req);
        var category = categoryService.mergeCategoryById(token, id, request);

        log.info("Category '" + category.getName() + "' were merged.");

        return "redirect:" + Routes.WEB_CATEGORIES;
    }

    /**
     * Delete the category from the database.
     *
     * @param id  the id of the category
     * @param req an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @return category page
     */
    @DeleteMapping(value = "/{id}")
    public String deleteCategoryById(@PathVariable(value = "id") int id,
                                     HttpServletRequest req) {
        var token = tokenProvider.provideTokenForHeader(req);
        var category = categoryService.deleteCategoryById(token, id);

        log.info("Category '" + category.getName() + "' was deleted.");

        return "redirect:" + Routes.WEB_CATEGORIES;
    }

    /**
     * Get the categories from the database.
     * (If the user enters an incorrect page number, which is greater than the actual one,
     * into the address bar, it will redirect to the last valid page).
     *
     * @param token  the token to access the corresponding endpoint
     * @param config pagination config
     * @return categories
     */
    private Page<CategoryUI> getCategories(String token, ConfigDTO config) {
        var categories = categoryService.listCategories(token, PageRequest.of(config.page(), config.size()));

        var totalPages = categories.getTotalPages();
        if (totalPages > 0 && config.page() + 1 > totalPages) {
            categories = categoryService.listCategories(token, PageRequest.of(totalPages - 1, config.size()));
        }

        return categories;
    }
}
