package com.jkojote.linear.engine.window;

public interface WindowStateEventListener<T extends WindowStateEvent> {

    Class<T> eventType();

    void onEvent(T event);

}
