package com.jkojote.linear.engine.graphics2d.text;

import com.jkojote.linear.engine.graphics2d.BaseTransformable;
import com.jkojote.linear.engine.graphics2d.Renderable;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class Text extends BaseTransformable implements Renderable {

    private StringBuilder text;

    private FontMap fontMap;

    private Vec3f color;

    public Text(FontMap fontMap) {
        super(new Vec3f(), 0.0f, 1.0f);
        this.text = new StringBuilder();
        this.fontMap = fontMap;
        this.color = new Vec3f();
    }

    public void setColor(Vec3f color) {
        this.color = color;
    }

    public Vec3f getColor() { return color; }

    public void setFontMap(FontMap fontMap) {
        this.fontMap = fontMap;
    }

    public FontMap getFontMap() {
        return fontMap;
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

    public int length() {
        return text.length();
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
    public String toString() {
        return text.toString();
    }
}
