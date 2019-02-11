package com.jkojote.engine.physics;

import com.jkojote.engine.graphics.LoopRunner;
import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.engine.graphics.BoundingCamera;
import com.jkojote.linear.engine.graphics2d.Camera;
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
import java.util.ArrayList;
import java.util.List;

public class CollisionDetectionTest {
    private Window window;
    private int height = 500, width = 500;
    private VertexShapeRenderer vertexShapeRenderer;
    private TextRenderer textRenderer;
    private FontMap fontMap;
    private List<CollidablePolygon> polygons;

    @Before
    public void init() {
        vertexShapeRenderer = new VertexShapeRenderer();
        textRenderer = new TextRenderer();
        fontMap = new FontMap(new Font("Calibri", Font.PLAIN, 32));
        polygons = createPolygons();
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
        CollidablePolygon controlledPolygon;
        ModifiableText collisionStatus;
        Camera boundingCamera, staticCamera;
        TransformationController polygonController;

        collisionStatus = new ModifiableText(fontMap).append("Collided: false");
        controlledPolygon = polygons.get(0);
        collisionStatus.setTranslation(new Vec3f(-width / 2f, height / 2f, 0));
        boundingCamera = new BoundingCamera<>(controlledPolygon, window);
        staticCamera = new StaticCamera(window);
        polygonController = new TransformationController(controlledPolygon);
        polygonController.setScaleFactorDelta(0.05f);

        LoopRunner runner = new LoopRunner(window);
        window
            .setKeyCallback(polygonController)
            .init();
        runner.setRenderCallback(() -> {
            renderPolygons(polygons, boundingCamera);
            textRenderer.render(collisionStatus, staticCamera);
        });
        runner.setUpdateCallback(() -> {
            updateCollisionStatus(collisionStatus, controlledPolygon, polygons);
            polygonController.update();
        });
        runner.run();
    }

    private void renderPolygons(List<CollidablePolygon> polygons, Camera camera) {
        for (CollidablePolygon polygon : polygons)
            vertexShapeRenderer.render(polygon, camera);
    }

    private void updateCollisionStatus(ModifiableText collisionStatus,
                                       CollidablePolygon controlledPolygon,
                                       List<CollidablePolygon> polygons) {
        int polygonNumber = 1;
        for (CollidablePolygon polygon : polygons) {
            if (controlledPolygon != polygon && controlledPolygon.checkCollides(polygon)) {
                collisionStatus.delete(10, collisionStatus.length()).append("collides with p" + polygonNumber);
                collisionStatus.setColor(new Vec3f(0.8f, 0.1f, 0.2f));
                return;
            }
        }
        collisionStatus.delete(10, collisionStatus.length()).append("false");
        collisionStatus.setColor(new Vec3f(0.1f, 0.7f, 0.2f));
    }

    private List<CollidablePolygon> createPolygons() {
        List<CollidablePolygon> polygons = new ArrayList<>();
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
        CollidablePolygon p1 = new CollidablePolygon(cbv1, new Vec3f(-30, 0, 0), new Vec3f(0.8f, 0.8f, 0.4f));
        CollidablePolygon p2 = new CollidablePolygon(cbv1, new Vec3f(30, 0, 0), new Vec3f(0.1f, 0.8f, 0.4f));
        CollidablePolygon p3 = new CollidablePolygon(cbv2, new Vec3f(-80, 0, 0), new Vec3f(0.1f, 0.8f, 0.4f));
        CollidablePolygon p4 = new CollidablePolygon(cbv3, new Vec3f(-20, -30, 0), new Vec3f(0.1f, 0.8f, 0.4f));
        p2.setRotationAngle(45);
        polygons.add(p1);
        polygons.add(p2);
        polygons.add(p3);
        polygons.add(p4);
        polygons.forEach(p -> p.setScaleFactor(5));
        return polygons;
    }
}
