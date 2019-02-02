package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.math.Vec3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_LINES;

public class Line extends BaseVertexShape {

    private float length;

    public Line(float length) {
        super(new ArrayList<>(2));
        this.length = length;
        vertices = new ArrayList<>(2);
        vertices.add(new Vec3f(-length / 2, 0, 0));
        vertices.add(new Vec3f(length / 2, 0, 0));
    }

    public Line(float length, Vec3f translation, Vec3f color) {
        super(new ArrayList<>(2), translation, 0, 1.0f, color);
        this.length = length;
        vertices = new ArrayList<>(2);
        vertices.add(new Vec3f(-length / 2, 0, 0));
        vertices.add(new Vec3f(length / 2, 0, 0));
    }

    public Line(Vec3f v1, Vec3f v2) {
        super(new ArrayList<>(2));
        float x = (v1.x() + v2.x()) / 2;
        float y = (v1.y() + v2.y()) / 2;
        this.length = new Vec3f(v1.x() - v2.x(), v1.y() - v2.y(), v1.z() - v2.z()).len();
        vertices = new ArrayList<>(2);
        vertices.add(new Vec3f(v1.x() - x, v1.y() - y, v1.z()));
        vertices.add(new Vec3f(v2.x() - x, v2.y() - y, v2.z()));
    }

    public float getLength() {
        return length;
    }

    @Override
    public int renderingMode() {
        return GL_LINES;
    }
}
