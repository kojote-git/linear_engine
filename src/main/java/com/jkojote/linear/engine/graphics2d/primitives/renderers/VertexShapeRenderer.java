package com.jkojote.linear.engine.graphics2d.primitives.renderers;

import com.jkojote.linear.engine.InitializableResource;
import com.jkojote.linear.engine.ReleasableResource;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.graphics2d.primitives.VertexShape;
import com.jkojote.linear.engine.math.Mat4f;

import java.io.*;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class VertexShapeRenderer implements Renderer<VertexShape>, ReleasableResource, InitializableResource {

    private Shader shader;

    private Mat4f projectionMatrix;

    public VertexShapeRenderer(Mat4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void render(VertexShape shape, Camera camera) {
        Vaof vao = GraphicsUtils.createPrimitiveVao(shape.vertices(), shape.color());
        shader.bind();
        shader.setUniform("pv", projectionMatrix.mult(camera.viewMatrix()), true);
        shader.setUniform("model", shape.modelMatrix(), true);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawArrays(shape.renderingMode(), 0, shape.vertices().size());

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

    public void setProjectionMatrix(Mat4f projectionMatrix) {
        if (projectionMatrix == null)
            throw new NullPointerException("projectionMatrix must not be null");
        this.projectionMatrix = projectionMatrix;
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
