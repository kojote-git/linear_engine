package com.jkojote.linear.engine;

/**
 * Resources (such as buffers, textures) that must be released after they aren't needed implement this interface
 */
public interface ReleasableResource {

    /**
     * Release resources
     */
    void release();

    boolean isReleased();
}
