package com.jkojote.engine.graphics.texture;

import com.jkojote.linear.engine.shared.Initializable;
import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.shared.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.shared.BaseTransformable;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class Bird extends BaseTransformable
        implements TexturedObject, Releasable, Initializable {

    private Texture2D texture;

    public Bird() {
        super(new Vec3f(), 0.0f, 1.0f);
    }

    @Override
    public void init() throws ResourceInitializationException {
        try {
            texture = new Texture2D("src/test/java/com/jkojote/engine/graphics/texture/bird.png");
            texture.init();
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public boolean isInitialized() {
        return texture != null;
    }

    @Override
    public void release() {
        texture.release();
    }

    @Override
    public boolean isReleased() {
        return texture.isReleased();
    }

    @Override
    public Texture2D getTexture() {
        return texture;
    }

    @Override
    public Mat4f modelMatrix() {
        return transformationMatrix();
    }

    @Override
    public int renderingMode() {
        return GL_TRIANGLES;
    }

}
