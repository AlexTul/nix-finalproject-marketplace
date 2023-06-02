package com.tuleninov.web.feignclient;

import com.tuleninov.web.Routes;
import com.tuleninov.web.model.goods.request.SaveGoodsUIRequest;
import com.tuleninov.web.model.goods.response.GoodsUIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.tuleninov.web.config.security.SecurityConstantsUI.AUTH_CLAIM;

/**
 * Feign Client for the Goods.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@FeignClient(name = "GoodsController.class", url = "${services.server.api}")
public interface GoodsServiceFeignClient {

    /**
     * Create the goods in the database.
     *
     * @param token   token to access the corresponding endpoint
     * @param request the request with goods parameters
     * @return the goods from the database in response format
     */
    @PostMapping(
            value = Routes.API_GOODS,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    GoodsUIResponse create(@RequestHeader(AUTH_CLAIM) String token,
                           @RequestBody SaveGoodsUIRequest request);

    /**
     * Find all goods in the database in response format with pagination information.
     *
     * @param pageable abstract interface for pagination information
     * @return all goods from the database in response format
     */
    @GetMapping(
            value = Routes.API_GOODS,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Page<GoodsUIResponse> listGoods(Pageable pageable);

    /**
     * Find all goods by category id in the database in response format with pagination information.
     *
     * @param pageable abstract interface for pagination information
     * @param id       the id of the category
     * @return all goods from the database in response format
     */
    @GetMapping(
            value = Routes.API_GOODS + "/{id}/goods",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Page<GoodsUIResponse> listByCategoryId(Pageable pageable,
                                           @PathVariable int id);

    /**
     * Get the goods by id in the database in response format.
     *
     * @param id    the id of the goods
     * @return the goods from database in response format
     */
    @GetMapping(
            value = Routes.API_GOODS + "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    GoodsUIResponse getGoodsById(@PathVariable int id);

    /**
     * Merge the goods by id in the database.
     *
     * @param token   token to access the corresponding endpoint
     * @param id      the id of the goods
     * @param request the request with goods parameters
     */
    @PutMapping(
            value = Routes.API_GOODS + "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    GoodsUIResponse mergeGoodsById(@RequestHeader(AUTH_CLAIM) String token,
                                   @PathVariable int id,
                                   @RequestBody SaveGoodsUIRequest request);

    /**
     * Delete the goods by id in the database.
     *
     * @param token token to access the corresponding endpoint
     * @param id    the id of the goods
     * @return the goods from the database in response format
     */
    @DeleteMapping(
            value = Routes.API_GOODS + "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    GoodsUIResponse deleteGoodsById(@RequestHeader(AUTH_CLAIM) String token,
                                    @PathVariable int id);
}
