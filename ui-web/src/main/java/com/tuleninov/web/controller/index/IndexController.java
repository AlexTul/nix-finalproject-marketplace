package com.tuleninov.web.controller.index;

import com.tuleninov.web.Routes;
import com.tuleninov.web.config.pagination.ConfigDTO;
import com.tuleninov.web.config.pagination.PaginationConfig;
import com.tuleninov.web.model.goods.GoodsUI;
import com.tuleninov.web.service.category.CategoryService;
import com.tuleninov.web.service.goods.GoodsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

import static com.tuleninov.web.AppConstants.*;

/**
 * Controller for the index page.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
public class IndexController {

    private final CategoryService categoryService;
    private final GoodsService goodsService;

    public IndexController(CategoryService categoryService, GoodsService goodsService) {
        this.categoryService = categoryService;
        this.goodsService = goodsService;
    }

    /**
     * Get the index page.
     *
     * @param req   an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @param model holder for model attributes
     * @return index page
     */
    @GetMapping(Routes.WEB_INDEX)
    public String getIndexPage(HttpServletRequest req,
                               Model model) {
        var categories = categoryService.getListCategories();
        var config = PaginationConfig.config(req);
        var goods = getGoods(config);

        model.addAttribute(SCOPE_CATEGORIES, categories);
        model.addAttribute(SCOPE_OBJECTS, goods);

        return "index";
    }

    /**
     * Get index page by category id.
     *
     * @param id    the id of the category
     * @param req   an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @param model holder for model attributes
     * @return the index page
     */
    @GetMapping(Routes.WEB_GOODS_CATEGORY + "/{id}")
    public String getIndexPageByCategoryId(@PathVariable(value = "id") int id,
                                           HttpServletRequest req,
                                           Model model) {
        var categories = categoryService.getListCategories();
        var config = PaginationConfig.config(req);
        var goods = getGoodsByCategoryId(config, id);

        model.addAttribute(SCOPE_CATEGORIES, categories);
        model.addAttribute(SCOPE_OBJECTS, goods);

        return "index";
    }

    /**
     * Get goods view page.
     *
     * @param id    the id of the goods
     * @param model holder for model attributes
     * @return goods view page
     */
    @GetMapping(Routes.WEB_GOODS_VIEW + "/{id}")
    public String getGoodsViewPage(@PathVariable(value = "id") int id,
                                   Model model) {
        model.addAttribute(SCOPE_GOODS, goodsService.getGoodsById(id));

        return "goods/goods-view";
    }

    /**
     * Get the goods from the database.
     * (If the user enters an incorrect page number, which is greater than the actual one,
     * into the address bar, it will redirect to the last valid page).
     *
     * @param config pagination config
     * @return goods
     */
    private Page<GoodsUI> getGoods(ConfigDTO config) {
        var goods = goodsService.listGoods(PageRequest.of(config.page(), config.size()));

        var totalPages = goods.getTotalPages();
        if (totalPages > 0 && config.page() + 1 > totalPages) {
            goods = goodsService.listGoods(PageRequest.of(totalPages - 1, config.size()));
        }

        return goods;
    }

    /**
     * Get the goods from the database.
     * (If the user enters an incorrect page number, which is greater than the actual one,
     * into the address bar, it will redirect to the last valid page).
     *
     * @param config pagination config
     * @param id     the id of the category
     * @return goods
     */
    private Page<GoodsUI> getGoodsByCategoryId(ConfigDTO config, int id) {
        var goods = goodsService.listByCategoryId(PageRequest.of(config.page(), config.size()), id);

        var totalPages = goods.getTotalPages();
        if (totalPages > 0 && config.page() + 1 > totalPages) {
            goods = goodsService.listByCategoryId(PageRequest.of(totalPages - 1, config.size()), id);
        }

        return goods;
    }
}
