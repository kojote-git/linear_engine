package com.jkojote.engine.graphics.engine;

import com.jkojote.engine.graphics.LoopRunner;
import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.engine.graphics.primitives.BoundingCamera;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.StaticCamera;
import com.jkojote.linear.engine.graphics2d.engine.GraphicsEngine;
import com.jkojote.linear.engine.graphics2d.engine.SimpleGraphicsEngine;
import com.jkojote.linear.engine.graphics2d.primitives.filled.Ellipse;
import com.jkojote.linear.engine.graphics2d.primitives.filled.Rectangle;
import com.jkojote.linear.engine.graphics2d.primitives.filled.Triangle;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.PublicKey;

public class GraphicsEngineTest {

    private GraphicsEngine engine;

    private Window window;

    @Before
    public void init() {
        window = new Window("Window", 400, 400, false, false);
        engine = new SimpleGraphicsEngine(window);
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
        Rectangle rectangle = new Rectangle(new Vec3f(), 50, 50);
        Ellipse ellipse = new Ellipse(new Vec3f(), 40, 60);
        Triangle triangle = new Triangle(new Vec3f(-20, -15, 0), new Vec3f(0, 20, 0), new Vec3f(20, -15, 0));
        Camera camera = new StaticCamera();
        engine.setCamera(camera);
        engine.renderVertexShape(rectangle);
        engine.renderEllipse(ellipse);
        engine.renderVertexShape(triangle);
        window.render();
    }
}
