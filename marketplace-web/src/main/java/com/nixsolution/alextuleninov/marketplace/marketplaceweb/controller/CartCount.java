/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.controller;

import com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.goods.GoodsUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Cart counter.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class CartCount {

    public static List<GoodsUI> cart;

    static {
        cart = new ArrayList<>();
    }
}
