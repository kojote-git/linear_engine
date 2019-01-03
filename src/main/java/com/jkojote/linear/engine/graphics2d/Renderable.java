package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.math.Mat4f;

public interface Renderable {

    /**
     * Returns model matrix for this object.
     * @return model matrix for this object
     */
    Mat4f modelMatrix();

    int renderingMode();
}
