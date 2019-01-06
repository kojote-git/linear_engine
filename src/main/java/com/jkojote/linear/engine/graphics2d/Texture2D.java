package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.ReleasableResource;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;

/**
 * Represents 2D texture that can be loaded from a file.
 * GL context need to be set before the texture is loaded.
 */
public final class Texture2D implements ReleasableResource {

    private static final int BYTES_PER_PIXEL = 4;

    private int width, height;

    private String path;

    private int texture;

    private boolean initialized;

    private boolean released;

    public Texture2D(String path) {
        this.path = path;
    }

    private int load(String path) {
        int[] pixels;
        try (FileInputStream fin = new FileInputStream(path)) {
            BufferedImage image = ImageIO.read(fin);
            width = image.getWidth();
            height = image.getHeight();
            pixels = image.getRGB(0, 0, width, height, null, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * BYTES_PER_PIXEL);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixels[y * width + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }
        buffer.flip();
        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glBindTexture(GL_TEXTURE_2D, 0);
        return id;
    }

    public void load() {
        if (initialized)
            return;
        initialized = true;
        texture = load(path);
    }

    public String getPath() {
        return path;
    }

    public int getWidth() {
        if (!initialized)
            return -1;
        return width;
    }

    public int getHeight() {
        if (!initialized)
            return -1;
        return height;
    }

    public void bind() {
        if (initialized)
            glBindTexture(GL_TEXTURE_2D, texture);
    }

    public void unbind() {
        if (initialized)
            glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public void release() {
        glDeleteTextures(texture);
    }

    @Override
    public boolean isReleased() {
        return released;
    }
}
