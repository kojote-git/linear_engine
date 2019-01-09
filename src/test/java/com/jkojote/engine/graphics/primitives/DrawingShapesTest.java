package com.jkojote.engine.graphics.primitives;

import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.StaticCamera;
import com.jkojote.linear.engine.graphics2d.primitives.*;
import com.jkojote.linear.engine.graphics2d.primitives.filled.Ellipse;
import com.jkojote.linear.engine.graphics2d.primitives.filled.Polygon;
import com.jkojote.linear.engine.graphics2d.primitives.filled.Rectangle;
import com.jkojote.linear.engine.graphics2d.primitives.filled.Triangle;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.VertexShapeRenderer;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.EllipseRenderer;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DrawingShapesTest {

    private Rectangle rectangle;

    private Ellipse ellipse;

    private Triangle triangle;

    private Polygon polygon;

    private Line line;

    private VertexShapeRenderer vertexShapeRenderer;

    private EllipseRenderer ellipseRenderer;

    private int width = 400, height = 400;

    private Window window;

    private Camera camera;

    public DrawingShapesTest() {
        // load rectangle
        rectangle = new Rectangle(new Vec3f(100, 100, 0), 80, 80);
        rectangle.setColor(new Vec3f(0.5f, 0.5f, 0.5f));
        // load ellipse
        ellipse = new Ellipse(new Vec3f(0, 0, 0), 80, 90);
        ellipse.setColor(new Vec3f(0.5f, 0.5f, 0.5f));
        // load triangle
        triangle = new Triangle(
                new Vec3f(-15, -30, 0),
                new Vec3f(0, 20, 0),
                new Vec3f(20, -20, 0));
        triangle.setColor(new Vec3f(0.5f, 0.5f, 0.5f));
        // load line
        line = new Line(50);
        line.setColor(new Vec3f(0.5f, 0.5f, 0.5f));
        // load polygon
        // cat head
        polygon = new Polygon(new Vec3f[]{
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
        polygon.setColor(new Vec3f(0.3f, 0.8f, 0.2f));
        Mat4f proj = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0.0f, 1.0f);
        vertexShapeRenderer = new VertexShapeRenderer(proj);
        ellipseRenderer = new EllipseRenderer(proj);
        camera = new StaticCamera();
    }

    @Before
    public void init() {
        window = new Window("w", width, height, false, false)
            .setInitCallback(() -> {
                try {
                    vertexShapeRenderer.init();
                    ellipseRenderer.init();
                } catch (ResourceInitializationException e) {
                    window.release();
                }
            })
            .setWindowClosedCallback(() -> {
                vertexShapeRenderer.release();
                ellipseRenderer.release();
            });
    }

    @After
    public void release() {
        window.release();
    }

    // draw rectangle, rotate it pressing right and left arrows
    // and scale it pressing up and down arrows
    @Test
    public void drawRectangle() {
        TransformationController controller = new TransformationController(rectangle);
        controller.setTranslationDelta(10);
        camera = new BoundingCamera<>(rectangle);
        window
            .setRenderCallback(() -> {
                vertexShapeRenderer.render(rectangle, camera);
                ellipseRenderer.render(ellipse, camera);
            })
            .setKeyCallback(controller)
            .init();
        while (!window.isTerminated()) {
            window.update();
            controller.update();
        }
    }

    @Test
    public void drawLine() {
        TransformationController controller = new TransformationController(line);
        window
            .setRenderCallback(() -> vertexShapeRenderer.render(line, camera))
            .setKeyCallback(controller)
            .init();
        while (!window.isTerminated()) {
            window.update();
            controller.update();
        }
    }

    @Test
    public void drawTriangle() {
        TransformationController controller = new TransformationController(triangle);
        window
            .setRenderCallback(() -> vertexShapeRenderer.render(triangle, camera))
            .setKeyCallback(controller)
            .init();
        while (!window.isTerminated()) {
            window.update();
            controller.update();
        }
    }

    @Test
    public void drawEllipse() {
        TransformationController controller = new TransformationController(ellipse);
        Painter painter = new Painter(ellipse);
        controller.setTranslationDelta(5);
        window
            .setRenderCallback(() -> ellipseRenderer.render(ellipse, camera))
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
            .setRenderCallback(() -> vertexShapeRenderer.render(polygon, camera))
            .setKeyCallback(controller)
            .setUpdateCallback(painter::updateColor)
            .init();
        while (!window.isTerminated()) {
            window.update();
            controller.update();
        }
    }
}
