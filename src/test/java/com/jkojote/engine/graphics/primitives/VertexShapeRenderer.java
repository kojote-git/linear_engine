package com.jkojote.engine.graphics.primitives;

import com.jkojote.linear.engine.ReleasableResource;
import com.jkojote.linear.engine.graphics2d.Shader;
import com.jkojote.linear.engine.graphics2d.Vaof;
import com.jkojote.linear.engine.graphics2d.primitives.VertexShape;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class VertexShapeRenderer implements ReleasableResource {

    private Mat4f viewProj;

    private Shader shader;

    public VertexShapeRenderer(Mat4f view, Mat4f proj) {
        viewProj = proj.mult(view);
    }

    public void render(VertexShape shape) {
        List<Vec3f> vertices = shape.vertices();
        Vec3f color = shape.color();
        float colorX = color.getX(),
              colorY = color.getY(),
              colorZ = color.getZ();
        int capacity = (vertices.size() * 5) << 2;
        FloatBuffer buffer = ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder()).asFloatBuffer();
        for (Vec3f vert : vertices) {
            buffer
                .put(vert.getX()).put(vert.getY())
                .put(colorX).put(colorY).put(colorZ);
        }
        buffer.flip();
        Vaof vao = new Vaof(2);
        vao.bind();
        vao.addArrayBuffer(buffer, GL_STATIC_DRAW, 0, 2, 20, 0);
        vao.addArrayBuffer(buffer, GL_STATIC_DRAW, 1, 3, 20, 8);
        shader.bind();
        shader.setUniform("viewProj", viewProj, false);
        shader.setUniform("model", shape.modelMatrix(), true);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawArrays(shape.renderingMode(), 0, vertices.size());

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        vao.release();
        shader.unbind();
    }

    public void init() {
        try {
            shader = Shader.fromFiles(
                "src/test/java/com/jkojote/engine/graphics/primitives/vert.glsl",
                "src/test/java/com/jkojote/engine/graphics/primitives/fragm.glsl"
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void release() {
        shader.release();
    }
}
