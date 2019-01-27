package com.jkojote.linear.engine.graphics2d.text;

import com.jkojote.linear.engine.graphics2d.Renderable;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public final class PlainText implements Renderable {

    private CharSequence text;

    private FontMap font;

    private Vec3f color;

    private Mat4f modelMatrix;

    public PlainText(CharSequence text, FontMap font, Mat4f modelMatrix) {
        this.text = text;
        this.modelMatrix = modelMatrix;
        this.font = font;
        this.color = new Vec3f();
    }

    public void setColor(Vec3f color) {
        if (color == null)
            throw new NullPointerException("color must be not null");
        this.color = color;
    }

    public CharSequence getText() {
        return text;
    }

    public FontMap getFont() {
        return font;
    }

    public Vec3f getColor() {
        return color;
    }

    @Override
    public Mat4f modelMatrix() {
        return modelMatrix;
    }

    @Override
    public int renderingMode() {
        return GL_QUADS;
    }
}
