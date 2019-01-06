package com.jkojote.engine.graphics.primitives;

import com.jkojote.linear.engine.graphics2d.primitives.Shape;
import com.jkojote.linear.engine.math.Vec3f;

/*
 * Object that is used to update color of the shape in a smooth manner
 */
public class Painter {

    private int colorChanges;

    private float colorOffsetX, colorOffsetY, colorOffsetZ;

    private float
            xColor = 0.0f,
            yColor = 0.0f,
            zColor = 0.0f;

    private static float colorOffsetFraction = 100;

    private Shape shape;

    public Painter(Shape shape) {
        this.shape = shape;
        colorOffsetX = (float) (Math.random() / colorOffsetFraction);
        colorOffsetY = (float) (Math.random() / colorOffsetFraction);
        colorOffsetZ = (float) (Math.random() / colorOffsetFraction);
    }

    public void updateColor() {
        if (colorChanges > 255) {
            colorChanges = 0;
            colorOffsetX = (float) (Math.random() / colorOffsetFraction);
            colorOffsetY = (float) (Math.random() / colorOffsetFraction);
            colorOffsetZ = (float) (Math.random() / colorOffsetFraction);
        }
        Vec3f color = shape.color();
        float incX = (float) (Math.random() / 100);
        float incY = (float) (Math.random() / 100);
        float incZ = (float) (Math.random() / 100);
        float x = color.getX() + incX * xColor + colorOffsetX;
        float y = color.getY() + incY * yColor + colorOffsetY;
        float z = color.getZ() + incZ * zColor + colorOffsetZ;
        if (x >= 1.0f) {
            xColor = -1;
            x = 1.0f;
        } else if (x <= 0.0f) {
            xColor = 1;
            x = 0.0f;
        }
        if (y >= 1.0f) {
            yColor = -1;
            y = 1.0f;
        } else if (y <= 0.0f) {
            yColor = 1;
            y = 0.0f;
        }
        if (z >= 1.0f) {
            zColor = -1;
            z = 1.0f;
        } else if (z <= 0.0f) {
            zColor = 1;
            z = 0.0f;
        }
        colorChanges++;
        shape.setColor(new Vec3f(x, y, z));
    }
}
