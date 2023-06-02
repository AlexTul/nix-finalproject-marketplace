package com.tuleninov.web.controller.cart;

import com.tuleninov.web.Routes;
import com.tuleninov.web.model.goods.GoodsUI;
import com.tuleninov.web.service.goods.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.tuleninov.web.AppConstants.*;

/**
 * Controller for the cart.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
@RequestMapping(Routes.WEB_CART)
public class CartController {

    private final GoodsService goodsService;

    public CartController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    /**
     * Add the goods to the cart.
     *
     * @param id  the id of the goods
     * @param req an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @return the index page
     */
    @GetMapping(value = "/{id}")
    public String addGoodsToCart(@PathVariable(value = "id") int id,
                                 HttpServletRequest req) {
        @SuppressWarnings("unchecked")
        var cart = (List<GoodsUI>) req.getSession().getAttribute(SCOPE_CART);
        if (cart == null) {
            cart = new ArrayList<>();
        }
        cart.add(goodsService.getGoodsById(id));

        req.getSession().setAttribute(SCOPE_CART, cart);
        req.getSession().setAttribute(SCOPE_CART_SIZE, cart.size());

        return "redirect:" + Routes.WEB_INDEX;
    }

    /**
     * Get cart page.
     *
     * @param req   an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @param model the holder for model attributes
     * @return the cart page
     */
    @GetMapping
    public String getCartPage(HttpServletRequest req,
                              Model model) {
        @SuppressWarnings("unchecked")
        var cart = (List<GoodsUI>) req.getSession().getAttribute(SCOPE_CART);
        if (cart == null) {
            model.addAttribute(SCOPE_CART_COUNT, 0);
            model.addAttribute(SCOPE_CART_TOTAL, 0);
        } else {
            model.addAttribute(SCOPE_CART_COUNT, cart.size());
            model.addAttribute(SCOPE_CART, cart);
            model.addAttribute(SCOPE_CART_TOTAL, cart.stream().mapToDouble(GoodsUI::getPrice).sum());
        }

        return "cart/cart";
    }

    /**
     * Delete the goods from the cart.
     *
     * @param index the index of goods in th cart
     * @param req   an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @return the cart page
     */
    @DeleteMapping(value = "/{index}")
    public String deleteGoodsByIndex(@PathVariable(value = "index") int index,
                                     HttpServletRequest req) {
        @SuppressWarnings("unchecked")
        var cart = (List<GoodsUI>) req.getSession().getAttribute(SCOPE_CART);
        cart.remove(index);

        req.getSession().setAttribute(SCOPE_CART_SIZE, cart.size());
        req.getSession().setAttribute(SCOPE_CART, cart);

        return "redirect:" + Routes.WEB_CART;
    }

    /**
     * Get order page with the sum of the cost of all items in the cart.
     *
     * @param req   an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @param model the holder for model attributes
     * @return the order page
     */
    @GetMapping(value = "/order")
    public String checkout(HttpServletRequest req,
                           Model model) {
        @SuppressWarnings("unchecked")
        var cart = (List<GoodsUI>) req.getSession().getAttribute(SCOPE_CART);

        model.addAttribute(SCOPE_CART_TOTAL, cart.stream().mapToDouble(GoodsUI::getPrice).sum());

        return "cart/cart-order";
    }
}
