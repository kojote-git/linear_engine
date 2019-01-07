package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.graphics2d.Vaof;
import com.jkojote.linear.engine.math.Vec3f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public final class VaoUtils {

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
}
