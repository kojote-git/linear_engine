package com.jkojote.linear.engine.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Interface that is used to read files.
 */
public interface FileReader {

    /**
     * Reads file and returns {@link ByteArrayOutputStream} which contains the data that are read.
     * No need to call {@link OutputStream#close()} after making use of the stream.
     * @param path path to the file
     * @return stream which contains the data that is read
     * @throws IOException if the file doesn't exists or some i/o error occurred
     */
    ByteArrayOutputStream asStream(String path) throws IOException;

    /**
     * @param file file that contains data to be read
     * @return stream which contains the data that is read
     * @throws IOException if the file doesn't exists or some i/o error occurred
     * @see FileReader#asStream(String)
     */
    ByteArrayOutputStream asStream(File file) throws IOException;

    /**
     * Reads file and returns the data read as byte array
     * @param path path to the file
     * @return byte array that contains the data read from the file
     * @throws IOException if the file doesn't exists or some i/o error occurred
     */
    byte[] asBytes(String path) throws IOException;

    /**
     * Reads file and returns the data read as byte array
     * @param file file that contains data to be read
     * @return byte array that contains the data read from the file
     * @throws IOException if the file doesn't exists or some i/o error occurred
     * @see FileReader#asBytes(String)
     */
    byte[] asBytes(File file) throws IOException;

    /**
     * Reads file and returns the data read as string
     * @param path path to the file
     * @return string that represents the file contents
     * @throws IOException if the file doesn't exists or some i/o error occurred
     */
    String asString(String path) throws IOException;

    /**
     * Reads file and returns the data read as string
     * @param file file that contains data to be read
     * @return string that represents the file contents
     * @throws IOException if the file doesn't exists or some i/o error occurred
     * @see FileReader#asString(File)
     */
    String asString(File file) throws IOException;

}
