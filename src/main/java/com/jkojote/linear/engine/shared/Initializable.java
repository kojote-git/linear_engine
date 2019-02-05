package com.jkojote.linear.engine.shared;

/**
 * <p>
 * If a class implements the <tt>Initializable</tt> interface, that means that the object has to be initialized
 * by {@link #init()} method due to the specific reason. One of them is to allow initializing object not immediately after
 * calling one of the constructor but postpone initialization until the certain moment in the future.
 * </p>
 */
public interface Initializable {

    /**
     * Initializes this object
     * @throws ResourceInitializationException if something goes wrong while initializing the object
     */
    void init() throws ResourceInitializationException;

    boolean isInitialized();
}
