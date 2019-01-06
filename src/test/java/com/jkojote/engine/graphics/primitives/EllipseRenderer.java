package com.jkojote.engine.graphics.primitives;

import com.jkojote.linear.engine.ReleasableResource;
import com.jkojote.linear.engine.graphics2d.Shader;
import com.jkojote.linear.engine.graphics2d.Vaof;
import com.jkojote.linear.engine.graphics2d.primitives.filled.Ellipse;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class EllipseRenderer implements ReleasableResource {

    private Mat4f viewProj;

    private static final float PI_180 = (float) Math.PI / 180;

    private Shader shader;

    public EllipseRenderer(Mat4f veiw, Mat4f proj) {
        this.viewProj = proj.mult(veiw);
    }

    public void render(Ellipse ellipse) {
        Vec3f color = ellipse.color();
        float xRad = ellipse.xRadius();
        float yRad = ellipse.yRadius();
        float
            colorX = color.getX(),
            colorY = color.getY(),
            colorZ = color.getZ();
        int capacity = (360 * 5) << 2;
        FloatBuffer buffer = ByteBuffer.allocateDirect(capacity)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        for (int i = 0; i < 360; i++) {
            float radians = i * PI_180;
            buffer.put((float) Math.cos(radians) * xRad)
                  .put((float) Math.sin(radians) * yRad)
                  .put(colorX).put(colorY).put(colorZ);

        }
        buffer.flip();
        Vaof vao = new Vaof(2, true)
                .addArrayBuffer(buffer, GL_STATIC_DRAW, 0, 2, 20, 0)
                .addArrayBuffer(buffer, GL_STATIC_DRAW, 1, 3, 20, 8);
        shader.bind();
        shader.setUniform("viewProj", viewProj, false);
        shader.setUniform("model", ellipse.modelMatrix(), true);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawArrays(ellipse.renderingMode(), 0, 360);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        vao.release();
        shader.unbind();
    }

    @Override
    public void release() {
        shader.release();
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
}
