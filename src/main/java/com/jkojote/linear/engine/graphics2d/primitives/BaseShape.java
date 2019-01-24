package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.shared.Transformable;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

public abstract class BaseShape implements Shape, Transformable {

    protected Vec3f color;

    protected Vec3f translation;

    protected float rotationAngle;

    protected float scaleFactor;

    private Mat4f transformationMatrix;

    private boolean updateMatrix = true;

    @Override
    public Vec3f color() {
        return color;
    }

    @Override
    public void setColor(Vec3f color) {
        if (color == null)
            throw new NullPointerException("color must not be null");
        this.color = color;
    }

    @Override
    public void setTranslation(Vec3f translation) {
        if (translation == null)
            throw new NullPointerException("translation must not be null");
        updateMatrix = true;
        this.translation = translation;
    }

    @Override
    public Vec3f getTranslation() {
        return translation;
    }

    @Override
    public void setScaleFactor(float scaleFactor) {
        if (scaleFactor <= 0.05f)
            scaleFactor = 0.05f;
        updateMatrix = true;
        this.scaleFactor = scaleFactor;
    }

    @Override
    public float getScaleFactor() {
        return scaleFactor;
    }

    @Override
    public void setRotationAngle(float rotationAngle) {
        updateMatrix = true;
        this.rotationAngle = rotationAngle;
    }

    @Override
    public float getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public Mat4f modelMatrix() {
        return transformationMatrix();
    }

    @Override
    public Mat4f transformationMatrix() {
        if (!updateMatrix)
            return transformationMatrix;
        Mat4f translate = Mat4f.translation(translation);
        Mat4f rotate = Mat4f.rotationZ(rotationAngle);
        Mat4f scale = Mat4f.scale(scaleFactor);
        transformationMatrix = translate.mult(rotate).mult(scale);
        updateMatrix = false;
        return transformationMatrix;
    }
}
