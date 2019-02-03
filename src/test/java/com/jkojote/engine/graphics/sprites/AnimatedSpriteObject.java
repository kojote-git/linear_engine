package com.jkojote.engine.graphics.sprites;

import com.jkojote.linear.engine.graphics2d.sprites.Sprite;
import com.jkojote.linear.engine.graphics2d.sprites.SpriteObject;
import com.jkojote.linear.engine.graphics2d.sprites.SpriteSheet;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.shared.BaseTransformable;

import java.util.List;

public class AnimatedSpriteObject extends BaseTransformable implements SpriteObject {

    private SpriteSheet spriteSheet;

    private List<Sprite> sprites;

    private int changeAfter = 32;

    private int getCount = 0;

    private int last = 0;

    private int sheetSize;

    public AnimatedSpriteObject(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
        this.sheetSize = spriteSheet.sprites().size();
        this.sprites = spriteSheet.sprites();
    }

    public AnimatedSpriteObject(SpriteSheet spriteSheet, int changeAfter) {
        this.spriteSheet = spriteSheet;
        this.sheetSize = spriteSheet.sprites().size();
        this.changeAfter = changeAfter;
        this.sprites = spriteSheet.sprites();
    }

    private Sprite getSprite() {
        getCount++;
        if (getCount == changeAfter) {
            getCount = 0;
            if (last == sheetSize - 1) {
                last = 0;
            }
            last++;
        }
        return sprites.get(last);
    }

    @Override
    public Sprite sprite() {
        return getSprite();
    }

    @Override
    public Mat4f modelMatrix() {
        return transformationMatrix();
    }
}
