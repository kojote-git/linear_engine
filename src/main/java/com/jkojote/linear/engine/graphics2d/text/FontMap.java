package com.jkojote.linear.engine.graphics2d.text;

import com.jkojote.linear.engine.InitializableResource;
import com.jkojote.linear.engine.ReleasableResource;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.Texture2D;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_LINEAR;

public class FontMap implements ReleasableResource, InitializableResource {

    private Glyph glyphUnknown;

    private Texture2D texture;

    private Map<Character, Glyph> glyphMap;

    private Font font;

    private int lineHeight;

    private boolean initialized;

    private boolean antiAliasEnabled;

    public FontMap(int size) {
        this(size, Font.PLAIN, true);
    }

    public FontMap(String path, int fontFormat, int size, int style, boolean antiAlias)
            throws IOException, FontFormatException {
        this(Font.createFont(fontFormat, new File(path))
                .deriveFont(style, size), antiAlias);
    }

    public FontMap(int size, int style, boolean antialias) {
        this(new Font(Font.MONOSPACED, style, size), antialias);
    }

    public FontMap(Font font) {
        this(font, true);
    }

    public FontMap(Font font, boolean antiAlias) {
        this.font = font;
        this.antiAliasEnabled = antiAlias;
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

    @Override
    public void init() throws ResourceInitializationException {
        initialized = true;
        // ascii
        Atlas atlas = new Atlas(font, 0, 256, antiAliasEnabled);
        // cyrillic
        Atlas cyrillic = new Atlas(font, 1040, 1104, antiAliasEnabled);
        atlas = atlas.combine(cyrillic);
        glyphMap = atlas.getGlyphsMap();
        glyphUnknown = glyphMap.get((char)31);
        this.texture = new Texture2D(atlas.getImage(), GL_LINEAR, GL_LINEAR);
        this.lineHeight = atlas.getLineHeight();
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }


    public static final class FontMapBuilder {

        private String path;

        private int fontFormat;

        private boolean antiAlias;

        private int size = 16;

        private int style = Font.PLAIN;

        private FontMapBuilder() { }

        public static FontMapBuilder aFont() {
            return new FontMapBuilder();
        }

        public FontMapBuilder fromFile(String path) {
            this.path = path;
            return this;
        }

        public FontMapBuilder withFontFormat(int fontFormat) {
            this.fontFormat = fontFormat;
            return this;
        }

        public FontMapBuilder withAntialiasingEnabled() {
            this.antiAlias = true;
            return this;
        }

        public FontMapBuilder withSize(int size) {
            this.size = size;
            return this;
        }

        public FontMapBuilder withStyle(int style) {
            this.style = style;
            return this;
        }

        public FontMap build() throws FontFormatException, IOException {
            return new FontMap(path, fontFormat, size, style, antiAlias);
        }

    }
}
