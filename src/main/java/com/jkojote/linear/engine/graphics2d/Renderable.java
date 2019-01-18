package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.math.Mat4f;


/**
 * An object that has necessary information for rendering.
 */
public interface Renderable {

    /**
     * @return model matrix for this object
     */
    Mat4f modelMatrix();

    /**
     * @return rendering mode
     */
    int renderingMode();
}
