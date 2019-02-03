package com.jkojote.linear.engine.graphics2d.sprites;

import com.jkojote.linear.engine.graphics2d.Texture2D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_NEAREST;

public class EvenSpriteSheet implements SpriteSheet {

    private Texture2D texture;

    private List<Sprite> sprites;

    public EvenSpriteSheet(Texture2D texture, float spriteWidth, float spriteHeight) {
        sprites = new ArrayList<>();
        this.texture = texture;
        cutSprites(texture, spriteWidth, spriteHeight);
    }

    public EvenSpriteSheet(String path, float spriteWidth, float spriteHeight) throws IOException {
        this(path, spriteWidth, spriteHeight, GL_NEAREST, GL_NEAREST);
    }

    public EvenSpriteSheet(String path,
                           float spriteWidth, float spriteHeight,
                           int minFilter, int magFilter) throws IOException {
        sprites = new ArrayList<>();
        texture = new Texture2D(path, minFilter, magFilter);
        cutSprites(texture, spriteWidth, spriteHeight);
    }

    @Override
    public List<Sprite> sprites() {
        return sprites;
    }

    @Override
    public Texture2D sheet() {
        return texture;
    }

    @Override
    public float getWidth() {
        return texture.getWidth();
    }

    @Override
    public float getHeight() {
        return texture.getHeight();
    }

    @Override
    public void release() {
        texture.release();
    }

    @Override
    public boolean isReleased() {
        return texture.isReleased();
    }

    private void cutSprites(Texture2D texture, float spriteWidth, float spriteHeight) {
        float texWidth = texture.getWidth(), texHeight = texture.getHeight();
        float x = 0, y = 0;
        while (y <= texHeight) {
            if (x >= texWidth) {
                x = 0;
                y += spriteHeight;
            } else {
                sprites.add(new OrdinarySprite(this, new Point(x, y), spriteWidth, spriteHeight));
                x += spriteWidth;
            }
        }
    }
}
