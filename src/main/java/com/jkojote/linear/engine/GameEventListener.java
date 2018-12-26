package com.jkojote.linear.engine;

public interface GameEventListener<T extends GameObject> {

    void perform(GameEvent<? extends T> event);
}
