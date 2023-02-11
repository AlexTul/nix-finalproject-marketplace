/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.io.write;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for the writing goods image in local folder.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface Writer {

    /**
     * Write goods image in local folder.
     *
     * @param uploadPath    folder with goods image
     * @param file          file from request
     */
    void write(String uploadPath, MultipartFile file);

}
