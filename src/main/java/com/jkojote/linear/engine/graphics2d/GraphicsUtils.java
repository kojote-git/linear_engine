package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.shared.Transformable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public final class GraphicsUtils {

    private GraphicsUtils() { throw new AssertionError(); }

    public static Vaof createPrimitiveVao(List<Vec3f> vertices, Vec3f color) {
        float
            colorX = color.getX(),
            colorY = color.getY(),
            colorZ = color.getZ();
        int capacity = (vertices.size() * 5) << 2;
        FloatBuffer buffer = ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder()).asFloatBuffer();
        for (Vec3f v : vertices) {
            buffer
                .put(v.getX()).put(v.getY())
                .put(colorX).put(colorY).put(colorZ);
        }
        buffer.flip();
        Vaof vaof = new Vaof(2, true)
            .addArrayBuffer(buffer, GL_STATIC_DRAW, 0, 2, 20, 0)
            .addArrayBuffer(buffer, GL_STATIC_DRAW, 1, 3, 20, 8);
        return vaof;
    }

    public static Mat4f tranformationMatrix(Transformable transformable) {
        Mat4f translation = Mat4f.translation(transformable.getTranslation());
        Mat4f rotation = Mat4f.rotationZ(transformable.getRotationAngle());
        Mat4f scale = Mat4f.scale(transformable.getScaleFactor());
        return translation.mult(rotation).mult(scale);
    }
}
