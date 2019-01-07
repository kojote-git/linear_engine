package com.jkojote.engine.graphics.texture;

import com.jkojote.linear.engine.ReleasableResource;
import com.jkojote.linear.engine.graphics2d.GraphicsUtils;
import com.jkojote.linear.engine.graphics2d.Texture2D;
import com.jkojote.linear.engine.graphics2d.TexturedObject;
import com.jkojote.linear.engine.graphics2d.Transformable;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class Bird implements TexturedObject, Transformable, ReleasableResource {

    private Texture2D texture;

    private float scaleFactor;

    private float rotationAngle;

    private Vec3f translation;

    private Mat4f transformationMatrix;

    private boolean updateMatrix = true;

    public Bird() {
        texture = new Texture2D("src/test/java/com/jkojote/engine/graphics/texture/bird.png");
        translation = new Vec3f();
        scaleFactor = 1.0f;
    }

    public void init() {
        texture.load();
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

    @Override
    public void setTranslation(Vec3f translation) {
        updateMatrix = true;
        this.translation = translation;
    }

    @Override
    public Vec3f getTranslation() {
        return translation;
    }

    @Override
    public void setScaleFactor(float scaleFactor) {
        if (scaleFactor < 0.05f)
            scaleFactor = 0.05f;
        this.scaleFactor = scaleFactor;
        updateMatrix = true;
    }

    @Override
    public float getScaleFactor() {
        return scaleFactor;
    }

    @Override
    public void setRotationAngle(float rotationAngle) {
        updateMatrix = true;
        this.rotationAngle = rotationAngle;
    }

    @Override
    public float getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public Mat4f transformationMatrix() {
        if (!updateMatrix)
            return transformationMatrix;
        transformationMatrix = GraphicsUtils.tranformationMatrix(this);
        updateMatrix = false;
        return transformationMatrix;
    }
}
