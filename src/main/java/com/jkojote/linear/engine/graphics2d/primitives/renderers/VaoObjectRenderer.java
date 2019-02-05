package com.jkojote.linear.engine.graphics2d.primitives.renderers;

import com.jkojote.linear.engine.shared.Initializable;
import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.shared.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.Renderer;
import com.jkojote.linear.engine.graphics2d.Shader;
import com.jkojote.linear.engine.graphics2d.VaoObject;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.glDrawArrays;

public class VaoObjectRenderer implements Renderer<VaoObject>, Releasable, Initializable {

    private Shader shader;

    public VaoObjectRenderer() { }

    @Override
    @SuppressWarnings("Duplicates")
    public void render(VaoObject vao, Camera camera) {
        shader.bind();
        shader.setUniform("pv", camera.viewProjection(), true);
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
