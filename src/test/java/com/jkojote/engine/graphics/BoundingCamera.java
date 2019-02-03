package com.jkojote.engine.graphics;

import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.shared.Transformable;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.window.Window;

public class BoundingCamera<T extends Transformable> implements Camera {

    private T obj;

    private Window window;

    public BoundingCamera(T obj, Window window) {
        this.obj = obj;
        this.window = window;
    }


    @Override
    public Mat4f view() {
        return Mat4f.translation(obj.getTranslation().copy().scalar(-1));
    }

    @Override
    public Mat4f viewProjection() {
        return window.getProjectionMatrix().mult(view());
    }
}
