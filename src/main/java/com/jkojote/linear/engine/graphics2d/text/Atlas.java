package com.jkojote.linear.engine.graphics2d.text;

import java.awt.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("Duplicates")
public class Atlas {

    private BufferedImage image;

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

    public Atlas(Font font, int from, int to) {
        this.glyphs = new HashMap<>();
        this.font = font;
        this.image = createImage(font, true, from, to);
    }

    public Atlas(Font font, int from, int to, boolean antiAlias) {
        this.glyphs = new HashMap<>();
        this.font = font;
        this.image = createImage(font, antiAlias, from, to);
    }

    private BufferedImage createImage(Font font, boolean antiAlias, int from, int to) {
        int imageWidth = 0, imageHeight = 0;
        for (int i = from; i < to; i++) {
            if (i == 127)
                continue;
            char c = (char) i;
            BufferedImage image = createCharImage(font, c, antiAlias);
            if (image == null)
                continue;
            imageWidth += image.getWidth();
            imageHeight = Math.max(imageHeight, image.getHeight());
        }
        lineHeight = imageHeight;
        BufferedImage finalImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = finalImage.createGraphics();
        int x = 0;
        for (int i = from; i < to; i++) {
            if (i == 127)
                continue;
            char c = (char) i;
            BufferedImage charImage = createCharImage(font, c, antiAlias);
            if (charImage == null)
                continue;
            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();
            g.drawImage(charImage, x, 0, null);
            Glyph glyph = new Glyph(charWidth, charHeight, x, finalImage.getHeight() - charHeight);
            x += charWidth;
            glyphs.put(c, glyph);
        }
        g.dispose();
        return finalImage;
    }

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

    public Atlas combine(Atlas atlas) {
        if (!font.equals(atlas.font))
            throw new RuntimeException("atlases have different font and they cannot be combined");
        int width = this.image.getWidth() + atlas.image.getWidth();
        int lineHeight = Math.max(this.lineHeight, atlas.lineHeight);
        int thisOffsetY = Math.abs(lineHeight - this.lineHeight);
        int atlasOffsetY = Math.abs(lineHeight - atlas.lineHeight);
        int offsetX = this.image.getWidth();
        Map<Character, Glyph> resGlyphs = new HashMap<>();
        BufferedImage image = new BufferedImage(width, lineHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(this.image, 0, 0, null);
        g.drawImage(atlas.image, offsetX, 0, null);
        this.glyphs.forEach((k, v) -> {
            resGlyphs.put(k, new Glyph(v.width, v.height, v.offsetX, thisOffsetY));
        });
        atlas.glyphs.forEach((k, v) -> {
            resGlyphs.put(k, new Glyph(v.width, v.height, offsetX + v.offsetX, atlasOffsetY));
        });
        g.dispose();
        return new Atlas(image, resGlyphs, font, lineHeight);
    }
}
