package com.jkojote.linear.engine.graphics2d.primitives.filled;

import com.jkojote.linear.engine.graphics2d.primitives.BaseVertexShape;
import com.jkojote.linear.engine.math.Vec3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class Rectangle extends BaseVertexShape {

    private float initialWidth, initialHeight;

    public Rectangle(Vec3f translation, float width, float height) {
        super.vertices = new ArrayList<>(4);
        super.translation = translation;
        super.color = new Vec3f();
        super.scaleFactor = 1.0f;

        initialWidth = width;
        initialHeight = height;
        vertices.add(new Vec3f(-width / 2, -height / 2, 0));
        vertices.add(new Vec3f(-width / 2,  height / 2, 0));
        vertices.add(new Vec3f( width / 2,  height / 2, 0));
        vertices.add(new Vec3f( width / 2, -height / 2, 0));
    }

    public float getInitialWidth() { return initialWidth; }

    public float getInitialHeight() { return initialHeight; }

    @Override
    public int renderingMode() {
        return GL_QUADS;
    }
}
