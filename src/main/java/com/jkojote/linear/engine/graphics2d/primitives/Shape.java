package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.graphics2d.Renderable;
import com.jkojote.linear.engine.math.Vec3f;

/**
 * Basic interface for shapes.
 * It may be extended by other interfaces to provide some new properties of the shape, but basic property is the color.
 */
public interface Shape extends Renderable {
    /**
     * @return color of the primitive
     */
    Vec3f color();

    /**
     * Sets the color of this shape
     * @param color color of this shape
     */
    void setColor(Vec3f color);
}
