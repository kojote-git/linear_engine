package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.math.Mat4f;

public class StaticCamera implements Camera {

    private Mat4f matrix = Mat4f.identity();

    @Override
    public Mat4f viewMatrix() {
        return matrix;
    }
}
