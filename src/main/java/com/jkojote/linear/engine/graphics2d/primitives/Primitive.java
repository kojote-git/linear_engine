package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.math.Mat4f;

public interface Primitive {

    float getScale();

    float getRotationAngle();

    Mat4f modelMatrix();
}
