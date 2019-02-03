package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.math.Mat4f;

/**
 * An object that represents the idea of a camera in games. All objects in the world are rendered relatively to
 * a camera.
 */
public interface Camera {

    /**
     * The view matrix is used to transform a model’s vertices from world-space coordinates to view-space coordinates.
     * @return view matrix
     */
    Mat4f view();

    /**
     * This matrix combines view and projection matrices
     * @return view-projection matrix
     */
    Mat4f viewProjection();
}
