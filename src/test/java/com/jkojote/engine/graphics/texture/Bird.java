package com.jkojote.engine.graphics.texture;

import com.jkojote.linear.engine.InitializableResource;
import com.jkojote.linear.engine.ReleasableResource;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class Bird extends BaseTransformable
        implements TexturedObject, ReleasableResource, InitializableResource {

    private Texture2D texture;

    public Bird() {
        super(new Vec3f(), 0.0f, 1.0f);
    }

    @Override
    public void init() throws ResourceInitializationException {
        try {
            texture = new Texture2D("src/test/java/com/jkojote/engine/graphics/texture/bird.png");
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
