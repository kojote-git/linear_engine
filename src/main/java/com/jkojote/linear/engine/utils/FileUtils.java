package com.jkojote.linear.engine.utils;

import java.io.File;
import java.io.IOException;

public final class FileUtils {

    private static final FileReader FILE_READER = new StandardIOFileReader();

    private FileUtils() { throw new AssertionError("no constructor for utility class!"); }

    public static String readAsString(String path) throws IOException {
        return FILE_READER.asString(path);
    }

    public static String readAsString(File file) throws IOException {
        return FILE_READER.asString(file);
    }
}
