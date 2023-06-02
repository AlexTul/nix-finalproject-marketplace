package com.tuleninov.web.io.remove;

import com.tuleninov.web.model.goods.GoodsUI;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class for the removing goods image from local folder.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Component
public class FileRemover implements Remover {

    /**
     * Remove goods image from local folder.
     *
     * @param uploadPath    folder with goods image
     * @param response      response with removed goods from database
     */
    @Override
    public boolean remove(String uploadPath, GoodsUI response) {
        Path fileNameAndPath = Paths.get(uploadPath, response.getImageName());
        File file = new File(String.valueOf(fileNameAndPath));

        return file.delete();
    }
}
