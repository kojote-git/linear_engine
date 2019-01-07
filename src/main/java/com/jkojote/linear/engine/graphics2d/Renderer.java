package com.jkojote.linear.engine.graphics2d;


public interface Renderer<T extends Renderable> {

    void render(T renderable, Camera camera);

}
