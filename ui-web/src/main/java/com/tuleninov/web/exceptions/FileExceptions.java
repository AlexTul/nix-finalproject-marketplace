package com.tuleninov.web.exceptions;

public final class FileExceptions {

    public static RuntimeException noFileNameSelected() {
        return new RuntimeException("No file selected in product image form");
    }
}
