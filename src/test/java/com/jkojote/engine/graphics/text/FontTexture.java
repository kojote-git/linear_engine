package com.jkojote.engine.graphics.text;

import com.jkojote.linear.engine.ReleasableResource;
import com.jkojote.linear.engine.graphics2d.Texture2D;
import com.jkojote.linear.engine.graphics2d.TexturedObject;
import com.jkojote.linear.engine.graphics2d.text.TrueTypeFont;
import com.jkojote.linear.engine.math.Mat4f;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class FontTexture implements TexturedObject, ReleasableResource {

    private TrueTypeFont font;

    public FontTexture(TrueTypeFont font) {
        this.font = font;
    }

    public TrueTypeFont getFont() {
        return font;
    }

    @Override
    public Texture2D getTexture() {
        return font.getTexture();
    }

    @Override
    public Mat4f modelMatrix() {
        return Mat4f.identity();
    }

    @Override
    public int renderingMode() {
        return GL_QUADS;
    }

    @Override
    public void release() {
        font.release();
    }

    @Override
    public boolean isReleased() {
        return font.isReleased();
    }
}
