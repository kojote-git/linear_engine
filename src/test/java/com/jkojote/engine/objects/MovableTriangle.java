package com.jkojote.engine.objects;

import com.jkojote.linear.engine.BaseGameObject;
import com.jkojote.linear.engine.Movable;
import com.jkojote.linear.engine.graphics2d.primitives.filled.Triangle;
import com.jkojote.linear.engine.math.Vec3f;

public class MovableTriangle extends BaseGameObject<MovableTriangle>
implements Movable<MovableTriangle> {

    private Triangle triangle;

    private Vec3f velocity;

    public MovableTriangle(Vec3f center, Vec3f v1, Vec3f v2, Vec3f v3) {
        super(1);
        triangle = new Triangle(center, v1, v2, v3);
        velocity = new Vec3f();
        position = center;
    }

    public MovableTriangle(Vec3f v1, Vec3f v2, Vec3f v3) {
        super(1);
        triangle = new Triangle(v1, v2, v3);
        velocity = new Vec3f();
    }

    @Override
    public void move() {
        position.add(velocity);
        triangle.setTranslation(position);
    }

    void setColor(Vec3f color) {
        triangle.setColor(color);
    }

    @Override
    public void setVelocity(Vec3f velocity) {
        this.velocity = velocity;
    }

    public Triangle getTriangle() {
        return triangle;
    }

    @Override
    public Vec3f getVelocity() {
        return velocity;
    }
}
