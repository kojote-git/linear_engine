package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.math.Vec3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_LINES;

public class Line extends BaseVertexShape {

    private float length;

    public Line(float length) {
        this.length = length;
        super.vertices = new ArrayList<>(2);
        vertices.add(new Vec3f(-length / 2, 0, 0));
        vertices.add(new Vec3f(length / 2, 0, 0));
        setColor(new Vec3f());
        setTranslation(new Vec3f());
        setScaleFactor(1.0f);
    }

    public Line(float length, Vec3f translation, Vec3f color) {
        this.length = length;
        super.vertices = new ArrayList<>(2);
        vertices.add(new Vec3f(-length / 2, 0, 0));
        vertices.add(new Vec3f(length / 2, 0, 0));
        setColor(color);
        setTranslation(translation);
        setScaleFactor(1.0f);
    }

    public Line(Vec3f v1, Vec3f v2) {
        float x = (v1.getX() + v2.getX()) / 2;
        float y = (v1.getY() + v2.getY()) / 2;
        super.vertices = new ArrayList<>(2);
        vertices.add(new Vec3f(v1.getX() - x, v1.getY() - y, v1.getZ()));
        vertices.add(new Vec3f(v2.getX() - x, v2.getY() - y, v2.getZ()));
        setColor(new Vec3f());
        setTranslation(new Vec3f());
        setScaleFactor(1.0f);
    }

    public float getLength() {
        return length;
    }

    @Override
    public int renderingMode() {
        return GL_LINES;
    }
}
