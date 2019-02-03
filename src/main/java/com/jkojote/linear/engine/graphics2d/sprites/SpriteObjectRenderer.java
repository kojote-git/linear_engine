package com.jkojote.linear.engine.graphics2d.sprites;

import com.jkojote.linear.engine.Initializable;
import com.jkojote.linear.engine.Releasable;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.math.Mat4f;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class SpriteObjectRenderer implements Renderer<SpriteObject>, Releasable, Initializable {

    private Shader shader;

    private Mat4f proj;

    public SpriteObjectRenderer(Mat4f proj) {
        this.proj = proj;
    }

    private boolean initialized;

    @Override
    @SuppressWarnings("Duplicates")
    public void render(SpriteObject spriteObj, Camera camera) {
        Sprite sprite = spriteObj.sprite();
        Texture2D tex = sprite.spriteSheet().sheet();
        float sWidth = sprite.width(), sHeight = sprite.height();
        float tWidth = tex.getWidth(), tHeight = tex.getHeight();
        float tlX = sprite.topLeft().getX(), tlY = sprite.topLeft().getY();

        float[] data = {
            -sWidth / 2f,  sHeight / 2f, 0.0f, tlX / tWidth, tlY / tHeight,
            -sWidth / 2f, -sHeight / 2f, 0.0f, tlX / tWidth, (tlY + sHeight) / tHeight,
             sWidth / 2f, -sHeight / 2,  0.0f, (tlX + sWidth) / tWidth, (tlY + sHeight) / tHeight,
             sWidth / 2f,  sHeight / 2f, 0.0f, (tlX + sWidth) / tWidth, tlY / tHeight
        };

        Vaof vaof = new Vaof(2, true)
            .addArrayBuffer(data, GL_STATIC_DRAW, 0, 3, 20, 0)
            .addArrayBuffer(data, GL_STATIC_DRAW, 1, 2, 20, 12);
        vaof.unbind();
        shader.bind();
        shader.setUniform("pv", proj.mult(camera.viewMatrix()), true);
        shader.setUniform("model", spriteObj.modelMatrix(), true);
        vaof.bind();
        tex.bind();
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawArrays(spriteObj.renderingMode(), 0, 4);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        tex.unbind();
        vaof.unbind();
        vaof.release();
        shader.unbind();
    }

    @Override
    public void init() throws ResourceInitializationException {
        if (initialized) return;
        initialized = true;
        try {
            shader = Shader.fromResources("shaders/texture/vert.glsl", "shaders/texture/fragm.glsl");
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void release() {
        shader.release();
    }

    @Override
    public boolean isReleased() {
        return shader.isReleased();
    }
}
