package com.jkojote.engine.graphics;

import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.shared.Transformable;
import com.jkojote.linear.engine.window.Window;

public class TransformableCamera implements Transformable, Camera {

    private Window window;

    private Mat4f matrix;

    private float scaleFactor;

    private Vec3f translation;

    private float rotationAngle;

    private boolean updateMatrix;

    public TransformableCamera(Window window) {
        this.window = window;
        this.scaleFactor = 1.0f;
        this.rotationAngle = 0;
        this.translation = new Vec3f();
        this.updateMatrix = true;
    }

    @Override
    public Mat4f view() {
        return transformationMatrix();
    }

    @Override
    public Mat4f viewProjection() {
        if (!updateMatrix)
            return matrix;
        matrix = window.getProjectionMatrix().mult(transformationMatrix());
        updateMatrix = false;
        return matrix;
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
        updateMatrix = true;
        this.scaleFactor = scaleFactor;
    }

    @Override
    public float getScaleFactor() { return scaleFactor; }

    @Override
    public void setRotationAngle(float rotationAngle) {
        updateMatrix = true;
        this.rotationAngle = rotationAngle;
    }

    @Override
    public float getRotationAngle() { return rotationAngle; }

    @Override
    public Mat4f transformationMatrix() {
        Mat4f translate = Mat4f.translation(translation.copy().scalar(-1));
        Mat4f rotate = Mat4f.rotationZ(-rotationAngle);
        Mat4f scale = Mat4f.scale(scaleFactor);
        return scale.mult(translate).mult(rotate);
    }
}
