package com.jkojote.linear.engine.graphics2d.primitives.solid;

import com.jkojote.linear.engine.graphics2d.primitives.BaseVertexShape;
import com.jkojote.linear.engine.math.Vec3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class SolidTriangle extends BaseVertexShape {

    /**
     * Creates a new triangle, which has given vertices.
     * These vertices are aligned in the object space,
     * thus to properly load a triangle consider this fact.
     * For example, the vertices may be specified as these: <br/>
     * {@code (-0.5f, -0.5f, 0.0f)} <br/>
     * {@code ( 0.5f, -0.5f, 0.0f)} <br/>
     * {@code ( 0.0f, 0.5f, 0.0f)} <br/>
     * By these vertices the triangle with the origin in {@code (0, 0, 0)} will be created
     * that allows to properly rotate this object.
     * @param v1 first vertex
     * @param v2 second vertex
     * @param v3 third vertex
     * @param translation
     */
    public SolidTriangle(Vec3f translation, Vec3f v1, Vec3f v2, Vec3f v3) {
        super(new ArrayList<>(3), translation, 0, 1.0f);
        super.vertices = new ArrayList<>(3);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
    }

    /**
     * @see SolidTriangle#SolidTriangle(Vec3f, Vec3f, Vec3f, Vec3f)
     * @param v1 first vertex
     * @param v2 second vertex
     * @param v3 third vertex
     */
    public SolidTriangle(Vec3f v1, Vec3f v2, Vec3f v3) {
        super(new ArrayList<>(3));
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
    }

    @Override
    public int renderingMode() {
        return GL_TRIANGLES;
    }
}
