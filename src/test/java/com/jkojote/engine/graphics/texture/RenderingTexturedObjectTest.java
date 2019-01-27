package com.jkojote.engine.graphics.texture;

import com.jkojote.engine.graphics.LoopRunner;
import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.StaticCamera;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.TexturedObjectRenderer;

import java.io.IOException;

public class RenderingTexturedObjectTest {

    private Bird bird;

    private TexturedObjectRenderer renderer;

    private Window window;

    private Mat4f proj;

    private Camera camera;

    private int width = 600, height = 600;

    public RenderingTexturedObjectTest() {
        proj = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0.0f, 1.0f);
        camera = new StaticCamera();
    }

    @Before
    public void init() throws IOException {
        renderer = new TexturedObjectRenderer(proj);
        bird = new Bird();
        window = new Window("w", width, height, false, false)
            .setInitCallback(() -> {
                try {
                    renderer.init();
                    bird.init();
                } catch (ResourceInitializationException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    window.release();
                }
            });
    }

    @After
    public void release() {
        renderer.release();
        bird.release();
        window.release();
    }

    @Test
    public void renderTexture() {
        TransformationController controller = new TransformationController(bird);
        LoopRunner runner = new LoopRunner(window);
        bird.setScaleFactor(0.2f);
        controller.setScaleFactorDelta(0.01f);
        controller.setRotationDelta(3);
        controller.setTranslationDelta(5);
        window
            .setKeyCallback(controller)
            .init();
        runner.setRenderCallback(() -> renderer.render(bird, camera));
        runner.setUpdateCallback(controller::update);
        runner.run();
    }
}
