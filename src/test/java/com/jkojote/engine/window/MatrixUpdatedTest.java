package com.jkojote.engine.window;

import com.jkojote.engine.graphics.LoopRunner;
import com.jkojote.engine.graphics.TransformableCamera;
import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.linear.engine.graphics2d.engine.GraphicsEngine;
import com.jkojote.linear.engine.graphics2d.engine.PrimitiveGraphicsEngineImpl;
import com.jkojote.linear.engine.graphics2d.primitives.solid.SolidRectangle;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MatrixUpdatedTest {

    private GraphicsEngine engine;

    private Window window;

    private TransformableCamera camera;

    private LoopRunner runner;

    private SolidRectangle rectangle;

    private TransformationController controller;

    @Before
    public void init() {
        System.out.println("Matrix Updated Test:");
        System.out.println("Resize window and watch if it has no problems with resizing");
        window = new Window("test", 300, 300, true, true);
        engine = new PrimitiveGraphicsEngineImpl();
        camera = new TransformableCamera(window);
        controller = new TransformationController(camera);
        runner = new LoopRunner(window);
        rectangle = new SolidRectangle(new Vec3f(), 50, 50);
        engine.setDefaultCamera(camera);
        window.setInitCallback((win) -> {
            engine.init();
        });
        window.setKeyCallback(controller);
        runner.setUpdateCallback(controller::update);
        runner.setRenderCallback(() -> {
            engine.render(rectangle);
        });
        window.init();
    }

    @After
    public void release() {
        engine.release();
        window.release();
    }

    @Test
    public void test() {
        runner.run();
    }
}
