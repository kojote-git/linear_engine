package com.jkojote.engine.physics;

import com.jkojote.engine.graphics.LoopRunner;
import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.engine.graphics.BoundingCamera;
import com.jkojote.linear.engine.graphics2d.cameras.StaticCamera;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.VertexShapeRenderer;
import com.jkojote.linear.engine.graphics2d.text.FontMap;
import com.jkojote.linear.engine.graphics2d.text.ModifiableText;
import com.jkojote.linear.engine.graphics2d.text.TextRenderer;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class CollisionDetectionTest {

    private Window window;

    private int height = 500, width = 500;

    private VertexShapeRenderer vertexShapeRenderer;

    private TextRenderer textRenderer;

    private FontMap fontMap;

    @Before
    public void init() {
        vertexShapeRenderer = new VertexShapeRenderer();
        textRenderer = new TextRenderer();
        fontMap = new FontMap(new Font("Calibri", Font.PLAIN, 32));
        window = new Window("w", width, height, false, false)
            .setInitCallback((win) -> {
                try {
                    vertexShapeRenderer.init();
                    textRenderer.init();
                    fontMap.init();
                } catch (Exception e) {
                    window.release();
                    throw new RuntimeException(e);
                }
            })
            .setTerminationCallback((win) -> {
                vertexShapeRenderer.release();
                textRenderer.release();
                fontMap.release();
            });
    }

    @After
    public void release() {
        window.release();
    }

    @Test
    public void renderCollision() {
        Vec3f[] cbv1 = new Vec3f[] {
            new Vec3f(-3, 4, 0),
            new Vec3f(-5, 0, 0),
            new Vec3f(-3, -4, 0),
            new Vec3f(3, -4, 0),
            new Vec3f(5, 0, 0),
            new Vec3f(3, 4, 0)
        };
        Vec3f[] cbv2 = new Vec3f[] {
            new Vec3f(-3, 0, 0),
            new Vec3f(-3, 4, 0),
            new Vec3f(0, 3, 0),
            new Vec3f(3, 0, 0),
            new Vec3f(3, -4, 0),
            new Vec3f(0, -4, 0)
        };
        Vec3f[] cbv3 = new Vec3f[] {
            new Vec3f(-3, -3, 0),
            new Vec3f(0, 3, 0),
            new Vec3f(3, -3, 0)
        };
        ModifiableText collisionStatus = new ModifiableText(fontMap).append("Collided: false");
        collisionStatus.setTranslation(new Vec3f(-width / 2f, height / 2f, 0));
        CollidablePolygon p1 = new CollidablePolygon(cbv1, new Vec3f(-30, 0, 0), new Vec3f(0.8f, 0.8f, 0.4f));
        CollidablePolygon p2 = new CollidablePolygon(cbv1, new Vec3f(30, 0, 0), new Vec3f(0.1f, 0.8f, 0.4f));
        CollidablePolygon p3 = new CollidablePolygon(cbv2, new Vec3f(-80, 0, 0), new Vec3f(0.1f, 0.8f, 0.4f));
        CollidablePolygon p4 = new CollidablePolygon(cbv3, new Vec3f(-20, -30, 0), new Vec3f(0.1f, 0.8f, 0.4f));
        p1.setScaleFactor(5);
        p2.setScaleFactor(5);
        p2.setRotationAngle(45);
        p3.setScaleFactor(5);
        p4.setScaleFactor(5);
        BoundingCamera camera = new BoundingCamera<>(p1, window);
        StaticCamera staticCamera = new StaticCamera(window);
        TransformationController controller = new TransformationController(p1);
        controller.setScaleFactorDelta(0.05f);
        LoopRunner runner = new LoopRunner(window);
        window
            .setKeyCallback(controller)
            .init();
        runner.setRenderCallback(() -> {
            vertexShapeRenderer.render(p1, camera);
            vertexShapeRenderer.render(p2, camera);
            vertexShapeRenderer.render(p3, camera);
            vertexShapeRenderer.render(p4, camera);
            textRenderer.render(collisionStatus, staticCamera);
        });
        runner.setUpdateCallback(() -> {
            if (p1.checkCollides(p2)) {
                collisionStatus.delete(10, collisionStatus.length()).append("collides with p2");
                collisionStatus.setColor(new Vec3f(0.8f, 0.1f, 0.2f));
            } else if (p1.checkCollides(p3)){
                collisionStatus.delete(10, collisionStatus.length()).append("collides with p3");
                collisionStatus.setColor(new Vec3f(0.8f, 0.1f, 0.2f));
            } else if (p1.checkCollides(p4)){
                collisionStatus.delete(10, collisionStatus.length()).append("collides with p4");
                collisionStatus.setColor(new Vec3f(0.8f, 0.1f, 0.2f));
            } else {
                collisionStatus.delete(10, collisionStatus.length()).append("false");
                collisionStatus.setColor(new Vec3f(0.1f, 0.7f, 0.2f));
            }
            controller.update();
        });
        runner.run();
    }

}
