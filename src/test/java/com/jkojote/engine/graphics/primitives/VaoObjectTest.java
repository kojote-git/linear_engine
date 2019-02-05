package com.jkojote.engine.graphics.primitives;

import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.engine.graphics.LoopRunner;
import com.jkojote.linear.engine.shared.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.cameras.StaticCamera;
import com.jkojote.linear.engine.graphics2d.primitives.solid.vao.PolygonVao;
import com.jkojote.linear.engine.graphics2d.primitives.solid.vao.RectangleVao;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jkojote.linear.engine.graphics2d.primitives.renderers.VaoObjectRenderer;

public class VaoObjectTest {

    private RectangleVao rectangle;

    private PolygonVao polygon;

    private Window window;

    private VaoObjectRenderer renderer;


    private Camera camera;

    private int width = 400, height = 400;

    public VaoObjectTest() { }

    @Before
    public void init() {
        renderer = new VaoObjectRenderer();
        rectangle = new RectangleVao(new Vec3f(), 10, 10);
        polygon = new PolygonVao(new Vec3f[]{
            new Vec3f(-2, -5, 0),
            new Vec3f(-5, -4, 0),
            new Vec3f(-7, -2, 0),
            new Vec3f(-7,  1, 0),
            new Vec3f(-6,  4, 0),
            new Vec3f(-4,  7, 0),
            new Vec3f(-3,  4, 0),
            // reflectional symmetry
            new Vec3f( 3,  4, 0),
            new Vec3f( 4,  7, 0),
            new Vec3f( 6,  4, 0),
            new Vec3f( 7,  1, 0),
            new Vec3f( 7, -2, 0),
            new Vec3f( 5, -4, 0),
            new Vec3f( 2, -5, 0)
        });
        window = new Window("w", width, height, false, false)
            .setInitCallback((win) -> {
                try {
                    renderer.init();
                } catch (ResourceInitializationException e) {
                    window.release();
                }
            });
        camera = new StaticCamera(window);
    }

    @After
    public void release() {
        renderer.release();
        polygon.release();
        rectangle.release();
        window.release();
    }

    @Test
    public void drawRectangle() {
        TransformationController controller = new TransformationController(rectangle);
        Painter painter = new Painter(rectangle);
        LoopRunner runner = new LoopRunner(window);
        controller.setTranslationDelta(5);
        window
            .setKeyCallback(controller)
            .init();
        runner.setRenderCallback(() -> renderer.render(rectangle, camera));
        runner.setUpdateCallback(() -> {
            painter.updateColor();
            controller.update();
        });
        runner.run();
    }

    @Test
    public void drawPolygon() {
        TransformationController controller = new TransformationController(polygon);
        LoopRunner runner = new LoopRunner(window);
        Painter painter = new Painter(polygon);
        controller.setTranslationDelta(5);
        window
            .setKeyCallback(controller)
            .init();
        runner.setRenderCallback(() -> renderer.render(polygon, camera));
        runner.setUpdateCallback(() -> {
            painter.updateColor();
            controller.update();
        });
        runner.run();
    }

}
