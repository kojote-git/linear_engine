package com.jkojote.linear.engine.window;

public abstract class WindowStateEvent {

    private Window window;

    private String message;

    protected WindowStateEvent(Window window, String message) {
        this.window = window;
        this.message = message;
    }

    public Window getWindow() { return window; }

    public String getMessage() { return message; }
}
