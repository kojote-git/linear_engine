package com.jkojote.linear.engine.graphics2d.sprites;

import com.jkojote.linear.engine.graphics2d.Renderable;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public interface Sprite extends Renderable {

    SpriteSheet spriteSheet();

    Point topLeft();

    float width();

    float height();

    @Override
    default int renderingMode() {
        return GL_QUADS;
    }
}
