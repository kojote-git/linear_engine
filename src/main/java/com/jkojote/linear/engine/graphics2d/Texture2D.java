package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.Initializable;
import com.jkojote.linear.engine.Releasable;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
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

import org.lwjgl.opengl.GL11;

/**
 * <p>
 * Represents 2D texture that can be rendered by means of OpenGL.
 * GL context need to be set before the texture is created.
 * </p><br/>
 *
 * <p>
 * To draw a texture, first of all bind it to the rendering context by {@link #bind()} method.
 * Then specify mapping between local space coordinates and texture coordinates. This can be done in several ways:
 * <ul style="margin: 5px">
 *   <li>using approach with vertex buffer objects (VBO)</li>
 *   <li>
 *     using approach with calling {@link GL11#glTexCoord2f(float, float)}
 *     and {@link GL11#glTexCoord2f(float, float)}
 *   </li>
 * </ul>
 * The second approach is considered as deprecated.
 * </p><br/>
 *
 * <p>
 * After mapping, call one of the drawing methods, such as {@link GL11#glDrawArrays(int, int, int)} to finally draw
 * the texture. Next unbind the texture.
 * </p><br/>
 *
 * <p>
 * When the texture is not needed anymore, one should call {@link #release()} method to release the texture.
 * </p>
 * @see TexturedObject
 */
public final class Texture2D implements Releasable, Initializable, Renderable {

    private static final int BYTES_PER_PIXEL = 4;

    private int width, height;

    private int texture;

    private boolean released;

    private Mat4f model;

    private BufferedImage image;

    private int minFilter, magFilter;

    private boolean initialized;

    /**
     * Load texture from the file
     * @param file file of the texture
     * @throws IOException if no such file exists or some other i/o exception occurs
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
     * Converts the source image into a texture
     * @param image source image
     * @throws NoContextSetException if the OpenGL context hasn't been set before calling the constructor
     */
    public Texture2D(BufferedImage image) throws NoContextSetException {
        this.image = image;
        this.minFilter = GL_NEAREST;
        this.magFilter = GL_NEAREST;
        this.model = Mat4f.identity();
    }

    public Texture2D(BufferedImage image, int minFilter, int magFilter) {
        this.image = image;
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        this.model = Mat4f.identity();
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
     * @return minification filter used for this texture
     */
    public int getMinFilter() {
        return minFilter;
    }

    /**
     * @return magnification filter used for this texture
     */
    public int getMagFilter() {
        return magFilter;
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

    @Override
    public Mat4f modelMatrix() {
        return model;
    }

    @Override
    public int renderingMode() {
        return GL_QUADS;
    }

    @Override
    public void init() throws ResourceInitializationException {
        if (glfwGetCurrentContext() == NULL) {
            throw new ResourceInitializationException(new NoContextSetException());
        }
        if (initialized)
            return;
        initialized = true;
        texture = load(image, minFilter, magFilter);
        image = null;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }
}
