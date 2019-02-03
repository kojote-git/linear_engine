package com.jkojote.linear.engine.window.events;

import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.window.Window;
import com.jkojote.linear.engine.window.WindowStateEvent;

public class WindowMatrixUpdatedEvent extends WindowStateEvent {

    private Mat4f matrix;

    public WindowMatrixUpdatedEvent(Window window, Mat4f matrix) {
        super(window, null);
        this.matrix = matrix;
    }

    public WindowMatrixUpdatedEvent(Window window, Mat4f mat4f, String message) {
        super(window, message);
        this.matrix = mat4f;
    }

    public Mat4f getMatrix() { return matrix; }
}
