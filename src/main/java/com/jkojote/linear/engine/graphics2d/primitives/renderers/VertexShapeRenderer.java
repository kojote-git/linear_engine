package com.jkojote.linear.engine.graphics2d.primitives.renderers;

import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.shared.Initializable;
import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.shared.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.graphics2d.primitives.VertexShape;

import java.io.*;

public class VertexShapeRenderer implements Renderer<VertexShape>, Releasable, Initializable {

    private Shader shader;

    private VaoObjectRenderer vaoObjectRenderer;

    public VertexShapeRenderer() { }

    @Override
    public void render(VertexShape shape, Camera camera) {
        Vaof vao = GraphicsUtils.createPrimitiveVao(shape.vertices(), shape.color());
        int drawCount = shape.vertices().size();
        int renderingMode = shape.renderingMode();
        Mat4f modelMatrix = shape.modelMatrix();
        vaoObjectRenderer.renderVao(vao, shader, camera, drawCount, renderingMode, modelMatrix);
        vao.release();
    }

    @Override
    public void init() throws ResourceInitializationException {
        try {
            shader = Shader.fromResources("shaders/shapes/vert.glsl","shaders/shapes/fragm.glsl");
            vaoObjectRenderer = new VaoObjectRenderer();
            vaoObjectRenderer.init();
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
