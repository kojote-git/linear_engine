package com.jkojote.engine.math;

import com.jkojote.linear.engine.BaseGameObject;
import com.jkojote.linear.engine.Movable;
import com.jkojote.linear.engine.math.Vec3f;

class MovableSquare extends BaseGameObject<MovableSquare>
implements Movable<MovableSquare> {

    private Vec3f velocity;

    private int width;

    private int height;

    public MovableSquare(long id, int width, int height) {
        super(id);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void move() {
        position.add(velocity);
    }

    @Override
    public void setVelocity(Vec3f velocity) {
        this.velocity = velocity;
    }

    @Override
    public Vec3f getVelocity() {
        return velocity;
    }
}
