package com.tuleninov.serverapi.service.goods;

import com.tuleninov.serverapi.model.goods.request.SaveGoodsRequest;
import com.tuleninov.serverapi.model.goods.response.GoodsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Interface CRUD for Goods.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface GoodsOperations {

    /**
     * Create the goods in the database.
     *
     * @param request request with goods parameters
     * @return the goods from the database in response format
     */
    GoodsResponse create(SaveGoodsRequest request);

    /**
     * Find all goods in the database in response format with pagination information.
     *
     * @param pageable abstract interface for pagination information
     * @return all goods from the database in response format
     */
    Page<GoodsResponse> list(Pageable pageable);

    /**
     * Find all goods by category id in the database in response format with pagination information.
     *
     * @param pageable abstract interface for pagination information
     * @param id       the id of the category
     * @return all goods from the database in response format
     */
    Page<GoodsResponse> findAllByCategoryId(Pageable pageable, int id);

    /**
     * Find the goods by id in the database in response format.
     *
     * @param id the id of the goods
     * @return the goods from database in response format
     */
    Optional<GoodsResponse> findById(int id);

    /**
     * Merge the goods in the database.
     *
     * @param id      the id of the goods
     * @param request the request with goods parameters
     */
    GoodsResponse mergeById(int id, SaveGoodsRequest request);

    /**
     * Delete the goods in the database.
     *
     * @param id the id of the goods
     * @return the goods from the database in response format
     */
    Optional<GoodsResponse> deleteById(int id);
}
