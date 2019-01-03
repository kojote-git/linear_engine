package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class Rectangle implements Scalable, Rotatable {

    private List<Vec3f> vertices;

    private Vec3f color;

    private Mat4f model;

    private boolean updateModel;

    private float scaleFactor;

    private float rotationAngle;

    private float initialWidth, initialHeight;

    public Rectangle(Vec3f center, float width, float height) {
        initialWidth = width;
        initialHeight = height;
        updateModel = true;
        scaleFactor = 1.0f;
        vertices = new ArrayList<>(4);
        float x = center.getX(), y = center.getY(), z = center.getZ();
        vertices.add(new Vec3f(x - width / 2, y - height / 2, z));
        vertices.add(new Vec3f(x - width / 2, y + height / 2, z));
        vertices.add(new Vec3f(x + width / 2, y + height / 2, z));
        vertices.add(new Vec3f(x + width / 2, y - height / 2, z));
        color = new Vec3f();
    }

    public float getInitialWidth() { return initialWidth; }

    public float getInitialHeight() { return initialHeight; }

    @Override
    public void setRotationAngle(float rotationAngle) {
        updateModel = true;
        this.rotationAngle = rotationAngle;
    }

    @Override
    public float getRotationAngle() { return this.rotationAngle; }

    @Override
    public void setScaleFactor(float scaleFactor) {
        updateModel = true;
        this.scaleFactor = scaleFactor;
    }

    @Override
    public float getScaleFactor() { return scaleFactor; }

    @Override
    public List<Vec3f> vertices() {
        return vertices;
    }

    @Override
    public Vec3f color() {
        return color;
    }

    public void setColor(Vec3f color) {
        this.color = color;
    }

    @Override
    public Mat4f modelMatrix() {
        if (!updateModel)
            return model;
        Mat4f rotation = Utils.rotationMatrix(rotationAngle);
        Mat4f scale = Utils.scaleMatrix(scaleFactor);
        model = rotation.mult(scale);
        updateModel = false;
        return model;
    }

    @Override
    public int renderingMode() {
        return GL_QUADS;
    }
}
