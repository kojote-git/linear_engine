package com.jkojote.linear.engine.window;

import com.jkojote.linear.engine.Initializable;
import com.jkojote.linear.engine.Releasable;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.math.Mat4f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Encapsulates GLFW library functionality for creating windows
 */
public final class Window implements Releasable, Initializable {

    private long window;

    private int width;

    private int height;

    private String title;

    private boolean resizable;

    private boolean initialized;

    private boolean terminated;

    private KeyCallback keyCallback;

    private InitCallback initCallback;

    private RenderCallback renderCallback;

    private MouseButtonCallback mouseButtonCallback;

    private CursorCallback cursorCallback;

    private ScrollCallback scrollCallback;

    private TextInputCallback textInputCallback;

    private UpdateCallback updateCallback;

    private TerminationCallback windowClosedCallback;

    private boolean mouseEnabled;

    private Mat4f projectionMatrix;

    /**
     * Creates a new window instance; this method doesn't initialize it.
     * To do so, call {@code init()} method after you set up all callbacks.
     * @param title title to be displayed
     * @param width width of the window
     * @param height height of the window
     * @param resizable whether window is resizable or not
     * @param mouseEnabled whether window support mouse input or not
     */
    public Window(String title, int width, int height,
                  boolean resizable, boolean mouseEnabled) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.resizable = resizable;
        this.mouseEnabled = mouseEnabled;
        this.projectionMatrix = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0, 1);
    }

    /**
     * Sets the callback that is called once after initialization of this window
     * @param initCallback callback to be set
     * @return this window
     */
    public Window setInitCallback(InitCallback initCallback) {
        if (!initialized)
            this.initCallback = initCallback;
        return this;
    }

    /**
     * Sets the callback that is called every time the window is ready for rendering
     * @param renderCallback callback to be set
     * @return this window
     */
    public Window setRenderCallback(RenderCallback renderCallback) {
        if (!initialized)
            this.renderCallback = renderCallback;
        return this;
    }

    public Window setKeyCallback(KeyCallback keyCallback) {
        if (!initialized)
            this.keyCallback = keyCallback;
        return this;
    }

    public Window setMouseButtonCallback(MouseButtonCallback mouseCallback) {
        if (!initialized)
            this.mouseButtonCallback = mouseCallback;
        return this;
    }

    /**
     * Sets the callback that is called every time the window is updated
     * @param updateCallback to be set
     * @return this
     */
    public Window setUpdateCallback(UpdateCallback updateCallback) {
        if (!initialized)
            this.updateCallback = updateCallback;
        return this;
    }

    public Window setCursorCallback(CursorCallback cursorCallback) {
        if (!initialized)
            this.cursorCallback = cursorCallback;
        return this;
    }

    public Window setWindowClosedCallback(TerminationCallback windowClosedCallback) {
        if (!initialized)
            this.windowClosedCallback = windowClosedCallback;
        return this;
    }

    public Window setScrollCallback(ScrollCallback scrollCallback) {
        if (!initialized)
            this.scrollCallback = scrollCallback;
        return this;
    }

    public Window setTextInputCallback(TextInputCallback textInputCallback) {
        if (!initialized)
            this.textInputCallback = textInputCallback;
        return this;
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public String getTitle() { return title; }

    public boolean isTerminated() { return terminated; }

    public boolean isMouseEnabled() { return mouseEnabled; }

    public boolean isResizable() { return resizable; }

    public long getWindow() { return window; }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Initializes the window by initializing GLFW library, rendering context, and callbacks that were set
     * before the invocation of this method. After it's done with initializing a stuff related to GLFW and OpenGL, it
     * executes {@link InitCallback} if it has been set before initialization.
     * @throws ResourceInitializationException if the window is terminated or initialized or if GLFW library cannot be initialized
     */
    @Override
    public void init() throws ResourceInitializationException {
        if (terminated || initialized) {
            throw new ResourceInitializationException("window is terminated or initialized");
        }
        if (!glfwInit()) {
            throw new ResourceInitializationException("Cannot initialize GLFW library");
        }
        if (!resizable)
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        glfwShowWindow(window);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glClearColor(1.0f, 1.0f, 1.0f, 0);
        // enable textures and blend function
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        initCallbacks();
        if (initCallback != null)
            initCallback.perform();
        initialized = true;
    }

    /**
     * Initializes all callbacks that were set before the {@code init()} method was called
     */
    private void initCallbacks() {
        if (keyCallback != null) {
            glfwSetKeyCallback(window, (window, key, scanCode, action, mods) ->
                keyCallback.perform(key, action, mods)
            );
        }
        if (textInputCallback != null) {
            glfwSetCharCallback(window, (window, code) ->
                textInputCallback.perform(code)
            );
        }
        if (mouseEnabled) {
            if (cursorCallback != null) {
                glfwSetCursorPosCallback(window, (window, x, y) ->
                    cursorCallback.perform(x, y)
                );
            }
            if (mouseButtonCallback != null) {
                glfwSetMouseButtonCallback(window, (window, button, action, mods) ->
                    mouseButtonCallback.perform(button, action, mods)
                );
            }
            if (scrollCallback != null) {
                glfwSetScrollCallback(window, (window, xOffset, yOffset) ->
                    scrollCallback.perform(xOffset, yOffset)
                );
            }
        }
    }

    /**
     * Updates the window calling {@link UpdateCallback} and {@link RenderCallback}
     * that had been set before the window was initialized.
     * Does nothing if the window has been already terminated.
     */
    public void update() {
        glfwPollEvents();
        if (glfwWindowShouldClose(window)) {
            terminate();
            return;
        }
        if (updateCallback != null)
            updateCallback.perform();
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        if (renderCallback != null)
            renderCallback.perform();
        glfwSwapBuffers(window);
    }

    public Mat4f getProjectionMatrix() {
        return projectionMatrix;
    }

    /**
     * Terminates the window
     */
    public void terminate() {
        if (terminated)
            return;
        terminated = true;
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        if (windowClosedCallback != null)
            windowClosedCallback.perform();
    }

    @Override
    public void release() {
        terminate();
    }

    @Override
    public boolean isReleased() {
        return terminated;
    }
}
