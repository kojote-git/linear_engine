package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.math.Mat4f;

public interface Renderable {

    Mat4f modelMatrix();

    int renderingMode();
}
