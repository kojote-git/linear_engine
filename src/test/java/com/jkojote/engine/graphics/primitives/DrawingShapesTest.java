package com.jkojote.engine.graphics.primitives;

import com.jkojote.engine.graphics.TransformableCamera;
import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.engine.graphics.LoopRunner;
import com.jkojote.linear.engine.ResourceInitializationException;
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

import static org.lwjgl.opengl.GL11.*;


public class DrawingShapesTest {

    private Rectangle rectangle;

    private Ellipse ellipse;

    private Triangle triangle;

    private Polygon polygon;

    private Line line;

    private VertexShapeRenderer vertexShapeRenderer;

    private EllipseRenderer ellipseRenderer;

    private int width = 600, height = 600;

    private Window window;

    private TransformableCamera transformableCamera;

    private StaticCamera staticCamera;

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
    }

    @Before
    public void init() {
        staticCamera = new StaticCamera();
        transformableCamera = new TransformableCamera();
        window = new Window("w", width, height, false, false)
            .setInitCallback(() -> {
                try {
                    vertexShapeRenderer.init();
                    ellipseRenderer.init();
                } catch (ResourceInitializationException e) {
                    window.release();
                }
                System.out.println("GL_VENDOR: " + glGetString(GL_VENDOR));
                System.out.println("GL_VERSION" + glGetString(GL_VERSION));
                System.out.println("GL_RENDERER: " + glGetString(GL_RENDERER));
            })
            .setTerminationCallback(() -> {
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
        LoopRunner runner = new LoopRunner(window);
        controller.setTranslationDelta(10);
        BoundingCamera camera = new BoundingCamera<>(rectangle);
        window
            .setKeyCallback(controller)
            .init();
        runner.setRenderCallback(() -> {
            vertexShapeRenderer.render(rectangle, camera);
            ellipseRenderer.render(ellipse, camera);
        });
        runner.setUpdateCallback(controller::update);
        runner.run();
    }

    @Test
    public void drawLine() {
        TransformationController controller = new TransformationController(line);
        window
            .setKeyCallback(controller)
            .init();
        while (!window.isTerminated()) {
            window.clear();
            window.pollEvents();
            vertexShapeRenderer.render(line, staticCamera);
            window.update();
            controller.update();
        }
    }

    @Test
    public void drawTriangle() {
        TransformationController controller = new TransformationController(triangle);
        LoopRunner runner = new LoopRunner(window);
        window
            .setKeyCallback(controller)
            .init();
        runner.setRenderCallback(() -> vertexShapeRenderer.render(triangle, staticCamera));
        runner.setUpdateCallback(controller::update);
        runner.run();
    }

    @Test
    public void drawEllipse() {
        TransformationController controller = new TransformationController(ellipse);
        Painter painter = new Painter(ellipse);
        LoopRunner runner = new LoopRunner(window);
        controller.setTranslationDelta(5);
        window
            .setKeyCallback(controller)
            .init();
        runner.setUpdateCallback(() -> {
            painter.updateColor();
            controller.update();
        });
        runner.setRenderCallback(() -> ellipseRenderer.render(ellipse, staticCamera));
        runner.run();
    }

    @Test
    public void drawPolygon() {
        TransformationController controller = new TransformationController(polygon);
        LoopRunner runner = new LoopRunner(window);
        Painter painter = new Painter(polygon);
        controller.setTranslationDelta(5f);
        window
            .setKeyCallback(controller)
            .init();
        runner.setUpdateCallback(() -> {
            painter.updateColor();
            controller.update();
        });
        runner.setRenderCallback(() -> vertexShapeRenderer.render(polygon, staticCamera));
        runner.run();
    }
}
