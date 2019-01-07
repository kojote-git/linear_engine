package com.jkojote.linear.engine.graphics2d.primitives.filled;

import com.jkojote.linear.engine.graphics2d.primitives.BaseVertexShape;
import com.jkojote.linear.engine.math.Vec3f;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

public class Polygon extends BaseVertexShape {

    public Polygon(Vec3f translation, Vec3f color, Vec3f[] vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("polygon must have at least 3 vertices");
        super.vertices = Arrays.asList(vertices);
        super.color = color;
        super.translation = translation;
        super.scaleFactor = 1.0f;
    }

    public Polygon(Vec3f[] vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("polygon must have at least 3 vertices");
        super.vertices = Arrays.asList(vertices);
        super.translation = new Vec3f();
        super.color = new Vec3f();
        super.scaleFactor = 1.0f;
    }

    @Override
    public int renderingMode() {
        return GL_TRIANGLE_FAN;
    }
}
