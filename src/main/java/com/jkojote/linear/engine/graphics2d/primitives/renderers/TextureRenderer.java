package com.jkojote.linear.engine.graphics2d.primitives.renderers;

import com.jkojote.linear.engine.shared.Initializable;
import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.shared.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.math.Mat4f;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class TextureRenderer implements Renderer<Texture2D>, Initializable, Releasable {

    private Shader shader;

    private VaoObjectRenderer vaoObjectRenderer;

    public TextureRenderer() { }

    @Override
    public void render(Texture2D texture, Camera camera) {
        render(texture, camera, texture.modelMatrix());
    }

    public void render(Texture2D texture, Camera camera, Mat4f transformationMatrix) {
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
        texture.bind();
        vaoObjectRenderer.renderVao(vao, shader, camera, 4, GL_QUADS, transformationMatrix);
        texture.unbind();
        vao.release();
    }

    @Override
    public void init() throws ResourceInitializationException {
        try {
            shader = Shader.fromResources("shaders/texture/vert.glsl", "shaders/texture/fragm.glsl");
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
