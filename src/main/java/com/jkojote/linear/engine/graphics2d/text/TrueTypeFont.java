package com.jkojote.linear.engine.graphics2d.text;

import com.jkojote.linear.engine.ReleasableResource;
import com.jkojote.linear.engine.graphics2d.Texture2D;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TrueTypeFont implements ReleasableResource {

    private Glyph glyphUnknown;

    private Texture2D texture;

    private Map<Character, Glyph> glyphMap;

    private Font font;

    private int lineHeight;

    public TrueTypeFont(int size) {
        this(size, Font.PLAIN, true);
    }

    public TrueTypeFont(String path, int size) throws IOException, FontFormatException {
        this (path, size, Font.PLAIN);
    }

    public TrueTypeFont(String path, int size, int style)
    throws IOException, FontFormatException {
        this(Font.createFont(Font.TRUETYPE_FONT, new File(path))
                 .deriveFont(style, size), true);
    }

    public TrueTypeFont(int size, int style, boolean antialias) {
        this(new Font(Font.MONOSPACED, style, size), antialias);
    }

    public TrueTypeFont(Font font) {
        this(font, true);
    }

    public TrueTypeFont(Font font, boolean antiAlias) {
        this.font = font;
        Atlas ascii = new Atlas(font, 0, 256, antiAlias);
        Atlas unknown = new Atlas(font, 63, 64, antiAlias);
        Atlas cyrillic = new Atlas(font, 1024, 1279);
        Atlas combined = unknown.combine(ascii).combine(cyrillic);
        glyphMap = combined.getGlyphsMap();
        glyphUnknown = glyphMap.get((char)63);
        this.texture = new Texture2D(combined.getImage());
        this.lineHeight = combined.getLineHeight();
    }


    public Glyph getGlyph(Character c) {
        return glyphMap.getOrDefault(c, glyphUnknown);
    }

    public Font getFont() { return font; }

    public Texture2D getTexture() { return texture; }

    public int getLineHeight() { return lineHeight; }

    @Override
    public void release() {
        texture.release();
    }

    @Override
    public boolean isReleased() {
        return texture.isReleased();
    }

}
