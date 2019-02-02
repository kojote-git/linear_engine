package com.jkojote.linear.engine.graphics2d.primitives.solid;

import com.jkojote.linear.engine.graphics2d.primitives.BaseShape;
import com.jkojote.linear.engine.graphics2d.primitives.Ellipse;
import com.jkojote.linear.engine.math.Vec3f;

import static org.lwjgl.opengl.GL11.*;

public class SolidEllipse extends BaseShape implements Ellipse {

    private float xRadius, yRadius;

    public SolidEllipse(Vec3f translation, float xRadius, float yRadius) {
        super(translation, 0, 1.0f);
        this.xRadius = xRadius;
        this.yRadius = yRadius;
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
