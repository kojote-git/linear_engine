package com.jkojote.engine.graphics.engine;

import com.jkojote.engine.graphics.LoopRunner;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.cameras.StaticCamera;
import com.jkojote.linear.engine.graphics2d.engine.GraphicsEngine;
import com.jkojote.linear.engine.graphics2d.engine.PrimitiveGraphicsEngineImpl;
import com.jkojote.linear.engine.graphics2d.primitives.striped.StripedEllipse;
import com.jkojote.linear.engine.graphics2d.primitives.striped.StripedRectangle;
import com.jkojote.linear.engine.graphics2d.primitives.striped.StripedTriangle;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphicsEngineTest {

    private GraphicsEngine engine;

    private Window window;

    @Before
    public void init() {
        window = new Window("Window", 400, 400, false, false);
        engine = new PrimitiveGraphicsEngineImpl();
        try {
            engine.init();
        } catch (ResourceInitializationException e) {
            System.out.println("Cannot initialize engine");
            e.printStackTrace();
        }
    }

    @After
    public void release() {
        engine.release();
    }


    @Test
    public void drawShapes() {
        StripedRectangle rectangle = new StripedRectangle(new Vec3f(-100, 0, 0), 50, 50);
        StripedEllipse ellipse = new StripedEllipse(new Vec3f(120, 0, 0), 40, 60);
        StripedTriangle triangle = new StripedTriangle(new Vec3f(-20, -15, 0), new Vec3f(0, 20, 0), new Vec3f(20, -15, 0));
        LoopRunner runner = new LoopRunner(window);
        Camera camera = new StaticCamera(window);
        engine.setCamera(camera);
        runner.setRenderCallback(() -> {
            engine.render(rectangle);
            engine.render(ellipse);
            engine.render(triangle);
        });
        runner.run();
    }
}
