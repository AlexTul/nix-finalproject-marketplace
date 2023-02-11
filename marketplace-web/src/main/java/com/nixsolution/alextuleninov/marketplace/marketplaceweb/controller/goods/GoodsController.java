/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.controller.goods;

import com.nixsolution.alextuleninov.marketplace.marketplaceweb.config.pagination.PaginationConfig;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.io.remove.Remover;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.io.write.Writer;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.goods.GoodsDTO;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.category.CategoryService;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.goods.GoodsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;

/**
 * Controller for the goods.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
public class GoodsController {

    @Value("${upload.path}")
    private String uploadPath;

    private final CategoryService categoryService;
    private final GoodsService goodsService;

    private final Writer writer;
    private final Remover remover;

    public GoodsController(CategoryService categoryService, GoodsService goodsService, Writer writer, Remover remover) {
        this.categoryService = categoryService;
        this.goodsService = goodsService;
        this.writer = writer;
        this.remover = remover;
    }

    /**
     * Get goods page with parameters.
     *
     * @param request       the servlet container, which provide request information for HTTP servlets
     * @param model         holder for model attributes
     * @return              goods page
     */
    @GetMapping(value = "/goods")
    public String goodsPage(HttpServletRequest request,
                            Model model) {
        var config = PaginationConfig.config(request);
        var allGoodsUI = goodsService.findAll(PageRequest.of(config.page(), config.size()));

        model.addAttribute("goods", allGoodsUI);

        return "goods/goods";
    }

    /**
     * Get goods add page with parameters.
     *
     * @param model         holder for model attributes
     * @return              goods add page
     */
    @GetMapping("/admins/goods/add")
    public String getGoodsAddPage(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                                  Model model) {
        model.addAttribute("goods", new GoodsDTO());
        model.addAttribute("categories", categoryService.findAll(pageable));

        return "goods/goods-add";
    }

    /**
     * Add category to database.
     *
     * @param request       request with category parameters
     * @return              goods page
     */
    @PostMapping(value = "/admins/goods")
    public String postGoodsAdd(@Valid @ModelAttribute("goods") GoodsDTO request,
                               @RequestParam("goodsImage") MultipartFile file) {
        request.setImageName(file.getOriginalFilename());

        if (!file.isEmpty()) {
            var uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            writer.write(uploadPath, file);
            request.setImageName(file.getOriginalFilename());
        }

        goodsService.create(request);

        return "redirect:/goods";
    }

    /**
     * Get goods update page.
     *
     * @param pageable      pagination configuration
     * @param id            id of goods
     * @param model         holder for model attributes
     * @return              goods update page
     */
    @GetMapping("/goods/update/{id}")
    public String getGoodsUpdatePage(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                                     @PathVariable(value = "id") int id,
                                     Model model) {
        var goodsUI = goodsService.findById(id);
        var newGoodsDTO = new GoodsDTO(
                goodsUI.getId(),
                goodsUI.getName(),
                goodsUI.getCategory().getId(),
                goodsUI.getPrice(),
                goodsUI.getWeight(),
                goodsUI.getDescription(),
                goodsUI.getImageName()
        );

        model.addAttribute("goods", newGoodsDTO);
        model.addAttribute("categories", categoryService.findAll(pageable));

        return "goods/goods-update";
    }

    /**
     * Update goods in database.
     *
     * @param id            id of goods
     * @param request       request with category parameters
     * @return              goods page
     */
    @PutMapping("/admins/goods/{id}")
    public String updateGoods(@PathVariable(value = "id") int id,
                              @Valid @ModelAttribute("goods") GoodsDTO request,
                              @RequestParam("goodsImage") MultipartFile file) {
        request.setImageName(file.getOriginalFilename());
        var goodsUI = goodsService.findById(id);
        if (!request.getImageName().equals(goodsUI.getImageName())) {
            remover.remove(uploadPath, goodsUI);
            writer.write(uploadPath, file);
        }

        goodsService.update(id, request);

        return "redirect:/goods";
    }

    /**
     * Delete goods from database.
     *
     * @param id            id of goods
     * @return              goods page
     */
    @DeleteMapping("/admins/goods/{id}")
    public String deleteGoods(@PathVariable(value = "id") int id) {
        var goodsUI = goodsService.delete(id);
        remover.remove(uploadPath, goodsUI);

        return "redirect:/goods";
    }
}
