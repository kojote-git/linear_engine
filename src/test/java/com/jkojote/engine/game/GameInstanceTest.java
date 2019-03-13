package com.jkojote.engine.game;

import com.jkojote.linear.engine.game.GameInstance;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.cameras.StaticCamera;
import com.jkojote.linear.engine.graphics2d.engine.GraphicsEngine;
import com.jkojote.linear.engine.graphics2d.engine.PrimitiveGraphicsEngineImpl;
import com.jkojote.linear.engine.graphics2d.primitives.solid.SolidRectangle;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.Test;

public class GameInstanceTest {

	@Test
	public void configureGameAndRun() {
		GameInstance gameInstance = new GameInstance();
		Window window = Window.WindowConfiguration.createWindow()
				.setTitle("win")
				.setAntialiasingEnabled(true)
				.setHeight(400)
				.setWidth(400)
				.setResizable(false)
				.setMouseEnabled(false)
				.configure();
		Camera camera = new StaticCamera(window);
		GraphicsEngine graphicsEngine = new PrimitiveGraphicsEngineImpl();

		graphicsEngine.setDefaultCamera(camera);
		gameInstance.setWindow(window);
		gameInstance.setGraphicsEngine(graphicsEngine);
		gameInstance.setRenderCallback(g -> {
			g.render(new SolidRectangle(new Vec3f(), 150, 150));
		});
		gameInstance.setUpdateCallback(() -> {
			System.out.println("game updated");
		});
		gameInstance.run();
	}
}
