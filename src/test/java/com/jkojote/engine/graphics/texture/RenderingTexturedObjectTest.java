package com.jkojote.engine.graphics.texture;

import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RenderingTexturedObjectTest {

    private Bird bird;

    private TextureObjectRenderer renderer;

    private Window window;

    private Mat4f view;

    private Mat4f proj;

    private int width = 600, height = 600;

    public RenderingTexturedObjectTest() {
        view = Mat4f.identity();
        proj = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0.0f, 1.0f);
    }

    @Before
    public void init() {
        renderer = new TextureObjectRenderer(view, proj);
        bird = new Bird();
        window = new Window("w", width, height, false, false)
            .setInitCallback(() -> {
                renderer.init();
                bird.init();
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
        bird.setScaleFactor(0.2f);
        controller.setScaleFactorDelta(0.01f);
        controller.setRotationDelta(3);
        controller.setTranslationDelta(5);
        window
            .setRenderCallback(() -> renderer.render(bird))
            .setKeyCallback(controller)
            .init();
        while (!window.isTerminated()) {
            window.update();
            controller.update();
        }
    }
}
