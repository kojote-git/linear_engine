package com.jkojote.linear.engine.graphics2d.text;

import com.jkojote.linear.engine.graphics2d.Renderable;
import com.jkojote.linear.engine.graphics2d.Transformable;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class Text implements Transformable, Renderable {

    private StringBuilder text;

    private TrueTypeFont font;

    private Vec3f translation;

    private Mat4f matrix;

    private Vec3f color;

    private boolean updateMatrix;

    private float rotationAngle;

    private float scaleFactor;

    public Text(TrueTypeFont font) {
        this.text = new StringBuilder();
        this.font = font;
        this.translation = new Vec3f();
        this.color = new Vec3f();
        this.updateMatrix = true;
        this.scaleFactor = 1.0f;
    }

    public void setColor(Vec3f color) {
        this.color = color;
    }

    public void setFont(TrueTypeFont font) {
        this.font = font;
    }

    public TrueTypeFont getFont() {
        return font;
    }

    public Text append(char c) {
        text.append(c);
        return this;
    }

    public Text append(CharSequence c) {
        text.append(c);
        return this;
    }

    public Text append(CharSequence c, int start, int end) {
        text.append(c, start, end);
        return this;
    }

    public Text insert(int offset, CharSequence c) {
        text.insert(offset, c);
        return this;
    }

    public Text delete(int start, int end) {
        text.delete(start, end);
        return this;
    }

    public CharSequence getSequence() {
        return text;
    }

    @Override
    public Mat4f modelMatrix() {
        return transformationMatrix();
    }

    @Override
    public int renderingMode() {
        return GL_QUADS;
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
        Mat4f translation = Mat4f.translation(this.translation);
        Mat4f rotation = Mat4f.rotationZ(this.rotationAngle);
        Mat4f scale = Mat4f.scale(this.scaleFactor);
        matrix = translation.mult(rotation).mult(scale);
        updateMatrix = false;
        return matrix;
    }

    @Override
    public String toString() {
        return text.toString();
    }
}
