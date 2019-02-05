package com.jkojote.linear.engine.graphics2d.engine;

import com.jkojote.linear.engine.shared.Initializable;
import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.graphics2d.*;

import java.util.Collection;

/**
 * This interface encapsulates the functionality of rendering {@link Renderable} objects.
 * The implementation may not support rendering some objects that implement this interface.
 */
public interface GraphicsEngine extends Initializable, Releasable {

    /**
     * Sets the camera that is used for rendering if no camera for rendering is specified
     * @param camera a camera to be set
     */
    void setCamera(Camera camera);

    /**
     * @return camera that is used fo rendering if no camera for rendering is specified
     */
    Camera getCamera();

    /**
     * Renders all objects from collection
     * @param renderables objects to be rendered
     */
    void renderAll(Collection<Renderable> renderables);

    void renderAll(Collection<Renderable> renderables, Camera camera);

    /**
     * Renders one given object
     * @param renderable an object to be rendered
     */
    void render(Renderable renderable);

    void render(Renderable renderable, Camera camera);
}
