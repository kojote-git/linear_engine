package com.jkojote.linear.engine.graphics2d.primitives.renderers;

import com.jkojote.linear.engine.shared.Initializable;
import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.shared.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.graphics2d.primitives.VertexShape;

import java.io.*;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class VertexShapeRenderer implements Renderer<VertexShape>, Releasable, Initializable {

    private Shader shader;

    public VertexShapeRenderer() { }

    @Override
    @SuppressWarnings("Duplicates")
    public void render(VertexShape shape, Camera camera) {
        Vaof vao = GraphicsUtils.createPrimitiveVao(shape.vertices(), shape.color());
        shader.bind();
        shader.setUniform("pv", camera.viewProjection(), true);
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
