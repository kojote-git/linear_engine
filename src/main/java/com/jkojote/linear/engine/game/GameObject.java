package com.jkojote.linear.engine.game;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {
	private long id;
	private List<GameEventListener> listeners;

	protected GameObject(long id) {
		this.id = id;
		this.listeners = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public void addEventListener(GameEventListener eventListener) {
		listeners.add(eventListener);
	}

	public void removeEventListener(GameEventListener eventListener) {
		listeners.remove(eventListener);
	}

	protected void notifyListeners(GameEvent event) {
		for (GameEventListener listener : listeners) {
			listener.onEvent(event);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof GameObject) {
			GameObject gameObj = (GameObject) obj;
			return id == gameObj.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Long.hashCode(id);
	}
}
