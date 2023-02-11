/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.controller.index;

import com.nixsolution.alextuleninov.marketplace.marketplaceweb.config.pagination.PaginationConfig;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.controller.CartCount;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.category.CategoryService;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.goods.GoodsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for the index page.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
@RequestMapping("/")
public class IndexController {

    private final CategoryService categoryService;
    private final GoodsService goodsService;

    public IndexController(CategoryService categoryService, GoodsService goodsService) {
        this.categoryService = categoryService;
        this.goodsService = goodsService;
    }

    /**
     * Get index page with parameters.
     *
     * @param request       the servlet container, which provide request information for HTTP servlets
     * @param model         holder for model attributes
     * @return              index page
     */
    @GetMapping
    public String shop(HttpServletRequest request,
                       Model model) {
        var allCategoryUIList = categoryService.findAllList();
        var config = PaginationConfig.config(request);
        var allGoodsUI = goodsService.findAll(PageRequest.of(config.page(), config.size()));

        model.addAttribute("categories", allCategoryUIList);
        model.addAttribute("goods", allGoodsUI);
        model.addAttribute("cartCount", CartCount.cart.size());

        return "index";
    }

    /**
     * Get index page by category id with parameters.
     *
     * @param id            id of category
     * @param request       the servlet container, which provide request information for HTTP servlets
     * @param model         holder for model attributes
     * @return              index page
     */
    @GetMapping("/categories/{id}")
    public String shopByCategory(@PathVariable(value = "id") int id,
                                 HttpServletRequest request,
                                 Model model) {
        var allCategoryUIList = categoryService.findAllList();
        var config = PaginationConfig.config(request);
        var allGoodsUIByCategoryId = goodsService.findAllByCategoryId(
                PageRequest.of(config.page(), config.size()), id);

        model.addAttribute("categories", allCategoryUIList);
        model.addAttribute("goods", allGoodsUIByCategoryId);
        model.addAttribute("cartCount", CartCount.cart.size());

        return "index";
    }

    /**
     * Get goods view page with parameters.
     *
     * @param id            id of goods
     * @param model         holder for model attributes
     * @return              index page
     */
    @GetMapping("/view/{id}")
    public String viewGoods(@PathVariable(value = "id") int id,
                            Model model) {
        model.addAttribute("goods", goodsService.findById(id));
        model.addAttribute("cartCount", CartCount.cart.size());

        return "goods/goods-view";
    }
}
