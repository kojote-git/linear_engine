package com.jkojote.linear.engine.graphics2d.text;

/**
 * A glyph encapsulates specific information about glyph's location and properties on the font atlas.
 * It's also correct to say that a glyph encapsulates information about specific <b>character's</b> location and properties
 * on the font atlas.
 */
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


    /**
     * @return width of the glyph
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return height of the glyph
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return offset of the top left corner of this glyph along x axis
     */
    public int getOffsetX() {
        return offsetX;
    }

    /**
     * @return offset of the top left corner of this glyph along y axis
     */
    public int getOffsetY() {
        return offsetY;
    }

    @Override
    public String toString() {
        return "Glyph{" +
                "width=" + width +
                ", height=" + height +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                '}';
    }
}
