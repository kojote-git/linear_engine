package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.shared.Releasable;

/**
 * This interface represents an object that contains vertex array object (VAO) inside.
 * The way that the VAO is created and stored is the implementation's direct responsibility.
 * The point is to allow caching of the VAO to reuse it for rendering purposes.
 * It can slightly improve performance but it is also must be considered
 * that <b>the object must be released if it's not in use</b> to prevent memory leaks.
 */
public interface VaoObject extends Renderable, Releasable {

    /**
     * Binds the vao to the rendering context
     */
    void bind();

    /**
     * Unbinds the vao from the rendering context
     */
    void unbind();

    /**
     * Enables all of the vertex attributes of the VAO that are needed to properly update the object
     */
    void enableAttributes();

    /**
     * Disables all of the vertex attributes of the VAO that are needed to properly update the object
     */
    void disableAttributes();

    /**
     * <a href="http://docs.gl/es3/glDrawArrays">Get detailed information</a>
     * @return the number of vertices after to transfer to the GL
     */
    int drawCount();
}
