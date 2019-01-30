package com.jkojote.linear.engine.graphics2d.engine;

import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.graphics2d.text.FontMap;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

/**
 * This extension of GraphicsEngine interface provides more capabilities of rendering text and textures
 */
public interface PrimitiveGraphicsEngine extends GraphicsEngine {

    /**
     * Renders a texture using given transformation
     * @param texture a texture to be rendered
     * @param translation translation transformation
     * @param rotationAngle rotation transformation
     * @param scaleFactor scale transformation
     */
    default void renderTexture(Texture2D texture, Vec3f translation, float rotationAngle, float scaleFactor) {
        renderTexture(texture, GraphicsUtils.transformationMatrix(translation, rotationAngle, scaleFactor));
    }

    /**
     * Renders a texture using given transformation
     * @param texture a texture to be rendered
     * @param transformationMatrix transformation matrix used to transform a texture
     */
    void renderTexture(Texture2D texture, Mat4f transformationMatrix);

    /**
     * Renders text using given transformation and text parameters (font and color)
     * @param text a text to be rendered
     * @param font a font of the text
     * @param color a color of the text
     * @param translation translation transformation
     * @param rotationAngle rotation transformation
     * @param scaleFactor scale transformation
     */
    default void renderText(CharSequence text,
                            FontMap font, Vec3f color,
                            Vec3f translation,
                            float rotationAngle, float scaleFactor) {
        renderText(text, font, color, GraphicsUtils.transformationMatrix(translation, rotationAngle, scaleFactor));
    }

    /**
     * Renders text using given transformation and text parameters (font and color)
     * @param text a text to be rendered
     * @param font a font of the text
     * @param color a color of the text
     * @param transformationMatrix a transformation matrix used to transform text
     */
    void renderText(CharSequence text, FontMap font, Vec3f color, Mat4f transformationMatrix);

}
