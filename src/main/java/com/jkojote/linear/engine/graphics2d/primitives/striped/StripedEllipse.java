package com.jkojote.linear.engine.graphics2d.primitives.striped;

import com.jkojote.linear.engine.graphics2d.primitives.BaseShape;
import com.jkojote.linear.engine.graphics2d.primitives.Ellipse;
import com.jkojote.linear.engine.math.Vec3f;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;

public class StripedEllipse extends BaseShape implements Ellipse {

    private float xRadius;

    private float yRadius;

    public StripedEllipse(float xRadius, float yRadius) {
        this.xRadius = xRadius;
        this.yRadius = yRadius;
    }

    public StripedEllipse(Vec3f translation, float xRadius, float yRadius) {
        super(translation, 0, 1.0f);
        this.xRadius = xRadius;
        this.yRadius = yRadius;
    }

    @Override
    public int renderingMode() {
        return GL_LINE_LOOP;
    }

    @Override
    public float xRadius() { return xRadius; }

    @Override
    public float yRadius() { return yRadius; }
}
