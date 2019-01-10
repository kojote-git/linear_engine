package com.jkojote.engine.graphics;

import com.jkojote.linear.engine.graphics2d.BaseTransformable;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

public class TransformableCamera extends BaseTransformable implements Camera {

    public TransformableCamera() {
        super(new Vec3f(), 0, 1.0f);
    }

    @Override
    public Mat4f viewMatrix() {
        Mat4f translation = Mat4f.translation(getTranslation().copy().scalar(-1));
        Mat4f rotation = Mat4f.rotationZ(-getRotationAngle());
        Mat4f scale = Mat4f.scale(getScaleFactor());
        return scale.mult(translation).mult(rotation);
    }
}
