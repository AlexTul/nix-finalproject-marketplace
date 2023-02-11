/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.controller.cart;

import com.nixsolution.alextuleninov.marketplace.marketplaceweb.controller.CartCount;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.goods.GoodsUI;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.goods.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for the cart.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
public class CartController {

    private final GoodsService goodsService;

    public CartController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    /**
     * Add goods to cart.
     *
     * @param id            id of goods
     * @return              the index page
     */
    @PostMapping("/view/{id}")
    public String addToCart(@PathVariable(value = "id") int id) {
        CartCount.cart.add(goodsService.findById(id));

        return "redirect:/";
    }

    /**
     * Get cart page with parameters.
     *
     * @param model         the holder for model attributes
     * @return              the cart page
     */
    @GetMapping("/cart")
    public String cartGet(Model model) {
        model.addAttribute("cartCount", CartCount.cart.size());
        model.addAttribute("total", CartCount.cart.stream().mapToDouble(GoodsUI::getPrice).sum());
        model.addAttribute("cart", CartCount.cart);

        return "cart/cart";
    }

    /**
     * Delete goods from cart.
     *
     * @param index         index of goods in array list
     * @return              the cart page
     */
    @DeleteMapping("/cart/{index}")
    public String deleteCart(@PathVariable(value = "index") int index) {
        CartCount.cart.remove(index);

        return "redirect:/cart";
    }

    /**
     * Get order page with the sum of the cost of all items in the cart.
     *
     * @param model         the holder for model attributes
     * @return              the order page
     */
    @GetMapping("/order")
    public String checkout(Model model) {
        model.addAttribute("total", CartCount.cart.stream().mapToDouble(GoodsUI::getPrice).sum());

        return "cart/order";
    }
}
