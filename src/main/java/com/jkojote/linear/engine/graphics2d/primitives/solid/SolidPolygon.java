package com.jkojote.linear.engine.graphics2d.primitives.solid;

import com.jkojote.linear.engine.graphics2d.primitives.BaseVertexShape;
import com.jkojote.linear.engine.math.Vec3f;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

public class SolidPolygon extends BaseVertexShape {

    public SolidPolygon(Vec3f translation, Vec3f color, Vec3f[] vertices) {
        super(Arrays.asList(vertices), translation, 0, 1.0f, color);
        if (vertices.length < 3) {
            throw new IllegalArgumentException("polygon must have at least 3 vertices");
        }
    }

    public SolidPolygon(Vec3f[] vertices) {
        super(Arrays.asList(vertices));
        if (vertices.length < 3)
            throw new IllegalArgumentException("polygon must have at least 3 vertices");
        super.vertices = Arrays.asList(vertices);
    }

    @Override
    public int renderingMode() {
        return GL_TRIANGLE_FAN;
    }
}
