package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.math.Vec3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class Rectangle extends BaseVertexShape {

    private float initialWidth, initialHeight;

    public Rectangle(Vec3f translation, float width, float height) {
        initialWidth = width;
        initialHeight = height;
        super.vertices = new ArrayList<>(4);
        vertices.add(new Vec3f(-width / 2, -height / 2, 0));
        vertices.add(new Vec3f(-width / 2,  height / 2, 0));
        vertices.add(new Vec3f( width / 2,  height / 2, 0));
        vertices.add(new Vec3f( width / 2, -height / 2, 0));
        setTranslation(translation);
        setColor(new Vec3f());
        setScaleFactor(1.0f);
        setRotationAngle(0.0f);
    }

    public float getInitialWidth() { return initialWidth; }

    public float getInitialHeight() { return initialHeight; }

    @Override
    public int renderingMode() {
        return GL_QUADS;
    }
}
