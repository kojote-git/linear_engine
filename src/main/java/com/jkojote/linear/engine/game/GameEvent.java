package com.jkojote.linear.engine.game;

public interface GameEvent<T> {

	T getTarget();

	String getMessage();
}
