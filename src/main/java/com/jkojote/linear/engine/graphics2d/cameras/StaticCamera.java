package com.jkojote.linear.engine.graphics2d.cameras;

import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.window.Window;

public class StaticCamera implements Camera {
    private Window window;

    public StaticCamera(Window window) {
        this.window = window;
    }

    @Override
    public Mat4f view() {
        return window.getProjectionMatrix();
    }

    @Override
    public Mat4f viewProjection() {
        return window.getProjectionMatrix();
    }
}
