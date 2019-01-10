package com.jkojote.linear.engine.graphics2d.text;

import com.jkojote.linear.engine.ReleasableResource;
import com.jkojote.linear.engine.graphics2d.Texture2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TrueTypeFont implements ReleasableResource {

    private Map<Character, Glyph> glyphs;

    private Texture2D texture;

    private Font font;

    public TrueTypeFont(int size) {
        font = new Font(Font.MONOSPACED, Font.PLAIN, size);
        glyphs = new HashMap<>();
        initFont(font, true);
    }

    public TrueTypeFont(String path, int size) throws IOException, FontFormatException {
        this (path, size, Font.PLAIN);
    }

    public TrueTypeFont(String path, int size, int style)
    throws IOException, FontFormatException {
        font = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(style, size);
        glyphs = new HashMap<>();
        initFont(font, true);
    }

    @SuppressWarnings("Duplicates")
    private void initFont(Font font, boolean antiAlias) {
        int imageWidth = 0, imageHeight = 0;
        for (int i = 32; i < 256; i++) {
            if (i == 127)
                continue;
            char c = (char) i;
            BufferedImage image = createCharImage(font, c, antiAlias);
            if (image == null)
                continue;
            imageWidth += image.getWidth();
            imageHeight = Math.max(imageHeight, image.getHeight());
        }
        BufferedImage finalImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = finalImage.createGraphics();
        int x = 0;
        for (int i = 32; i < 256; i++) {
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
        texture = new Texture2D(finalImage);
    }

    public Glyph getGlyph(Character c) {
        return glyphs.get(c);
    }

    public Font getFont() {
        return font;
    }

    private BufferedImage createCharImage(Font font, char c, boolean antiAlias) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
        g.setFont(font);
        g.setPaint(Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

    public Texture2D getTexture() {
        return texture;
    }

    @Override
    public void release() {
        texture.release();
    }

    @Override
    public boolean isReleased() {
        return texture.isReleased();
    }
}
