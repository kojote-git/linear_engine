package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.math.Mat4f;

/**
 * An object that represents the idea of the camera in games. All the objects in the world are rendered relatively to
 * the camera. The matrix returned by {@link #viewMatrix()} method is the view matrix used in MVP matrix and the purpose
 * of this matrix is to transform a model's vertices from world-space coordinates to view-space coordinates.
 */
public interface Camera {

    /**
     * The view matrix is used to transform a model’s vertices from world-space coordinates to view-space coordinates.
     * @return view matrix
     */
    Mat4f viewMatrix();
}
