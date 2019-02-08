package com.jkojote.linear.engine.graphics2d.primitives.renderers;

import com.jkojote.linear.engine.shared.Initializable;
import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.shared.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.Renderer;
import com.jkojote.linear.engine.graphics2d.Shader;
import com.jkojote.linear.engine.graphics2d.Vaof;
import com.jkojote.linear.engine.graphics2d.primitives.Ellipse;
import com.jkojote.linear.engine.math.MathUtils;
import com.jkojote.linear.engine.math.Vec3f;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class EllipseRenderer implements Renderer<Ellipse>, Releasable, Initializable {

    private Shader shader;

    public EllipseRenderer() { }

    @Override
    @SuppressWarnings("Duplicates")
    public void render(Ellipse ellipse, Camera camera) {
        Vec3f color = ellipse.color();
        float xRad = ellipse.xRadius();
        float yRad = ellipse.yRadius();
        float
            colorX = color.x(),
            colorY = color.y(),
            colorZ = color.z();
        int capacity = (360 * 5) << 2;
        FloatBuffer buffer = ByteBuffer.allocateDirect(capacity)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        for (int i = 0; i < 360; i++) {
            float radians = i * MathUtils.PI_180;
            buffer.put((float) Math.cos(radians) * xRad)
                    .put((float) Math.sin(radians) * yRad)
                    .put(colorX).put(colorY).put(colorZ);

        }
        buffer.flip();
        Vaof vao = new Vaof(2, true)
                .addArrayBuffer(buffer, GL_STATIC_DRAW, 0, 2, 20, 0)
                .addArrayBuffer(buffer, GL_STATIC_DRAW, 1, 3, 20, 8);
        shader.bind();
        shader.setUniform("pv", camera.viewProjection(), true);
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
    public void init() throws ResourceInitializationException {
        try {
            shader = Shader.fromResources("shaders/shapes/vert.glsl","shaders/shapes/fragm.glsl");
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public boolean isInitialized() {
        return shader != null;
    }

    @Override
    public void release() {
        if (isInitialized())
            shader.release();
    }

    @Override
    public boolean isReleased() {
        if (!isInitialized())
            return false;
        return shader.isReleased();
    }
}
