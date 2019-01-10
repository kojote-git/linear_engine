package com.jkojote.linear.engine;

public interface InitializableResource {

    void init() throws ResourceInitializationException;

    boolean isInitialized();
}
