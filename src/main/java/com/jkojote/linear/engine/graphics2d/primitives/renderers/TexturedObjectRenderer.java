package com.jkojote.linear.engine.graphics2d.primitives.renderers;

import com.jkojote.linear.engine.Initializable;
import com.jkojote.linear.engine.Releasable;
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
        Releasable, Initializable {

    private TextureRenderer textureRenderer;


    public TexturedObjectRenderer(Mat4f projectionMatrix) {
        this.textureRenderer = new TextureRenderer(projectionMatrix);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void render(TexturedObject obj, Camera camera) {
        renderTexture(obj.getTexture(), camera, obj.modelMatrix());
    }

    private void renderTexture(Texture2D texture, Camera camera, Mat4f modelMatrtix) {
        textureRenderer.render(texture, camera, modelMatrtix);
    }

    @Override
    public void init() throws ResourceInitializationException {
        textureRenderer.init();
    }

    @Override
    public boolean isInitialized() {
        return textureRenderer.isInitialized();
    }

    @Override
    public void release() {
        textureRenderer.release();
    }

    @Override
    public boolean isReleased() {
        return textureRenderer.isReleased();
    }
}
