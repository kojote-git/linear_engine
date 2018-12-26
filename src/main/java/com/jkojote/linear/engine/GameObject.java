package com.jkojote.linear.engine;

import com.jkojote.linear.engine.math.Vec3f;

import java.util.Set;

public interface GameObject<T extends GameObject> {

    long id();

    Vec3f position();

    Object getProperty(String name);

    void setProperty(String name, Object value);

    Set<PropertyPair> getAllProperties();

    void addEventListener(GameEventListener<T> listener);

    void removeEventListener(GameEventListener<T> listener);

    void notifyAllListeners(GameEvent<T> event);
}
