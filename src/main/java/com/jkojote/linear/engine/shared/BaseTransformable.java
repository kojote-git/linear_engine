package com.jkojote.linear.engine.shared;

import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

public abstract class BaseTransformable implements Transformable {
    private Vec3f translation;
    private Mat4f matrix;
    private float rotationAngle;
    private float scaleFactor;
    private boolean updateMatrix;

    protected BaseTransformable() {
        this(new Vec3f(), 0, 1.0f);
    }

    protected BaseTransformable(Vec3f translation, float rotationAngle, float scaleFactor) {
        this.translation = translation;
        this.rotationAngle = rotationAngle;
        this.scaleFactor = scaleFactor;
        this.updateMatrix = true;
    }

    @Override
    public void setTranslation(Vec3f translation) {
        updateMatrix = true;
        this.translation = translation;
    }

    @Override
    public Vec3f getTranslation() {
        return translation;
    }

    @Override
    public void setScaleFactor(float scaleFactor) {
        if (scaleFactor <= 0.0f)
            scaleFactor = 0.0f;
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
    public Mat4f transformationMatrix() {
        if (!updateMatrix)
            return matrix;
        Mat4f translation = Mat4f.translation(getTranslation());
        Mat4f rotation = Mat4f.rotationZ(getRotationAngle());
        Mat4f scale = Mat4f.scale(getScaleFactor());
        matrix = translation.mult(rotation).mult(scale);
        updateMatrix = false;
        return matrix;
    }
}
