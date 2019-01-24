package com.jkojote.engine.graphics.primitives;

import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.shared.Transformable;
import com.jkojote.linear.engine.math.Mat4f;

public class BoundingCamera<T extends Transformable> implements Camera {

    private T obj;

    public BoundingCamera(T obj) {
        this.obj = obj;
    }

    @Override
    public Mat4f viewMatrix() {
        return Mat4f.translation(obj.getTranslation().copy().scalar(-1));
    }
}
