package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.math.Mat4f;

public final class Utils {

    private static final float PI_180 = (float) Math.PI / 180;

    public static Mat4f scaleMatrix(float scaleFactor) {
        Mat4f scale = Mat4f.identity();
        scale.set(0, 0, scaleFactor);
        scale.set(1, 1, scaleFactor);
        scale.set(2, 2, scaleFactor);
        return scale;
    }

    public static Mat4f rotationMatrix(float angle) {
        Mat4f res = Mat4f.identity();
        res.set(0, 0, (float) Math.cos(angle * PI_180));
        res.set(0, 1, (float) -Math.sin(angle * PI_180));
        res.set(1, 0, (float) Math.sin(angle * PI_180));
        res.set(1, 1, (float) Math.cos(angle * PI_180));
        return res;
    }
}
