package com.jkojote.linear.engine.game;

import com.jkojote.linear.engine.graphics2d.engine.GraphicsEngine;

public interface GameRenderCallback {
	/**
	 * @param g graphics engine that is used for rendering
	 */
	void render(GraphicsEngine g);
}
