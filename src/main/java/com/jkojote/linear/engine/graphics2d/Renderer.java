package com.jkojote.linear.engine.graphics2d;

/**
 * An object that is responsible for rendering objects of type {@link Renderable}
 * @param <T>
 */
public interface Renderer<T extends Renderable> {

    /**
     * Renders object on the screen relatively to camera
     * @param renderable an object to be rendered
     * @param camera
     */
    void render(T renderable, Camera camera);

}
