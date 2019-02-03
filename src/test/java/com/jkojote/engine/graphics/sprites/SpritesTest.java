package com.jkojote.engine.graphics.sprites;

import com.jkojote.engine.graphics.LoopRunner;
import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.engine.graphics.primitives.BoundingCamera;
import com.jkojote.linear.engine.Releasable;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.sprites.EvenSpriteSheet;
import com.jkojote.linear.engine.graphics2d.sprites.SpriteObjectRenderer;
import com.jkojote.linear.engine.graphics2d.sprites.SpriteSheet;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.lwjgl.opengl.GL11.GL_LINEAR;


public class SpritesTest {

    private SpriteSheet spriteSheet;

    private Camera camera;

    private LoopRunner runner;

    private TransformationController controller;

    private AnimatedSpriteObject spriteObject;

    private String path = "src/test/java/com/jkojote/engine/graphics/sprites/spritesheet.png";

    private Window window;

    private SpriteObjectRenderer renderer;

    private int width = 400, height = 400;

    @Before
    public void init() throws Exception {
        window = new Window("w", width, height, false, false);
        renderer = new SpriteObjectRenderer(window.getProjectionMatrix());
        runner = new LoopRunner(window, (fps) -> System.out.println("FPS: " + fps));
        spriteSheet = new EvenSpriteSheet(path, 180, 340, GL_LINEAR, GL_LINEAR);
        spriteObject = new AnimatedSpriteObject(spriteSheet, 1);
        camera = new BoundingCamera<>(spriteObject);
        controller = new TransformationController(spriteObject);
        runner.setUpdateCallback(controller::update);
        window.setKeyCallback(controller);
        window.setInitCallback(() -> {
            try {
                spriteSheet.init();
                int last = spriteSheet.sprites().size() - 1;
                spriteSheet.sprites().remove(last--);
                spriteSheet.sprites().remove(last);
                renderer.init();
            } catch (Exception e) {
                e.printStackTrace();
                tryRelease(renderer);
                tryRelease(spriteSheet);
            }
        });
    }

    @After
    public void dispose() {
        tryRelease(renderer);
        tryRelease(spriteSheet);
        tryRelease(window);
    }

    @Test
    public void render() {
        window.init();
        runner.setRenderCallback(() -> {
            renderer.render(spriteObject, camera);
        });
        runner.run();
    }

    private void tryRelease(Releasable releasable) {
        try {
            releasable.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
