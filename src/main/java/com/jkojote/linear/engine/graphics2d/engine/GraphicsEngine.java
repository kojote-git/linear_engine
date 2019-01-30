package com.jkojote.linear.engine.graphics2d.engine;

import com.jkojote.linear.engine.Initializable;
import com.jkojote.linear.engine.Releasable;
import com.jkojote.linear.engine.graphics2d.*;

import java.util.Collection;

/**
 * This interface encapsulates the functionality of rendering {@link Renderable} objects.
 * The implementation may not support rendering some objects that implement this interface.
 */
public interface GraphicsEngine extends Initializable, Releasable {

    /**
     * Sets the camera that is used for rendering
     * @param camera a camera to be set
     */
    void setCamera(Camera camera);

    /**
     * @return camera that is used fo rendering
     */
    Camera getCamera();

    /**
     * Renders all objects from collection
     * @param renderables objects to be rendered
     */
    void renderAll(Collection<Renderable> renderables);

    /**
     * Renders one given object
     * @param renderable an object to be rendered
     */
    void render(Renderable renderable);

}
