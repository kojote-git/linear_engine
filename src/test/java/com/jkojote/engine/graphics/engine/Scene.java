package com.jkojote.engine.graphics.engine;

import com.jkojote.engine.graphics.BoundingCamera;
import com.jkojote.engine.graphics.LoopRunner;
import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.engine.graphics.sprites.AnimatedSpriteObject;
import com.jkojote.linear.engine.Releasable;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.Renderable;
import com.jkojote.linear.engine.graphics2d.engine.GraphicsEngine;
import com.jkojote.linear.engine.graphics2d.engine.PrimitiveGraphicsEngineImpl;
import com.jkojote.linear.engine.graphics2d.primitives.Shape;
import com.jkojote.linear.engine.graphics2d.primitives.solid.SolidEllipse;
import com.jkojote.linear.engine.graphics2d.primitives.solid.SolidPolygon;
import com.jkojote.linear.engine.graphics2d.primitives.solid.SolidRectangle;
import com.jkojote.linear.engine.graphics2d.primitives.solid.SolidTriangle;
import com.jkojote.linear.engine.graphics2d.sprites.EvenSpriteSheet;
import com.jkojote.linear.engine.graphics2d.sprites.SpriteObject;
import com.jkojote.linear.engine.graphics2d.sprites.SpriteSheet;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_LINEAR;

public class Scene {

    private GraphicsEngine engine;

    private TransformationController controller;

    private Window window;

    private String path = "src/test/java/com/jkojote/engine/graphics/sprites/spritesheet.png";

    private List<Renderable> renderables;

    private List<Releasable> releasables;

    private Camera camera;

    private SpriteObject spriteObject;

    private LoopRunner runner;

    @Before
    public void init() throws Exception {
        renderables = new ArrayList<>();
        releasables = new ArrayList<>();
        window = new Window("render scene", 600, 600, false, false);
        runner = new LoopRunner(window);
        SpriteSheet spriteSheet = new EvenSpriteSheet(path, 180, 340, GL_LINEAR, GL_LINEAR);
        spriteObject = new AnimatedSpriteObject(spriteSheet, 1);
        camera = new BoundingCamera<>((AnimatedSpriteObject) spriteObject, window);
        controller = new TransformationController((AnimatedSpriteObject) spriteObject);
        engine = new PrimitiveGraphicsEngineImpl();
        engine.setCamera(camera);
        for (int i = 0; i < 16; i++)
            renderables.add(getRenderable());
        renderables.add(spriteObject);
        releasables.add(window);
        releasables.add(engine);
        releasables.add(spriteSheet);
        window.setInitCallback(() -> {
            engine.init();
            spriteSheet.init();
            int size = spriteSheet.sprites().size();
            spriteSheet.sprites().remove(size - 1);
            spriteSheet.sprites().remove(size - 2);
        });
        window.setKeyCallback(controller);
    }

    @After
    public void release() {
        for (Releasable r : releasables) {
            tryRelease(r);
        }
    }

    private void tryRelease(Releasable r) {
        try {
            r.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void renderScene() {
        window.init();
        runner.setRenderCallback(() -> {
            for (Renderable r : renderables)
                engine.render(r);
        });
        runner.setUpdateCallback(controller::update);
        runner.run();
    }

    private Renderable getRenderable() {
        int rand = (int) (Math.random() * 10 % 4);
        float x = (float) (Math.random() * 2 - 1) * 400;
        float y = (float) (Math.random() * 2 - 1) * 400;
        Vec3f translation = new Vec3f(x, y, 0);
        Vec3f color = new Vec3f((float) Math.random(), (float) Math.random(), (float) Math.random());
        Shape shape;
        switch (rand) {
            case 0:
                shape = new SolidEllipse(translation, 13, 13);
                shape.setColor(color);
                return shape;
            case 1:
                shape = new SolidRectangle(translation, 13, 13);
                shape.setColor(color);
                return shape;
            case 2:
                shape = new SolidTriangle(translation,
                    new Vec3f(0, 34, 0),
                    new Vec3f(-30, -20, 0),
                    new Vec3f(30, -20, 0)
                );
                shape.setColor(color);
                return shape;
            default:
                return new SolidPolygon(translation, color,
                    new Vec3f[] {
                        new Vec3f(-15, 9, 0), new Vec3f(-15, -9, 0), new Vec3f(-3, -12, 0),
                        new Vec3f(3, -12, 0), new Vec3f(15, -9, 0), new Vec3f(15, 9, 0),
                        new Vec3f(3, 12, 0), new Vec3f(-3, 9, 0)
                    }
                );
        }
    }
}
