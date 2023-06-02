package com.tuleninov.web.io.write;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Interface for the writing goods image in local folder.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Component
public class FileWriter implements Writer {

    /**
     * Write goods image in local folder.
     *
     * @param uploadPath    folder with goods image
     * @param file          file from request
     */
    @Override
    public void write(String uploadPath, MultipartFile file) {
        String imageName;
        if (!file.isEmpty()) {
            imageName = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadPath, imageName);
            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
