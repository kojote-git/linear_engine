package com.jkojote.linear.engine.shared;

/**
 * <p>
 * If a class implements the <tt>Releasable</tt> interface, that means that the object of this class must be released
 * after it's not used anymore. For example, the object might hold some resources (such as open files or buffers)
 * that must be released to prevent memory leaks or due to any other specific reason.
 * <p/>
 */
public interface Releasable {

    /**
     * Release resources
     */
    void release();

    /**
     * @return if the object is released or not
     */
    boolean isReleased();
}
