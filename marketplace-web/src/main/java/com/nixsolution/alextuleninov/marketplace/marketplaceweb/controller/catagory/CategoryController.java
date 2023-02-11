/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.controller.catagory;

import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.category.SaveCategoryRequest;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.config.pagination.PaginationConfig;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.category.CategoryUI;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.category.CategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Controller for the category.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Get category page with parameters.
     *
     * @param request       the servlet container, which provide request information for HTTP servlets
     * @param model         holder for model attributes
     * @return              category page
     */
    @GetMapping("/categories")
    public String categoryPage(HttpServletRequest request,
                               Model model) {
        var config = PaginationConfig.config(request);
        var allCategoryUI = categoryService.findAll(PageRequest.of(config.page(), config.size()));

        model.addAttribute("categories", allCategoryUI);

        return "category/category";
    }

    /**
     * Get category add page with parameters.
     *
     * @param model         holder for model attributes
     * @return              category add page
     */
    @GetMapping("/categories/add")
    public String getCategoryAddPage(Model model) {
        model.addAttribute("category", new CategoryUI());

        return "category/category-add";
    }

    /**
     * Add category to database.
     *
     * @param request       request with category parameters
     * @return              category page
     */
    @PostMapping("/admins/categories")
    public String postCategoryAdd(@Valid @ModelAttribute("category") SaveCategoryRequest request) {
        categoryService.create(request);

        return "redirect:/categories";
    }

    /**
     * Get category update page.
     *
     * @param id            id of category
     * @param model         holder for model attributes
     * @return              category update page
     */
    @GetMapping("/categories/update/{id}")
    public String getCategoryUpdatePage(@PathVariable(value = "id") int id,
                                        Model model) {
        var categoryUI = categoryService.findById(id);

        model.addAttribute("category", categoryUI);

        return "category/category-update";
    }

    /**
     * Update category in database.
     *
     * @param id            id of category
     * @param request       request with category parameters
     * @return              category page
     */
    @PutMapping("/admins/categories/{id}")
    public String updateCategory(@PathVariable(value = "id") int id,
                                 @Valid @ModelAttribute("category") SaveCategoryRequest request) {
        categoryService.update(id, request);

        return "redirect:/categories";
    }

    /**
     * Delete category from database.
     *
     * @param id            id of category
     * @return              category page
     */
    @DeleteMapping("/admins/categories/{id}")
    public String deleteCategory(@PathVariable(value = "id") int id) {
        categoryService.delete(id);

        return "redirect:/categories";
    }
}
