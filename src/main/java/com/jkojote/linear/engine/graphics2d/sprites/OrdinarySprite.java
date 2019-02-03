package com.jkojote.linear.engine.graphics2d.sprites;

import com.jkojote.linear.engine.math.Mat4f;

public final class OrdinarySprite implements Sprite {

    private float width, height;

    private SpriteSheet spriteSheet;

    private Point topLeft;

    private Mat4f identity = Mat4f.identity();

    public OrdinarySprite(SpriteSheet spriteSheet, Point topLeft, float width, float height) {
        this.spriteSheet = spriteSheet;
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    @Override
    public SpriteSheet spriteSheet() {
        return spriteSheet;
    }

    @Override
    public Point topLeft() {
        return topLeft;
    }

    @Override
    public float width() {
        return width;
    }

    @Override
    public float height() {
        return height;
    }

    @Override
    public Mat4f modelMatrix() {
        return identity;
    }
}
