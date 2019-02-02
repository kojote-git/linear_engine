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

public class SpriteRenderer implements Renderer<Sprite>, Releasable, Initializable {

    private Shader shader;

    private Mat4f proj;

    public SpriteRenderer(Mat4f proj) {
        this.proj = proj;
    }

    private boolean initialized;

    @Override
    public void render(Sprite sprite, Camera camera) {
        Texture2D tex = sprite.spriteSheet().sheet();
        float sWidth = sprite.width(), sHeight = sprite.height();
        float tWidth = tex.getWidth(), tHeight = tex.getHeight();
        float tlX = sprite.topLeft().getX(), tlY = sprite.topLeft().getY();

        float[] data = {
            tlX - sWidth / 2, tlY - sHeight / 2, 0, tlX / tWidth, tlY / tHeight,
            tlX - sWidth / 2, tlY + sHeight / 2, 0, tlX / tWidth, (tlY + sHeight) / tHeight,
            tlX + sWidth / 2, tlY + sHeight / 2, 0, (tlX + sWidth) / tWidth, (tlY + sHeight) / tHeight,
            tlX + sWidth / 2, tlY - sHeight / 2, 0, (tlX + sWidth) / tWidth, tlY / tHeight
        };

        Vaof vaof = new Vaof(2, true)
            .addArrayBuffer(data, GL_STATIC_DRAW, 0, 3, 20, 0)
            .addArrayBuffer(data, GL_STATIC_DRAW, 1, 2, 20, 12);
        shader.bind();
        vaof.bind();
        shader.setUniform("pv", proj.mult(camera.viewMatrix()), true);
        shader.setUniform("model", sprite.modelMatrix(), true);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawArrays(sprite.renderingMode(), 0, 4);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        shader.unbind();
        vaof.release();
    }

    @Override
    public void init() throws ResourceInitializationException {
        if (initialized) return;
        initialized = true;
        try {
            shader = Shader.fromResources("texture/vert.glsl", "texture/fragm.glsl");
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
