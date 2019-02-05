package com.jkojote.linear.engine.graphics2d.primitives.renderers;

import com.jkojote.linear.engine.shared.Initializable;
import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.shared.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.math.Mat4f;

public class TexturedObjectRenderer implements Renderer<TexturedObject>,
        Releasable, Initializable {

    private TextureRenderer textureRenderer;

    public TexturedObjectRenderer() {
        this.textureRenderer = new TextureRenderer();
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
