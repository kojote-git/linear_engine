package com.jkojote.engine.objects;

import com.jkojote.linear.engine.BaseGameObject;
import com.jkojote.linear.engine.Movable;
import com.jkojote.linear.engine.graphics2d.primitives.Rectangle;
import com.jkojote.linear.engine.math.Vec3f;

class MovableSquare extends BaseGameObject<MovableSquare>
implements Movable<MovableSquare> {

    private Vec3f velocity;

    private Rectangle rectangle;

    private int width;

    private int height;

    public MovableSquare(long id, int width) {
        super(id);
        this.width = width;
        this.rectangle = new Rectangle(position, width, width);
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
        for (Vec3f v : rectangle.vertices()) {
            v.add(velocity);
        }
    }

    public Rectangle getRectangle() {
        return rectangle;
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
