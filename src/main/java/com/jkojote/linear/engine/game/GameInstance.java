package com.jkojote.linear.engine.game;

import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.cameras.StaticCamera;
import com.jkojote.linear.engine.graphics2d.engine.GraphicsEngine;
import com.jkojote.linear.engine.graphics2d.engine.PrimitiveGraphicsEngineImpl;
import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.window.Window;

import java.io.PrintStream;

public class GameInstance implements Runnable {
	private Window window;
	private GraphicsEngine graphicsEngine;
	private GameRenderCallback renderCallback;
	private GameUpdateCallback updateCallback;
	private Camera camera;
	private PrintStream errorStream = System.err;
	private boolean running;
	private double amountOfTicks = 60.0;
	private boolean initialized;
	private boolean released;

	public void setGraphicsEngine(GraphicsEngine graphicsEngine) {
		if (!initialized) {
			this.graphicsEngine = graphicsEngine;
		}
	}

	public void setRenderCallback(GameRenderCallback renderCallback) {
		if (!initialized) {
			this.renderCallback = renderCallback;
		}
	}

	public void setUpdateCallback(GameUpdateCallback updateCallback) {
		if (!initialized) {
			this.updateCallback = updateCallback;
		}
	}

	public void setWindow(Window window) {
		if (!initialized) {
			this.window = window;
		}
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public GraphicsEngine getGraphicsEngine() {
		return graphicsEngine;
	}

	public Window getWindow() {
		return window;
	}

	public void setAmountOfTicks(double amountOfTicks) {
		if (!initialized) {
			this.amountOfTicks = amountOfTicks;
		}
	}

	public void setErrorStream(PrintStream errorStream) {
		if (!initialized) {
			this.errorStream = errorStream;
		}
	}

	public boolean isInitialized() {
		return initialized;
	}

	public boolean isReleased() {
		return released;
	}

	public boolean isRunning() {
		return running;
	}

	@Override
	public void run() {
		init();
		double lastTime = System.nanoTime();
		double ns = 1_000_000_000 / amountOfTicks;
		double elapsed = 0;
		running = true;
		while (!window.isTerminated() && running) {
			long now = System.nanoTime();
			elapsed += (now - lastTime) / ns;
			lastTime = now;
			while (elapsed >= 1) {
				elapsed--;
				window.pollEvents();
				if (updateCallback != null) {
					updateCallback.update();
				}
			}
			window.clear();
			if (renderCallback != null) {
				renderCallback.render(graphicsEngine);
			}
			window.update();
		}
		running = false;
		release();
	}

	private void init() {
		if (window == null) {
			throw new IllegalStateException("you must provide a window");
		}
		if (graphicsEngine == null)
			graphicsEngine = new PrimitiveGraphicsEngineImpl();
		try {
			window.init();
			graphicsEngine.init();
		} catch (RuntimeException e) {
			tryRelease(graphicsEngine, errorStream);
			tryRelease(window, errorStream);
			throw new RuntimeException(e);
		}
		if (camera == null && graphicsEngine.getDefaultCamera() == null) {
			camera = new StaticCamera(window);
			graphicsEngine.setDefaultCamera(camera);
		}
		initialized = true;
	}

	private void release() {
		tryRelease(graphicsEngine, errorStream);
		tryRelease(window, errorStream);
		released = true;

	}

	private void tryRelease(Releasable releasable, PrintStream errorStream) {
		try {
			releasable.release();
		} catch (Exception e) {
			e.printStackTrace(errorStream);
		}
	}

}
