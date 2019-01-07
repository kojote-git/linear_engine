package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

/**
 * Base interface for transformable objects.
 * It allows to perform such transformations as scaling, rotation and translation on the object,
 * but it also may be decided that the object doesn't support some of the transformations.
 * All these transformations are combined into the matrix returned by {@link Transformable#transformationMatrix()}
 */
public interface Transformable {

    /**
     * Sets the vector that is used for translation transformation (optional transformation)
     * @param translation vector that is used for translation transformation
     */
    void setTranslation(Vec3f translation);

    /**
     * @return vector that is used for translation transformation
     */
    Vec3f getTranslation();

    /**
     * Sets the scale factor that is used for scaling transformation (optional transformation)
     * @param scaleFactor scale factor that is used for scaling transformation
     */
    void setScaleFactor(float scaleFactor);

    /**
     * @return scale factor that is used for scaling transformation
     */
    float getScaleFactor();

    /**
     * Sets rotation angle that is used for rotation transformation (optional transformation)
     * Rotation is performed around Z axis
     * @param rotationAngle rotation angle that is used for rotation transformation
     */
    void setRotationAngle(float rotationAngle);

    /**
     * @return rotation angle that is used for rotation transformation
     */
    float getRotationAngle();

    /**
     * @return transformation matrix
     * @see Transformable
     */
    Mat4f transformationMatrix();
}
