package com.jkojote.linear.engine.graphics2d.primitives.filled;

import com.jkojote.linear.engine.graphics2d.primitives.BaseShape;
import com.jkojote.linear.engine.math.Vec3f;

import static org.lwjgl.opengl.GL11.*;

public class Ellipse extends BaseShape {

    private float xRadius, yRadius;

    public Ellipse(Vec3f translation, float xRadius, float yRadius) {
        super.translation = translation;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
        setScaleFactor(1.0f);
        setTranslation(translation);
        setColor(new Vec3f());
    }

    public float xRadius() {
        return xRadius;
    }

    public float yRadius() {
        return yRadius;
    }

    @Override
    public int renderingMode() {
        return GL_TRIANGLE_FAN;
    }
}