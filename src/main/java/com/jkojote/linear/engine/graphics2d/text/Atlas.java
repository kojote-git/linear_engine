package com.jkojote.linear.engine.graphics2d.text;

import java.awt.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.*;


@SuppressWarnings("Duplicates")
public class Atlas {

    private BufferedImage image;

    private static final int MAX_IMAGE_WIDTH = 4096;

    private Map<Character, Glyph> glyphs;

    private Font font;

    private int lineHeight;

    public BufferedImage getImage() { return image; }

    public int getLineHeight() { return lineHeight; }

    public Map<Character, Glyph> getGlyphsMap() {
        return Collections.unmodifiableMap(glyphs);
    }

    private Atlas(BufferedImage image, Map<Character, Glyph> glyphs, Font font, int lineHeight) {
        this.image = image;
        this.glyphs = glyphs;
        this.font = font;
        this.lineHeight = lineHeight;
    }

    /**
     * Create atlas that includes unicode characters within range that begins at {@code from} (inclusive)
     * and ends at {@code end} (exclusive) that are drawn using given font.
     * @param font
     * @param begin begin of the range
     * @param end end of the range
     * @param antiAlias whether antialiasing to be enabled por not
     */
    public Atlas(Font font, int begin, int end, boolean antiAlias) {
        if (begin < 0 || end < begin)
            throw new IllegalArgumentException("begin must be positive and must be less than end");
        this.glyphs = new HashMap<>();
        this.font = font;
        this.image = createImage(font, begin, end, antiAlias);
    }

    private BufferedImage createImage(Font font, int begin, int end, boolean antiAlias) {
        AtlasImageProperties props = getProperties(font, begin, end, antiAlias);
        int imageWidth = props.width,
            imageHeight = props.height,
            lineHeight = props.lineHeight;
        LinkedList<NewLinePoint> newLinePoints = props.linePoints;
        BufferedImage finalImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = finalImage.createGraphics();
        int xOffset = 0;
        int yOffset = 0;
        for (int i = begin; i < end; i++) {
            if (i == 127)
                continue;
            if (!newLinePoints.isEmpty() && i == newLinePoints.getFirst().lastChar) {
                // move offsets to new line
                yOffset += newLinePoints.getFirst().lineHeight;
                xOffset = 0;
                newLinePoints.removeFirst();
            }
            char c = (char) i;
            BufferedImage charImage = createCharImage(font, c, antiAlias);
            if (charImage == null)
                continue;
            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();
            g.drawImage(charImage, xOffset, yOffset, null);
            Glyph glyph = new Glyph(charWidth, charHeight, xOffset, yOffset + lineHeight - charHeight);
            xOffset += charWidth;
            glyphs.put(c, glyph);
        }
        g.dispose();
        return finalImage;
    }

    /* creates buffered image for given character using given font
     */
    private BufferedImage createCharImage(Font font, char c, boolean antiAlias) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();
        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();
        if (charWidth == 0)
            return null;
        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        g.setFont(font);
        g.setFont(font);
        g.setPaint(Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

    /**
     * Combines two atlases. Doesn't change this object.
     * @param atlas atlas to be combined with
     * @return new combined atlas
     * @throws RuntimeException atlases have different font
     */
    public Atlas combine(Atlas atlas) {
        if (!font.equals(atlas.font))
            throw new RuntimeException("atlases have different font and they cannot be combined");
        int width = Math.max(this.image.getWidth(), atlas.image.getWidth()),
            height = this.image.getHeight() + atlas.image.getHeight(),
            // y offset for the atlas image on the new atlas image
            atlasOffsetY = this.image.getHeight(),
            lineHeight = Math.max(this.lineHeight, atlas.lineHeight);
        Map<Character, Glyph> resAtlasGlyphs = new HashMap<>();
        BufferedImage resAtlasImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resAtlasImage.createGraphics();
        g.drawImage(this.image, 0, 0, null);
        // draw the second image below the first image
        g.drawImage(atlas.image, 0, this.image.getHeight(), null);
        // calculate glyphs for atlases
        this.glyphs.forEach(resAtlasGlyphs::put);
        atlas.glyphs.forEach((k, v) -> {
            resAtlasGlyphs.put(k, new Glyph(v.width, v.height, v.offsetX, atlasOffsetY + v.offsetY));
        });
        g.dispose();
        return new Atlas(resAtlasImage, resAtlasGlyphs, font, lineHeight);
    }

    private AtlasImageProperties getProperties(Font font, int from, int to, boolean antiAlias) {
        int imageWidth = 0, imageHeight = 0, lineHeight = 0, lineWidth = 0;
        LinkedList<NewLinePoint> linePoints = new LinkedList<>();
        // determine width and size of the final image
        for (int i = from; i < to; i++) {
            if (i == 127)
                continue;
            char c = (char) i;
            BufferedImage image = createCharImage(font, c, antiAlias);
            if (image == null)
                continue;
            // start new line
            if (lineWidth >= MAX_IMAGE_WIDTH) {
                lineWidth = 0;
                // increase height by line height
                imageHeight += lineHeight;
                // when line width exceeds maximum line width
                // we need the information such as what character was last in this line
                // and also the height of the line
                linePoints.add(new NewLinePoint(i, lineHeight));
            }
            lineWidth += image.getWidth();
            imageWidth = Math.max(lineWidth, imageWidth);
            lineHeight = Math.max(lineHeight, image.getHeight());
        }
        this.lineHeight = lineHeight;
        // ensure sufficient height so that all characters fit onto final image
        imageHeight += lineHeight;
        return new AtlasImageProperties(linePoints, imageHeight, imageWidth, lineHeight);
    }

    private class NewLinePoint {
        int lastChar;
        int lineHeight;

        public NewLinePoint(int lastChar, int lineHeight) {
            this.lastChar = lastChar;
            this.lineHeight = lineHeight;
        }
    }

    private class AtlasImageProperties {
        int height;
        int width;
        int lineHeight;
        LinkedList<NewLinePoint> linePoints;

        AtlasImageProperties(LinkedList<NewLinePoint> linePoints, int height, int width, int lineHeight) {
            this.height = height;
            this.width = width;
            this.lineHeight = lineHeight;
            this.linePoints = linePoints;
        }
    }
}
