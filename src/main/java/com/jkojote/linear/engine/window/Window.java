package com.jkojote.linear.engine.window;

import com.jkojote.linear.engine.shared.InitCallback;
import com.jkojote.linear.engine.shared.Initializable;
import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.shared.ResourceInitializationException;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.window.events.WindowMatrixUpdatedEvent;
import com.jkojote.linear.engine.window.events.WindowResizedEvent;
import org.lwjgl.opengl.GL;

import java.util.HashSet;
import java.util.Set;

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

    private MouseButtonCallback mouseButtonCallback;

    private CursorCallback cursorCallback;

    private ScrollCallback scrollCallback;

    private TextInputCallback textInputCallback;

    private TerminationCallback windowClosedCallback;

    private boolean mouseEnabled;

    private Mat4f projectionMatrix;

    private Set<WindowStateEventListener<?>> stateListeners;

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
        this.stateListeners = new HashSet<>();
        this.projectionMatrix = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0, 1);
    }

    /**
     * Sets the callback that is called once after initialization of this window
     * @param initCallback callback to be set
     * @return this window
     */
    public Window setInitCallback(InitCallback initCallback) {
        if (initCallback == null)
            throw new NullPointerException("initCallback must not be null");
        this.initCallback = initCallback;
        return this;
    }

    public Window setKeyCallback(KeyCallback keyCallback) {
        if (keyCallback == null)
            throw new NullPointerException("keyCallback must not be null");
        this.keyCallback = keyCallback;
        return this;
    }

    public Window setMouseButtonCallback(MouseButtonCallback mouseCallback) {
        if (mouseCallback == null)
            throw new NullPointerException("mouseButtonCallback must not be null");
        this.mouseButtonCallback = mouseCallback;
        return this;
    }

    public Window setCursorCallback(CursorCallback cursorCallback) {
        if (cursorCallback == null)
            throw new NullPointerException("cursorCallback must not be null");
        this.cursorCallback = cursorCallback;
        return this;
    }

    public Window setTerminationCallback(TerminationCallback windowClosedCallback) {
        if (windowClosedCallback == null)
            throw new NullPointerException("terminationCallback must not be null");
        this.windowClosedCallback = windowClosedCallback;
        return this;
    }

    public Window setScrollCallback(ScrollCallback scrollCallback) {
        if (scrollCallback == null)
            throw new NullPointerException("scrollCallback must not be null");
        this.scrollCallback = scrollCallback;
        return this;
    }

    public Window setTextInputCallback(TextInputCallback textInputCallback) {
        if (textInputCallback == null)
            throw new NullPointerException("textInputCallback must not be null");
        this.textInputCallback = textInputCallback;
        return this;
    }

    public void addStateListener(WindowStateEventListener<?> listener) {
        if (listener == null)
            return;
        stateListeners.add(listener);
    }

    public void removeStateListener(WindowStateEventListener<?> listener) {
        stateListeners.remove(listener);
    }

    private<T extends WindowStateEvent> void notifyListeners(T event) {
        for (WindowStateEventListener listener : stateListeners) {
            if (listener.eventType().equals(event.getClass())) {
                listener.onEvent(event);
            }
        }
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
        glViewport(0, 0, width, height);
        glClearColor(1.0f, 1.0f, 1.0f, 0);
        // enable textures and blend function
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        initCallbacks();
        if (initCallback != null)
            initCallback.perform(this);
        initialized = true;
    }

    /**
     * Initializes all callbacks that were set before the {@code init()} method was called
     */
    private void initCallbacks() {
        if (resizable) {
            glfwSetWindowSizeCallback(window, (window, width, height) -> {
                this.width = width;
                this.height = height;
                glViewport(0, 0, width, height);
                notifyListeners(new WindowResizedEvent(this, width, height));
                updateMatrix();
                notifyListeners(new WindowMatrixUpdatedEvent(this, projectionMatrix));

            });
        }
        if (keyCallback != null) {
            glfwSetKeyCallback(window, (window, key, scanCode, action, mods) ->
                keyCallback.perform(this, key, action, mods)
            );
        }
        if (textInputCallback != null) {
            glfwSetCharCallback(window, (window, code) ->
                textInputCallback.perform(this, code)
            );
        }
        if (mouseEnabled) {
            if (cursorCallback != null) {
                glfwSetCursorPosCallback(window, (window, x, y) ->
                    cursorCallback.perform(this, x, y)
                );
            }
            if (mouseButtonCallback != null) {
                glfwSetMouseButtonCallback(window, (window, button, action, mods) ->
                    mouseButtonCallback.perform(this, button, action, mods)
                );
            }
            if (scrollCallback != null) {
                glfwSetScrollCallback(window, (window, xOffset, yOffset) ->
                    scrollCallback.perform(this, xOffset, yOffset)
                );
            }
        }
    }

    private void updateMatrix() {
        this.projectionMatrix = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, -1, 1);
    }

    public void pollEvents() {
        if (terminated)
            return;
        glfwPollEvents();
        if (glfwWindowShouldClose(window)) {
            terminate();
        }
    }

    public void clear() {
        if (terminated)
            return;
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void update() {
        if (terminated)
            return;
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
            windowClosedCallback.perform(this);
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
