package com.tuleninov.web.service.goods;

import com.tuleninov.web.feignclient.GoodsServiceFeignClient;
import com.tuleninov.web.model.goods.request.SaveGoodsUIRequest;
import com.tuleninov.web.model.goods.GoodsUI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service class for Goods.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class GoodsService {

    private final GoodsServiceFeignClient goodsServiceFeignClient;

    public GoodsService(GoodsServiceFeignClient goodsServiceFeignClient) {
        this.goodsServiceFeignClient = goodsServiceFeignClient;
    }

    /**
     * Create the goods in the database.
     *
     * @param token   token to access the corresponding endpoint
     * @param request the request with goods parameters
     * @return the goods from the database in response format
     */
    public GoodsUI create(String token, SaveGoodsUIRequest request) {
        return GoodsUI.fromGoodsResponse(
                goodsServiceFeignClient.create(token, request));
    }

    /**
     * Find all goods from database in response format with pagination information.
     *
     * @param pageable abstract interface for pagination information
     * @return all goods from the database in response format
     */
    public Page<GoodsUI> listGoods(Pageable pageable) {
        return goodsServiceFeignClient.listGoods(pageable)
                .map(GoodsUI::fromGoodsResponse);
    }

    /**
     * Find all goods by category id in the database in response format with pagination information.
     *
     * @param pageable abstract interface for pagination information
     * @param id       the id of the category
     * @return all goods from the database in response format
     */
    public Page<GoodsUI> listByCategoryId(Pageable pageable, int id) {
        return goodsServiceFeignClient.listByCategoryId(pageable, id)
                .map(GoodsUI::fromGoodsResponse);
    }

    /**
     * Get the goods by id in the database in response format.
     *
     * @param id    the id of the goods
     * @return the goods from database in response format
     */
    public GoodsUI getGoodsById(int id) {
        return GoodsUI.fromGoodsResponse(
                goodsServiceFeignClient.getGoodsById(id));
    }

    /**
     * Merge the goods by id in the database.
     *
     * @param token   token to access the corresponding endpoint
     * @param id      the id of the goods
     * @param request the request with goods parameters
     */
    public GoodsUI mergeGoodsById(String token, int id, SaveGoodsUIRequest request) {
        return GoodsUI.fromGoodsResponse(
                goodsServiceFeignClient.mergeGoodsById(token, id, request));
    }

    /**
     * Delete the goods by id in the database.
     *
     * @param token token to access the corresponding endpoint
     * @param id    the id of the goods
     * @return the goods from the database in response format
     */
    public GoodsUI deleteGoodsById(String token, int id) {
        return GoodsUI.fromGoodsResponse(
                goodsServiceFeignClient.deleteGoodsById(token, id));
    }
}
