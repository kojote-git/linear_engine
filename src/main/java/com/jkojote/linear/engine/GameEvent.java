package com.jkojote.linear.engine;

public interface GameEvent<T extends GameObject> {

    String getMessage();

    T getTarget();
}
