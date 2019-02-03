package com.jkojote.engine.graphics.sprites;

import com.jkojote.engine.graphics.LoopRunner;
import com.jkojote.engine.graphics.TransformableCamera;
import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.linear.engine.Releasable;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.TextureRenderer;
import com.jkojote.linear.engine.graphics2d.sprites.EvenSpriteSheet;
import com.jkojote.linear.engine.graphics2d.sprites.Sprite;
import com.jkojote.linear.engine.graphics2d.sprites.SpriteRenderer;
import com.jkojote.linear.engine.graphics2d.sprites.SpriteSheet;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.lwjgl.opengl.GL11.GL_LINEAR;


public class SpritesTest {

    private SpriteSheet spriteSheet;

    private String path = "src/test/java/com/jkojote/engine/graphics/sprites/spritesheet.png";

    private Window window;

    private SpriteRenderer renderer;

    private int width = 400, height = 400;

    @Before
    public void init() {
        window = new Window("w", width, height, false, false);
        renderer = new SpriteRenderer(window.getProjectionMatrix());
        window.setInitCallback(() -> {
            try {
                renderer.init();
                spriteSheet = new EvenSpriteSheet(path, 180, 340, GL_LINEAR, GL_LINEAR);
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
        LoopRunner runner = new LoopRunner(window);
        TransformableCamera camera = new TransformableCamera();
        TransformationController cameraController = new TransformationController(camera);
        window.setKeyCallback(cameraController);
        runner.setUpdateCallback(cameraController::update);
        runner.setRenderCallback(() -> {
            renderer.render(spriteSheet.sprites().get(0), camera);
        });
        window.init();
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
