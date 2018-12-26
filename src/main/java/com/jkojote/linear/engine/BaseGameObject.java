package com.jkojote.linear.engine;

import com.jkojote.linear.engine.math.Vec3f;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseGameObject<T extends GameObject> implements GameObject<T> {

    private long id;

    private Map<String, Object> properties;

    protected Vec3f position;

    private Set<GameEventListener<T>> gameEventListeners;

    protected BaseGameObject(long id) {
        this.id = id;
        this.properties = new HashMap<>();
        this.gameEventListeners = new HashSet<>();
        this.position = new Vec3f();
    }

    protected BaseGameObject(long id, Vec3f position, Map<String, Object> properties) {
        this.id = id;
        this.position = position;
        this.properties = properties;
        this.gameEventListeners = new HashSet<>();
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public Vec3f position() {
        return position;
    }

    @Override
    public Object getProperty(String name) {
        return properties.get(name);
    }

    @Override
    public void setProperty(String name, Object value) {
        properties.put(name, value);
    }

    @Override
    public Set<PropertyPair> getAllProperties() {
        return properties.entrySet().stream()
                .map((e) -> new PropertyPair(e.getKey(), e.getValue()))
                .collect(Collectors.toSet());
    }

    @Override
    public void addEventListener(GameEventListener<T> listener) {
        gameEventListeners.add(listener);
    }

    @Override
    public void removeEventListener(GameEventListener<T> listener) {
        gameEventListeners.remove(listener);
    }

    @Override
    public void notifyAllListeners(GameEvent<T> event) {
        for (GameEventListener<T> listener : gameEventListeners) {
            listener.perform(event);
        }
    }
}
