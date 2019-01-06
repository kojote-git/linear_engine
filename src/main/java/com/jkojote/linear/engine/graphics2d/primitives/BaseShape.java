package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

public abstract class BaseShape implements Shape {

    protected Vec3f color;

    protected Vec3f translation;

    protected float rotationAngle;

    protected float scaleFactor;

    private Mat4f modelMatrix;

    private boolean updateMatrix = true;

    @Override
    public final Vec3f color() {
        return color;
    }

    @Override
    public final void setColor(Vec3f color) {
        if (color == null)
            throw new NullPointerException("color must not be null");
        this.color = color;
    }

    @Override
    public final void setTranslation(Vec3f translation) {
        if (translation == null)
            throw new NullPointerException("translation must not be null");
        updateMatrix = true;
        this.translation = translation;
    }

    @Override
    public final Vec3f getTranslation() {
        return translation;
    }

    @Override
    public final void setScaleFactor(float scaleFactor) {
        if (scaleFactor <= 0.05f)
            scaleFactor = 0.05f;
        updateMatrix = true;
        this.scaleFactor = scaleFactor;
    }

    @Override
    public final float getScaleFactor() {
        return scaleFactor;
    }

    @Override
    public final void setRotationAngle(float rotationAngle) {
        updateMatrix = true;
        this.rotationAngle = rotationAngle;
    }

    @Override
    public final float getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public final Mat4f modelMatrix() {
        if (!updateMatrix)
            return modelMatrix;
        Mat4f translate = Mat4f.translation(translation);
        Mat4f rotate = Mat4f.rotationZ(rotationAngle);
        Mat4f scale = Mat4f.scale(scaleFactor);
        modelMatrix = translate.mult(rotate).mult(scale);
        updateMatrix = false;
        return modelMatrix;
    }
}
