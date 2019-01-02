package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.math.Mat4f;

public final class Utils {

    private static final float PI_180 = (float) Math.PI / 180;

    public static Mat4f modelMatrix(Primitive p) {
        float scaleFactor = p.getScale();
        float rotationAngle = p.getRotationAngle();
        Mat4f scale = Mat4f.identity();
        Mat4f rotation = Mat4f.identity();
        scale.set(0, 0, scaleFactor);
        scale.set(1, 1, scaleFactor);
        scale.set(2, 2, scaleFactor);

        rotation.set(0, 0, (float) Math.cos(rotationAngle * PI_180));
        rotation.set(0, 1, (float) -Math.sin(rotationAngle * PI_180));
        rotation.set(1, 0, (float) Math.sin(rotationAngle * PI_180));
        rotation.set(1, 1, (float) Math.cos(rotationAngle * PI_180));
        return rotation.mult(scale);
    }
}
