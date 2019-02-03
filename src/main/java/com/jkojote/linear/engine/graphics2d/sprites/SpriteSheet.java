package com.jkojote.linear.engine.graphics2d.sprites;

import com.jkojote.linear.engine.Initializable;
import com.jkojote.linear.engine.Releasable;
import com.jkojote.linear.engine.graphics2d.Texture2D;

import java.util.List;

public interface SpriteSheet extends Releasable, Initializable {

    List<Sprite> sprites();

    Texture2D sheet();

    float getWidth();

    float getHeight();

}