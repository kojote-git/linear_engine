package com.jkojote.linear.engine.window.events;

import com.jkojote.linear.engine.window.Window;
import com.jkojote.linear.engine.window.WindowStateEvent;

public class WindowResizedEvent extends WindowStateEvent {

    private int width, height;

    public WindowResizedEvent(Window window, int width, int height) {
        super(window, null);
        this.width = width;
        this.height = height;
    }

    public WindowResizedEvent(Window window, int width, int height, String message) {
        super(window, message);
        this.width = width;
        this.height = height;
    }

    public int getHeight() { return height; }

    public int getWidth() { return width; }
}
