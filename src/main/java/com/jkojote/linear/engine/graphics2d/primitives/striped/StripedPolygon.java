package com.jkojote.linear.engine.graphics2d.primitives.striped;

import com.jkojote.linear.engine.graphics2d.primitives.BaseVertexShape;
import com.jkojote.linear.engine.math.Vec3f;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11C.GL_LINE_LOOP;

public class StripedPolygon extends BaseVertexShape {

    public StripedPolygon(Vec3f translation, Vec3f color, Vec3f[] vertices) {
        super(Arrays.asList(vertices), translation, 0, 1.0f, color);
        if (vertices.length < 3) {
            throw new IllegalArgumentException("polygon must have at least 3 vertices");
        }
    }

    public StripedPolygon(Vec3f[] vertices) {
        super(Arrays.asList(vertices));
        if (vertices.length < 3)
            throw new IllegalArgumentException("polygon must have at least 3 vertices");
        super.vertices = Arrays.asList(vertices);
    }

    @Override
    public int renderingMode() {
        return GL_LINE_LOOP;
    }
}
