package com.jkojote.linear.engine.graphics2d.primitives.solid;

import com.jkojote.linear.engine.graphics2d.primitives.BaseVertexShape;
import com.jkojote.linear.engine.math.Vec3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class SolidRectangle extends BaseVertexShape {

    private float width, height;

    public SolidRectangle(Vec3f translation, float width, float height) {
        super(new ArrayList<>(4), translation, 0, 1.0f);

        this.width = width;
        this.height = height;
        vertices.add(new Vec3f(-width / 2, -height / 2, 0));
        vertices.add(new Vec3f(-width / 2,  height / 2, 0));
        vertices.add(new Vec3f( width / 2,  height / 2, 0));
        vertices.add(new Vec3f( width / 2, -height / 2, 0));
    }

    public float getWidth() { return width; }

    public float getHeight() { return height; }

    @Override
    public int renderingMode() {
        return GL_QUADS;
    }
}
