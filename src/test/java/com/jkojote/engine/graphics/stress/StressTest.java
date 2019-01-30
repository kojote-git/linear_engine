package com.jkojote.engine.graphics.stress;

import com.jkojote.engine.graphics.LoopRunner;
import com.jkojote.engine.graphics.TransformableCamera;
import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.linear.engine.graphics2d.cameras.StaticCamera;
import com.jkojote.linear.engine.shared.Transformable;
import com.jkojote.linear.engine.graphics2d.primitives.Shape;
import com.jkojote.linear.engine.graphics2d.primitives.VertexShape;
import com.jkojote.linear.engine.graphics2d.primitives.solid.Ellipse;
import com.jkojote.linear.engine.graphics2d.primitives.solid.Polygon;
import com.jkojote.linear.engine.graphics2d.primitives.solid.Rectangle;
import com.jkojote.linear.engine.graphics2d.primitives.solid.Triangle;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.EllipseRenderer;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.VertexShapeRenderer;
import com.jkojote.linear.engine.graphics2d.text.FontMap;
import com.jkojote.linear.engine.graphics2d.text.ModifiableText;
import com.jkojote.linear.engine.graphics2d.text.TextRenderer;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class StressTest {

    private TransformableCamera transformableCamera;

    private StaticCamera staticCamera;

    private Mat4f proj;

    private FontMap fontMap;

    private TextRenderer textRenderer;

    private EllipseRenderer ellipseRenderer;

    private VertexShapeRenderer vertexShapeRenderer;

    private int width = 800, height = 800;

    private Window window;

    public StressTest() {
        proj = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0.0f, 1.0f);
    }

    @Before
    public void init() throws Exception {
        transformableCamera = new TransformableCamera();
        staticCamera = new StaticCamera();
        textRenderer = new TextRenderer(proj);
        ellipseRenderer = new EllipseRenderer(proj);
        vertexShapeRenderer = new VertexShapeRenderer(proj);
        fontMap = FontMap.FontMapBuilder.aFont()
                .fromFile("src/test/java/com/jkojote/engine/graphics/text/Cousine-Regular.ttf")
                .withFontFormat(Font.TRUETYPE_FONT)
                .withSize(48)
                .withAntialiasingEnabled()
                .build();
        window = new Window("test", width, height, false, false)
                .setInitCallback(() -> {
                    try {
                        fontMap.init();
                        textRenderer.init();
                        ellipseRenderer.init();
                        vertexShapeRenderer.init();
                    } catch (Exception e) {
                        window.release();
                    }
                    System.out.println("GL_VENDOR: " + glGetString(GL_VENDOR));
                    System.out.println("GL_VERSION: " + glGetString(GL_VERSION));
                    System.out.println("GL_RENDERER: " + glGetString(GL_RENDERER));
                })
                .setTerminationCallback(() -> {
                    fontMap.release();
                    textRenderer.release();
                    ellipseRenderer.release();
                    vertexShapeRenderer.release();
                });
    }

    @After
    public void release() {
        window.release();
    }

    @Test
    public void drawMultipleObjects() {
        TransformationController controller = new TransformationController(transformableCamera);
        ModifiableText fpsCount = new ModifiableText(fontMap).append("FPS: ");
        LoopRunner runner = new LoopRunner(window, (fps) -> {
            fpsCount.delete(4, fpsCount.length());
            fpsCount.append(String.valueOf(fps));
        });
        int shapesNumber = 1024;
        List<Shape> shapes = getRandomShapes(shapesNumber);

        controller.setScaleFactorDelta(0.01f);
        transformableCamera.setScaleFactor(0.2f);
        fpsCount.setTranslation(new Vec3f(width / 2f - 200, height / 2f, 0));
        fpsCount.setScaleFactor(0.8f);
        fpsCount.setColor(new Vec3f(0.3f, 0.8f, 0.3f));
        controller.setTranslationDelta(5f);
        window
            .setKeyCallback(controller)
            .init();
        runner.setUpdateCallback(() -> {
            controller.update();
            for (Shape shape : shapes) {
                if (shape instanceof Transformable)
                randomTransform((Transformable) shape);
            }
        });
        runner.setRenderCallback(() -> {
            for (Shape shape : shapes) {
                if (shape instanceof VertexShape)
                    vertexShapeRenderer.render((VertexShape) shape, transformableCamera);
                if (shape instanceof Ellipse)
                    ellipseRenderer.render((Ellipse) shape, transformableCamera);
            }
            textRenderer.render(fpsCount, staticCamera);
        });
        runner.run();
    }

    private List<Shape> getRandomShapes(int number) {
        List<Shape> shapes = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            shapes.add(getShape());
        }
        return shapes;
    }

    private Shape getShape() {
        int rand = (int) (Math.random() * 10 % 4);
        float x = (float) (Math.random() * 2 - 1) * 10;
        float y = (float) (Math.random() * 2 - 1) * 10;
        Vec3f translation = new Vec3f(x, y, 0);
        switch (rand) {
            case 0:
                return new Rectangle(translation, 30, 30);
            case 1:
                Triangle triangle = new Triangle(
                        new Vec3f(-15, -15, 0),
                        new Vec3f(0, 15, 0),
                        new Vec3f(15, -15, 0)
                );
                triangle.setTranslation(translation);
            case 2:
                return new Ellipse(translation, 30, 30);
            default:
                return new Polygon(translation, new Vec3f(), new Vec3f[]{
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
        }
    }

    private void randomTransform(Transformable transformable) {
        int rand = (int) (Math.random() * 10 % 3);
        switch (rand) {
            case 1:
                float x = (float)(Math.random() * 2 - 1) * 50;
                float y = (float)(Math.random() * 2 - 1) * 50;
                transformable.setTranslation(transformable.getTranslation().add(x, y, 0));
                break;
            case 2:
                float scaleFactor = (float) Math.random() * 3;
                transformable.setScaleFactor(scaleFactor);
                break;
            case 3:
                float rotation = (float) (Math.random() * 2 - 1) * 5;
                transformable.setRotationAngle(transformable.getRotationAngle() + rotation);
                break;
        }
    }
}
