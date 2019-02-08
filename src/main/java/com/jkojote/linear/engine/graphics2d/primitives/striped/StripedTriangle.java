package com.jkojote.linear.engine.graphics2d.primitives.striped;

import com.jkojote.linear.engine.graphics2d.primitives.BaseVertexShape;
import com.jkojote.linear.engine.math.Vec3f;

import java.util.ArrayList;

import static org.lwjgl.opengles.GLES20.GL_LINE_LOOP;
import static org.lwjgl.opengles.GLES20.GL_TRIANGLE_STRIP;

public class StripedTriangle extends BaseVertexShape {

    public StripedTriangle(Vec3f translation, Vec3f v1, Vec3f v2, Vec3f v3) {
        super(new ArrayList<>(3), translation, 0, 1.0f);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
    }

    public StripedTriangle(Vec3f v1, Vec3f v2, Vec3f v3) {
        super(new ArrayList<>(3));
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
    }

    @Override
    public int renderingMode() {
        return GL_LINE_LOOP;
    }
}
