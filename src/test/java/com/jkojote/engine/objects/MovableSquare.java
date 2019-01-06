package com.jkojote.engine.objects;

import com.jkojote.linear.engine.BaseGameObject;
import com.jkojote.linear.engine.Movable;
import com.jkojote.linear.engine.graphics2d.primitives.filled.Rectangle;
import com.jkojote.linear.engine.math.Vec3f;

class MovableSquare extends BaseGameObject<MovableSquare>
implements Movable<MovableSquare> {

    private Vec3f velocity;

    private Rectangle rectangle;

    private int width;

    public MovableSquare(long id, int width) {
        super(id);
        this.width = width;
        this.rectangle = new Rectangle(position, width, width);
        this.rectangle.setTranslation(this.position);
    }

    public MovableSquare(Vec3f pos, int width)  {
        super(1);
        this.width = width;
        this.rectangle = new Rectangle(pos, width, width);
        this.position = pos;
        this.rectangle.setTranslation(this.position);
    }

    public int getWidth() {
        return width;
    }

    @Override
    public void move() {
        position.add(velocity);
        rectangle.setTranslation(position);
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
