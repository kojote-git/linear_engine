package com.jkojote.linear.engine.graphics2d.engine;

import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.graphics2d.text.FontMap;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

/**
 * This extension of {@link GraphicsEngine} engine provides
 */
public interface PrimitiveGraphicsEngine extends GraphicsEngine {

    default void renderTexture(Texture2D texture, Vec3f translation, float rotationAngle, float scaleFactor) {
        renderTexture(texture, GraphicsUtils.transformationMatrix(translation, rotationAngle, scaleFactor));
    }

    void renderTexture(Texture2D texture2D, Mat4f transformationMatrix);

    default void renderText(CharSequence text,
                            FontMap font, Vec3f color,
                            Vec3f translation,
                            float rotationAngle, float scaleFactor) {
        renderText(text, font, color, GraphicsUtils.transformationMatrix(translation, rotationAngle, scaleFactor));
    }

    void renderText(CharSequence text, FontMap font, Vec3f color, Mat4f transformationMatrix);

}
