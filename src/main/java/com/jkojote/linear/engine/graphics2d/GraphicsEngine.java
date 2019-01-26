package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.graphics2d.primitives.VertexShape;
import com.jkojote.linear.engine.graphics2d.primitives.filled.Ellipse;
import com.jkojote.linear.engine.graphics2d.text.FontMap;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;

import java.util.Collection;

public interface GraphicsEngine {

    Window getWindow();

    void setCamera(Camera camera);

    Camera getCamera();

    void renderAll(Collection<Renderable> renderable);

    default void renderTexture(Texture2D texture, Vec3f translation, float rotationAngle, float scaleFactor) {
        renderTexture(texture, GraphicsUtils.transformationMatrix(translation, rotationAngle, scaleFactor));
    }

    void renderTexture(Texture2D texture2D, Mat4f transformationMatrix);

    void renderTexturedObject(TexturedObject texturedObject);

    default void renderText(CharSequence text,
                            FontMap font, Vec3f color,
                            Vec3f translation,
                            float rotationAngle, float scaleFactor) {
        renderText(text, font, color, GraphicsUtils.transformationMatrix(translation, rotationAngle, scaleFactor));
    }

    void renderText(CharSequence text, FontMap font, Vec3f color, Mat4f transformationMatrix);

    void renderVertexShape(VertexShape shape);

    void renderVaoObject(VaoObject object);

    void renderEllipse(Ellipse ellipse);
}
