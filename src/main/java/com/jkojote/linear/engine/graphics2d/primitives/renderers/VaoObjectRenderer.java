package com.jkojote.linear.engine.graphics2d.primitives.renderers;

import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.shared.Initializable;
import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.shared.ResourceInitializationException;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class VaoObjectRenderer implements Renderer<VaoObject>, Releasable, Initializable {

    private Shader shader;

    public VaoObjectRenderer() { }

    @Override
    public void render(VaoObject vao, Camera camera) {
        render(shader, vao, camera);
    }

    void render(Shader shader, VaoObject vao, Camera camera) {
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

    void renderVao(Vaof vaof, Shader shader, Camera camera,
                   int drawCount, int renderingMode, Mat4f modelMatrix) {
        shader.bind();
        shader.setUniform("pv", camera.viewProjection(), true);
        shader.setUniform("model", modelMatrix, true);
        vaof.bind();
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawArrays(renderingMode, 0, drawCount);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        vaof.unbind();
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
