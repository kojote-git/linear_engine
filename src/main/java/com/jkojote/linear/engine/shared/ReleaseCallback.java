package com.jkojote.linear.engine.shared;

/**
 * Callback that may be executed once a {@link Releasable} is released.
 */
public interface ReleaseCallback {

    void perform(Releasable r);
}
