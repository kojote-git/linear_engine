package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.Releasable;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Represents 2D texture that can be rendered by means of OpenGL.
 * GL context need to be set before the texture is created.
 */
public final class Texture2D implements Releasable {

    private static final int BYTES_PER_PIXEL = 4;

    private int width, height;

    private int texture;

    private boolean released;


    /**
     * Lad texture from the file represented by given file
     * @param file file of the texture
     * @throws IOException if no such file exist by given path or some other i/o exception occurs
     * @throws NoContextSetException if the OpenGL context hasn't been set before calling the constructor
     */
    public Texture2D(File file) throws IOException, NoContextSetException {
        this(ImageIO.read(file));
    }

    public Texture2D(File file, int minFilter, int magFilter) throws IOException, NoContextSetException {
        this(ImageIO.read(file), minFilter, magFilter);
    }

    /**
     * Load texture from the file located by given path
     * @param path path where is the texture located
     * @throws IOException if no such file exist by given path or some other i/o exception occurs
     * @throws NoContextSetException if the OpenGL context hasn't been set before calling the constructor
     */
    public Texture2D(String path) throws IOException, NoContextSetException {
        this(new File(path));
    }

    public Texture2D(String path, int minFilter, int magFilter) throws IOException, NoContextSetException {
        this(new File(path), minFilter, magFilter);
    }

    /**
     * Load texture from input stream
     * @param in input stream from which texture is loaded
     * @throws IOException if no such file exist by given path or some other i/o exception occurs
     * @throws NoContextSetException if the OpenGL context hasn't been set before calling the constructor
     */
    public Texture2D(InputStream in) throws IOException, NoContextSetException {
        this(ImageIO.read(in));
    }

    public Texture2D(InputStream in, int minFilter, int magFilter) throws IOException, NoContextSetException {
        this(ImageIO.read(in), minFilter, magFilter);
    }

    /**
     * Converts given image so that it can be rendered by means of OpenGL
     * @param image source image
     * @throws NoContextSetException if the OpenGL context hasn't been set before calling the constructor
     */
    public Texture2D(BufferedImage image) throws NoContextSetException {
        if (glfwGetCurrentContext() == NULL)
            throw new NoContextSetException();
        this.texture = load(image, GL_NEAREST, GL_NEAREST);
    }

    public Texture2D(BufferedImage image, int minFilter, int magFilter) {
        if (glfwGetCurrentContext() == NULL)
            throw new NoContextSetException();
        this.texture = load(image, minFilter, magFilter);
    }

    private int load(BufferedImage image, int minFilter, int magFilter) {
        width = image.getWidth();
        height = image.getHeight();
        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * BYTES_PER_PIXEL);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixels[y * width + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));    // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));     // Green component
                buffer.put((byte) (pixel & 0xFF));            // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }
        buffer.flip();
        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glBindTexture(GL_TEXTURE_2D, 0);
        return id;
    }

    /**
     * @return width of the texture
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return height of the texture
     */
    public int getHeight() {
        return height;
    }

    /**
     * Binds this texture to rendering context
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    /**
     * Unbinds this texture from rendering context
     */
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public void release() {
        released = true;
        glDeleteTextures(texture);
    }

    @Override
    public boolean isReleased() {
        return released;
    }
}
