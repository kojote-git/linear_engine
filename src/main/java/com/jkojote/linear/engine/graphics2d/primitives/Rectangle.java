package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

public class Rectangle implements Primitive {

    private Vec3f position;

    private Vec3f color;

    private Mat4f modelMatrix;

    private boolean updateMatrix = true;

    private float scaleFactor;

    private float rotationAngle;

    private float width;

    private float height;

    public Rectangle() {
        this.position = new Vec3f();
        this.color = new Vec3f(1.0f, 1.0f, 1.0f);
    }

    public Vec3f getPosition() { return position; }

    public void setPosition(Vec3f position) { this.position = position; }

    public Rectangle withPosition(Vec3f position) {
        this.position = position;
        return this;
    }

    public Vec3f getColor() { return color; }

    public void setColor(Vec3f color) { this.color = color; }

    public Rectangle withColor(Vec3f color) {
        this.color = color;
        return this;
    }

    public float getWidth() { return width; }

    public void setWidth(float width) { this.width = width; }

    public Rectangle withWidth(float width) {
        this.width = width;
        return this;
    }

    public float getHeight() { return height; }

    public void setHeight(float height) { this.height = height; }

    public Rectangle withHeight(float height) {
        this.height = height;
        return this;
    }

    @Override
    public float getScale() {
        return scaleFactor;
    }

    @Override
    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(float rotationAngle) {
        updateMatrix = true;
        this.rotationAngle = rotationAngle;
    }

    public Rectangle withRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
        return this;
    }

    public void setScaleFactor(float scaleFactor) {
        updateMatrix = true;
        this.scaleFactor = scaleFactor;
    }

    public Rectangle withScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
        return this;
    }

    @Override
    public Mat4f modelMatrix() {
        if (!updateMatrix)
            return modelMatrix;
        return modelMatrix = Utils.modelMatrix(this);
    }
}
