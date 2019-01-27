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

    void setCamera(Camera camera);

    Camera getCamera();

    void renderAll(Collection<Renderable> renderables);

    void render(Renderable renderable);

}
