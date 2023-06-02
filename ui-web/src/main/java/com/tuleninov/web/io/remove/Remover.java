package com.tuleninov.web.io.remove;

import com.tuleninov.web.model.goods.GoodsUI;

/**
 * Interface for the removing goods image from local folder.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface Remover {

    /**
     * Remove goods image from local folder.
     *
     * @param uploadPath    folder with goods image
     * @param response      response with removed goods from database
     */
    boolean remove(String uploadPath, GoodsUI response);

}
