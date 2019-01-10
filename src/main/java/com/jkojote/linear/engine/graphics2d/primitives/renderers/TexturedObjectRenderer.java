package com.jkojote.linear.engine.graphics2d.primitives.renderers;

import com.jkojote.linear.engine.InitializableResource;
import com.jkojote.linear.engine.ReleasableResource;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.math.Mat4f;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class TexturedObjectRenderer implements Renderer<TexturedObject>,
        ReleasableResource, InitializableResource {

    private Mat4f projectionMatrix;

    private Shader shader;

    public TexturedObjectRenderer(Mat4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public void setProjectionMatrix(Mat4f projectionMatrix) {
        if (projectionMatrix == null)
            throw new NullPointerException("projection matrix cannot be null");
        this.projectionMatrix = projectionMatrix;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void render(TexturedObject obj, Camera camera) {
        Texture2D texture = obj.getTexture();
        int
            width = texture.getWidth(),
            height = texture.getHeight();
        float[] coords = {
        //  ---------- position -----------  -coords-
            -width / 2f,  height / 2f, 0.0f,   0, 0,
            -width / 2f, -height / 2f, 0.0f,   0, 1,
             width / 2f, -height / 2f, 0.0f,   1, 1,
             width / 2f,  height / 2f, 0.0f,   1, 0
        };
        Vaof vao = new Vaof(2, true)
                .addArrayBuffer(coords, GL_STATIC_DRAW, 0, 3, 20, 0)
                .addArrayBuffer(coords, GL_STATIC_DRAW, 1, 2, 20, 12);
        vao.unbind();
        shader.bind();
        shader.setUniform("pv", projectionMatrix.mult(camera.viewMatrix()), true);
        shader.setUniform("model", obj.modelMatrix(), true);
        texture.bind();
        vao.bind();
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawArrays(GL_QUADS, 0, 4);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        texture.unbind();
        vao.unbind();
        vao.release();
        shader.unbind();
    }

    @Override
    public void init() throws ResourceInitializationException {
        try {
            shader = Shader.fromResources("shaders/texture/vert.glsl", "shaders/texture/fragm.glsl");
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
