package com.jkojote.linear.engine.graphics2d.sprites;

import com.jkojote.linear.engine.Releasable;
import com.jkojote.linear.engine.graphics2d.Texture2D;

import java.util.ArrayList;
import java.util.List;

public class EvenSpriteSheet implements SpriteSheet, Releasable {

    private Texture2D texture;

    private List<Sprite> sprites;

    public EvenSpriteSheet(Texture2D texture, float spriteWidth, float spriteHeight) {
        sprites = new ArrayList<>();
        this.texture = texture;
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
            }
            sprites.add(new TransformableSprite(this, new Point(x, y), spriteWidth, spriteHeight));
            x += spriteWidth;
        }
    }
}
