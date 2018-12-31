package com.jkojote.linear.engine.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Reads file using standard io library
 */
public final class StandardIOFileReader implements FileReader {
    @Override
    public ByteArrayOutputStream asStream(String path) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[512];
        int read;
        try (FileInputStream fin = new FileInputStream(path)) {
            while ((read = fin.read(buffer)) > 0) {
                bout.write(buffer, 0, read);
            }
        }
        return bout;
    }

    @Override
    public ByteArrayOutputStream asStream(File file) throws IOException {
        return asStream(file.getAbsolutePath());
    }

    @Override
    public byte[] asBytes(String path) throws IOException {
        return asStream(path).toByteArray();
    }

    @Override
    public byte[] asBytes(File file) throws IOException {
        return asBytes(file.getAbsolutePath());
    }

    @Override
    public String asString(String path) throws IOException {
        return new String(asBytes(path));
    }

    @Override
    public String asString(File file) throws IOException {
        return asString(file.getAbsolutePath());
    }

}
