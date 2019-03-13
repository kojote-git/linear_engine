package com.jkojote.linear.engine.game;

public interface GameEventListener<T extends GameEvent> {
	void onEvent(GameEvent event);
}
