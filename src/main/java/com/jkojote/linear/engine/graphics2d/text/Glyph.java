package com.jkojote.linear.engine.graphics2d.text;

public final class Glyph {

    final int width;

    final int height;

    final int offsetX;

    final int offsetY;

    public Glyph(int width, int height, int offsetX, int offsetY) {
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
