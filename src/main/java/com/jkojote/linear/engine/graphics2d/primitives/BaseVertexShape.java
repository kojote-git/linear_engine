package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.shared.Transformable;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

import java.util.List;

public abstract class BaseVertexShape implements VertexShape, Transformable {

    protected List<Vec3f> vertices;

    private Vec3f color;

    private Vec3f translation;

    private float scaleFactor;

    private float rotationAngle;

    private boolean updateMatrix = true;

    private Mat4f modelMatrix;

    protected BaseVertexShape(List<Vec3f> vertices) {
        this(vertices, new Vec3f(), 0, 1.0f, new Vec3f());
    }

    protected BaseVertexShape(List<Vec3f> vertices,
                              Vec3f translation,
                              float rotationAngle,
                              float scaleFactor) {
        this(vertices, translation, rotationAngle, scaleFactor, new Vec3f());
    }

    protected BaseVertexShape(List<Vec3f> vertices,
                              Vec3f translation,
                              float rotationAngle,
                              float scaleFactor,
                              Vec3f color) {
        this.vertices = vertices;
        this.translation = translation;
        this.rotationAngle = rotationAngle;
        this.scaleFactor = scaleFactor;
        this.color = color;
    }

    @Override
    public List<Vec3f> vertices() {
        return vertices;
    }

    @Override
    public Vec3f color() {
        return color;
    }

    @Override
    public void setColor(Vec3f color) {
        this.color = color;
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
        if (scaleFactor < 0.05f)
            scaleFactor = 0.05f;
        updateMatrix = true;
        this.scaleFactor = scaleFactor;
    }

    @Override
    public float getScaleFactor() {
        return scaleFactor;
    }

    @Override
    public final void setRotationAngle(float rotationAngle) {
        updateMatrix = true;
        this.rotationAngle = rotationAngle;
    }

    @Override
    public float getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public final Mat4f modelMatrix() {
        return transformationMatrix();
    }

    @Override
    public Mat4f transformationMatrix() {
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
