package com.jkojote.linear.engine.graphics2d.primitives.renderers;

import com.jkojote.linear.engine.Initializable;
import com.jkojote.linear.engine.Releasable;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.Renderer;
import com.jkojote.linear.engine.graphics2d.Shader;
import com.jkojote.linear.engine.graphics2d.VaoObject;
import com.jkojote.linear.engine.math.Mat4f;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.glDrawArrays;

public class VaoObjectRenderer implements Renderer<VaoObject>, Releasable, Initializable {

    private Mat4f projectionMatrix;

    private Shader shader;

    public VaoObjectRenderer(Mat4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public void setProjectionMatrix(Mat4f projectionMatrix) {
        if (projectionMatrix == null)
            throw new NullPointerException("projectionMatrix must mot be null");
        this.projectionMatrix = projectionMatrix;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void render(VaoObject vao, Camera camera) {
        shader.bind();
        shader.setUniform("pv", projectionMatrix.mult(camera.viewMatrix()), true);
        shader.setUniform("model", vao.modelMatrix(), true);
        vao.bind();
        vao.enableAttributes();
        glDrawArrays(vao.renderingMode(), 0, vao.drawCount());
        vao.disableAttributes();
        vao.unbind();
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
