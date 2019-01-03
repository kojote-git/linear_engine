package com.jkojote.linear.engine;

import com.jkojote.linear.engine.math.Vec3f;

public interface Movable<T extends GameObject> extends GameObject<T> {

    void move();

    void setVelocity(Vec3f velocity);

    Vec3f getVelocity();

}
