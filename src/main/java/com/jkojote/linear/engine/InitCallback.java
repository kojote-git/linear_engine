package com.jkojote.linear.engine;


/**
 * Callback that may be executed once a {@link Initializable} is initialized.
 */
public interface InitCallback {

    void perform(Initializable i);
}
