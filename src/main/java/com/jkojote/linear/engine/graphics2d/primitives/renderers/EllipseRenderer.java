package com.jkojote.linear.engine.graphics2d.primitives.renderers;

import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.shared.Initializable;
import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.shared.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.primitives.Ellipse;
import com.jkojote.linear.engine.math.MathUtils;
import com.jkojote.linear.engine.math.Vec3f;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class EllipseRenderer implements Renderer<Ellipse>, Releasable, Initializable {

    private Shader shader;

    private VaoObjectRenderer vaoObjectRenderer;

    public EllipseRenderer() { }

    @Override
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
        vaoObjectRenderer.renderVao(vao, shader, camera, 360, ellipse.renderingMode(), ellipse.modelMatrix());
        vao.release();
    }

    @Override
    public void init() throws ResourceInitializationException {
        try {
            shader = Shader.fromResources("shaders/shapes/vert.glsl","shaders/shapes/fragm.glsl");
            vaoObjectRenderer = new VaoObjectRenderer();
            vaoObjectRenderer.init();
        } catch (IOException | ResourceInitializationException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public boolean isInitialized() {
        return shader != null;
    }

    @Override
    public void release() {
        if (isInitialized()) {
            shader.release();
            vaoObjectRenderer.release();
        }
    }

    @Override
    public boolean isReleased() {
        if (!isInitialized())
            return false;
        return shader.isReleased();
    }
}
