package com.jkojote.engine.graphics.primitives;

import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.StaticCamera;
import com.jkojote.linear.engine.graphics2d.primitives.filled.vao.PolygonVao;
import com.jkojote.linear.engine.graphics2d.primitives.filled.vao.RectangleVao;
import com.jkojote.linear.engine.math.Mat4f;
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

    private Mat4f proj;

    private Camera camera;

    private int width = 400, height = 400;

    public VaoObjectTest() {
        proj = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0, 1.0f);
        camera = new StaticCamera();
    }

    @Before
    public void init() {
        renderer = new VaoObjectRenderer(proj);
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
            .setInitCallback(() -> {
                try {
                    renderer.init();
                } catch (ResourceInitializationException e) {
                    window.release();
                }
            });
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
        controller.setTranslationDelta(5);
        window
            .setRenderCallback(() -> renderer.render(rectangle, camera))
            .setKeyCallback(controller)
            .setUpdateCallback(painter::updateColor)
            .init();
        while (!window.isTerminated()) {
            window.update();
            controller.update();
        }
    }

    @Test
    public void drawPolygon() {
        TransformationController controller = new TransformationController(polygon);
        Painter painter = new Painter(polygon);
        controller.setTranslationDelta(5);
        window
            .setRenderCallback(() -> renderer.render(polygon, camera))
            .setKeyCallback(controller)
            .setUpdateCallback(painter::updateColor)
            .init();
        while (!window.isTerminated()) {
            window.update();
            controller.update();
        }
    }

}
