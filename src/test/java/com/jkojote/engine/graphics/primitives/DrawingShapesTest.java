package com.jkojote.engine.graphics.primitives;

import com.jkojote.linear.engine.graphics2d.primitives.Ellipse;
import com.jkojote.linear.engine.graphics2d.primitives.Line;
import com.jkojote.linear.engine.graphics2d.primitives.Rectangle;
import com.jkojote.linear.engine.graphics2d.primitives.Triangle;
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

    private Line line;

    private VertexShapeRenderer vertexShapeRenderer;

    private EllipseRenderer ellipseRenderer;

    private int width = 400, height = 400;

    private Window window;

    public DrawingShapesTest() {
        // create rectangle
        rectangle = new Rectangle(new Vec3f(100, 100, 0), 80, 80);
        rectangle.setColor(new Vec3f(0.5f, 0.5f, 0.5f));
        // create ellipse
        ellipse = new Ellipse(new Vec3f(0, 0, 0), 80, 90);
        ellipse.setColor(new Vec3f(0.5f, 0.5f, 0.5f));
        // create triangle
        triangle = new Triangle(
                new Vec3f(-15, -30, 0),
                new Vec3f(0, 20, 0),
                new Vec3f(20, -20, 0));
        triangle.setColor(new Vec3f(0.5f, 0.5f, 0.5f));
        // create line
        line = new Line(50);
        line.setColor(new Vec3f(0.5f, 0.5f, 0.5f));
        Mat4f proj = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0.0f, 1.0f);
        vertexShapeRenderer = new VertexShapeRenderer(Mat4f.identity(), proj);
        ellipseRenderer = new EllipseRenderer(Mat4f.identity(), proj);
    }

    @Before
    public void init() {
        window = new Window("w", width, height, false, false)
            .setInitCallback(() -> {
                vertexShapeRenderer.init();
                ellipseRenderer.init();
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
        ShapeTransformationController controller = new ShapeTransformationController(rectangle);
        controller.setTranslationDelta(10);
        window
            .setRenderCallback(() -> vertexShapeRenderer.render(rectangle))
            .setKeyCallback(controller)
            .init();
        while (!window.isTerminated()) {
            window.update();
            controller.update();
        }
    }

    @Test
    public void drawLine() {
        ShapeTransformationController controller = new ShapeTransformationController(line);
        window
            .setRenderCallback(() -> vertexShapeRenderer.render(line))
            .setKeyCallback(controller)
            .init();
        while (!window.isTerminated()) {
            window.update();
            controller.update();
        }
    }

    @Test
    public void drawTriangle() {
        ShapeTransformationController controller = new ShapeTransformationController(triangle);
        window
            .setRenderCallback(() -> vertexShapeRenderer.render(triangle))
            .setKeyCallback(controller)
            .init();
        while (!window.isTerminated()) {
            window.update();
            controller.update();
        }
    }

    @Test
    public void drawEllipse() {
        ShapeTransformationController controller = new ShapeTransformationController(ellipse);
        window
            .setRenderCallback(() -> ellipseRenderer.render(ellipse))
            .setKeyCallback(controller)
            .init();
        while (!window.isTerminated()) {
            window.update();
            controller.update();
        }
    }
}
